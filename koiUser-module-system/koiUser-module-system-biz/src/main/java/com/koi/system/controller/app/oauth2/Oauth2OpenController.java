package com.koi.system.controller.app.oauth2;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.koi.common.domain.CommonResult;
import com.koi.common.enums.UserTypeEnum;
import com.koi.common.exception.ServiceException;
import com.koi.common.utils.bean.BeanCopyUtils;
import com.koi.common.utils.collection.CollectionUtils;
import com.koi.common.utils.date.DateUtils;
import com.koi.common.utils.http.HttpUtils;
import com.koi.common.utils.json.JsonUtils;
import com.koi.framework.security.core.utils.SecurityFrameworkUtils;
import com.koi.system.convert.oauth2.Oauth2OpenConvert;
import com.koi.system.domain.oauth2.entity.Oauth2AccessToken;
import com.koi.system.domain.oauth2.entity.Oauth2Approve;
import com.koi.system.domain.oauth2.entity.Oauth2Client;
import com.koi.system.domain.oauth2.vo.response.OAuth2OpenAccessTokenResp;
import com.koi.system.domain.oauth2.vo.response.OAuth2OpenAuthorizeInfoRespVO;
import com.koi.system.domain.oauth2.vo.response.OAuth2OpenCheckTokenResp;
import com.koi.system.enums.oauth2.OAuth2GrantTypeEnum;
import com.koi.system.service.oauth2.Oauth2ApproveService;
import com.koi.system.service.oauth2.Oauth2ClientService;
import com.koi.system.service.oauth2.Oauth2GrantService;
import com.koi.system.service.oauth2.Oauth2TokenService;
import com.koi.system.utils.oauth2.OAuth2Utils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.koi.common.exception.enums.GlobalErrorCodeConstants.BAD_REQUEST;
import static com.koi.common.utils.json.JsonUtils.stringListFromJson;

/**
 * 主要提供给外部应用调用
 *
 * @Author zjl
 * @Date 2023/7/30 15:49
 */
@Tag(name = "OAuth2.0 授权")
@RestController
@RequestMapping("/system/oauth2")
public class Oauth2OpenController {

    @Resource
    private Oauth2ClientService oauth2ClientService;
    @Resource
    private Oauth2TokenService oauth2TokenService;
    @Resource
    private Oauth2GrantService oauth2GrantService;
    @Resource
    private Oauth2ApproveService oauth2ApproveService;

    /**
     * 获得访问令牌
     */
    @PermitAll
    @PostMapping("/token")
    @Operation(summary = "获得访问令牌", description = "适合 code 授权码模式")
    @Parameters({
            @Parameter(name = "grant_type", required = true, description = "授权类型", example = "authorization_code"),
            @Parameter(name = "code", description = "授权码", example = "userinfo"),
            @Parameter(name = "redirect_uri", description = "重定向 URI", example = "https://127.0.0.1:18080"),
            @Parameter(name = "state", description = "状态", example = "1")
    })
    public CommonResult<OAuth2OpenAccessTokenResp> postAccessToken(HttpServletRequest request,
                                                                   @RequestParam("grant_type") String grantType,
                                                                   @RequestParam(value = "code", required = false) String code, // 授权码模式
                                                                   @RequestParam(value = "redirect_uri", required = false) String redirectUri, // 授权码模式
                                                                   @RequestParam(value = "state", required = false) String state // 授权码模式
    ) {
        // 校验授权类型
        OAuth2GrantTypeEnum grantTypeEnum = OAuth2GrantTypeEnum.getByGranType(grantType);
        if (grantTypeEnum == null) {
            throw new ServiceException(BAD_REQUEST.getCode(), StrUtil.format("未知授权类型({})", grantType));
        }
        // 校验客户端
        String[] clientIdAndSecret = HttpUtils.obtainBasicAuthorization(request);
        if (clientIdAndSecret == null) {
            throw new ServiceException(BAD_REQUEST.getCode(), "clientId或clientSecret错误");
        }
        Oauth2Client oauth2Client = oauth2ClientService.validOAuthClientFromCache(clientIdAndSecret[0], clientIdAndSecret[1],
                grantType, null, redirectUri);

        // 根据授权模式，获取访问令牌
        Oauth2AccessToken oauth2accessToken;
        switch (grantTypeEnum) {
            case AUTHORIZATION_CODE:
                oauth2accessToken = oauth2GrantService.grantAuthorizationCodeForAccessToken(oauth2Client.getClientId(), code, redirectUri, state);
                break;
            default:
                throw new ServiceException(BAD_REQUEST.getCode(), StrUtil.format("未知授权类型({})", grantType));
        }
        Assert.notNull(oauth2accessToken, "访问令牌不能为空"); // 防御性检查

        return CommonResult.success(Oauth2OpenConvert.convertAccessToken(oauth2accessToken));
    }

    /**
     * 校验访问令牌
     */
    @PermitAll
    @PostMapping("/check-token")
    @Operation(summary = "校验访问令牌")
    @Parameter(name = "token", required = true, description = "访问令牌", example = "biu")
    public CommonResult<OAuth2OpenCheckTokenResp> checkToken(HttpServletRequest request,
                                                             @RequestParam("token") String token) {
        // 校验客户端
        String[] clientIdAndSecret = HttpUtils.obtainBasicAuthorization(request);
        if (clientIdAndSecret == null) {
            throw new ServiceException(BAD_REQUEST.getCode(), "clientId或clientSecret错误");
        }
        oauth2ClientService.validOAuthClientFromCache(clientIdAndSecret[0], clientIdAndSecret[1],
                null, null, null);

        // 校验令牌
        Oauth2AccessToken oauth2AccessToken = oauth2TokenService.checkAccessToken(token);
        Assert.notNull(oauth2AccessToken, "访问令牌不能为空"); // 防御性检查

        return CommonResult.success(Oauth2OpenConvert.convertCheckToken(oauth2AccessToken));
    }

    /**
     * 场景一：【自动授权 autoApprove = true】
     *          刚进入 sso.vue 界面，调用该接口，用户历史已经给该应用做过对应的授权，或者 OAuth2Client 支持该 scope 的自动授权
     * 场景二：【手动授权 autoApprove = false】
     *          在 sso.vue 界面，用户选择好 scope 授权范围，调用该接口，进行授权。此时，approved 为 true 或者 false
     */
    @PostMapping("/authorize")
    @Operation(summary = "申请授权", description = "适合 code 授权码模式；在 sso.vue 单点登录界面被【提交】调用")
    @Parameters({
            @Parameter(name = "response_type", required = true, description = "响应类型", example = "code"),
            @Parameter(name = "client_id", required = true, description = "客户端编号", example = "koiuser-sso-demo-by-code"),
            @Parameter(name = "scope", description = "授权范围", example = "userinfo.read"), // 使用 Map<String, Boolean> 格式，Spring MVC 暂时不支持这么接收参数
            @Parameter(name = "redirect_uri", required = true, description = "重定向 URI", example = "https://127.0.0.1:18080"),
            @Parameter(name = "auto_approve", required = true, description = "用户是否接受", example = "true"),
            @Parameter(name = "state", example = "1")
    })
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<String> approveOrDeny(@RequestParam("response_type") String responseType,
                                              @RequestParam("client_id") String clientId,
                                              @RequestParam(value = "scope", required = false) String scope,
                                              @RequestParam("redirect_uri") String redirectUri,
                                              @RequestParam(value = "auto_approve") Boolean autoApprove,
                                              @RequestParam(value = "state", required = false) String state) {
        @SuppressWarnings("unchecked")
        Map<String, Boolean> scopes = JsonUtils.convertToMap(scope);
        scopes = ObjectUtil.defaultIfNull(scopes, Collections.emptyMap());

        // 0. 校验用户已经登录。通过 Spring Security 实现

        // 校验 responseType 是否满足 code 或者 token 值
        OAuth2GrantTypeEnum grantTypeEnum = getGrantTypeEnum(responseType);

        // 校验 redirectUri 重定向域名是否合法 + 校验 scope 是否在 Client 授权范围内
        Oauth2Client client = oauth2ClientService.validOAuthClientFromCache(clientId, null,
                grantTypeEnum.getGrantType(), scopes.keySet(), redirectUri);

        // 场景一
        if (Boolean.TRUE.equals(autoApprove)) {
            // 如果无法自动授权通过，则返回空 url，前端不进行跳转
            if (!oauth2ApproveService.checkForPreApproval(SecurityFrameworkUtils.getLoginUserId(), getUserType(), clientId, scopes.keySet())) {
                return CommonResult.success(null);
            }
        } else { // 场景二
            // 如果计算后不通过，则跳转一个错误链接
            if (!oauth2ApproveService.updateAfterApproval(SecurityFrameworkUtils.getLoginUserId(), getUserType(), clientId, scopes)) {
                return CommonResult.success(OAuth2Utils.buildUnsuccessfulRedirect(redirectUri, responseType, state,
                        "access_denied", "User denied access"));
            }
        }

        // 如果是 code 授权码模式，则发放 code 授权码，并重定向
        List<String> approveScopes = CollectionUtils.convertList(scopes.entrySet(), Map.Entry::getKey, Map.Entry::getValue);
        if (grantTypeEnum == OAuth2GrantTypeEnum.AUTHORIZATION_CODE) {
            return CommonResult.success(getAuthorizationCodeRedirect(SecurityFrameworkUtils.getLoginUserId(), client, approveScopes, redirectUri, state));
        }
        return null;
    }

    /**
     * 获得授权信息
     */
    @GetMapping("/authorize")
    @Operation(summary = "获得授权信息", description = "适合 code 授权码模式；在 sso.vue 单点登录界面被【获取】调用")
    @Parameter(name = "clientId", required = true, description = "客户端编号", example = "koiuser-sso-demo-by-code")
    public CommonResult<OAuth2OpenAuthorizeInfoRespVO> authorize(@RequestParam("clientId") String clientId) {
        // 校验用户已经登录。通过 Spring Security 实现

        // 获得 Client 客户端的信息
        Oauth2Client oauth2Client = oauth2ClientService.validOAuthClientFromCache(clientId);
        // 获得用户已经授权的信息
        List<Oauth2Approve> oauth2ApproveList = oauth2ApproveService.getApproveList(SecurityFrameworkUtils.getLoginUserId(), getUserType(), clientId);

        // 封装返回
        return CommonResult.success(Oauth2OpenConvert.convertOpenAuthorizeInfo(oauth2Client, oauth2ApproveList));
    }


    private static OAuth2GrantTypeEnum getGrantTypeEnum(String responseType) {
        if (StrUtil.equals(responseType, "code")) {
            return OAuth2GrantTypeEnum.AUTHORIZATION_CODE;
        }
        throw new ServiceException(BAD_REQUEST.getCode(), "response_type 参数值只允许 code");
    }

    private String getAuthorizationCodeRedirect(Long userId, Oauth2Client oauth2Client,
                                                List<String> scopes, String redirectUri, String state) {
        // 1. 创建 code 授权码
        String authorizationCode = oauth2GrantService.grantAuthorizationCodeForCode(userId, getUserType(), oauth2Client.getClientId(), scopes,
                redirectUri, state);
        // 2. 拼接重定向的 URL
        return OAuth2Utils.buildAuthorizationCodeRedirectUri(redirectUri, authorizationCode, state);
    }

    private Integer getUserType() {
        return UserTypeEnum.MEMBER.getValue();
    }

}
