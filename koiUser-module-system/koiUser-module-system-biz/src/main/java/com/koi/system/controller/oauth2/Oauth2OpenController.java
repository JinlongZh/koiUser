package com.koi.system.controller.oauth2;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.koi.common.domain.CommonResult;
import com.koi.common.exception.ServiceException;
import com.koi.common.utils.bean.BeanCopyUtils;
import com.koi.common.utils.date.DateUtils;
import com.koi.common.utils.http.HttpUtils;
import com.koi.system.convert.oauth2.Oauth2OpenConvert;
import com.koi.system.domain.oauth2.entity.Oauth2AccessToken;
import com.koi.system.domain.oauth2.entity.Oauth2Client;
import com.koi.system.domain.oauth2.vo.response.OAuth2OpenAccessTokenResp;
import com.koi.system.domain.oauth2.vo.response.OAuth2OpenCheckTokenResp;
import com.koi.system.enums.oauth2.OAuth2GrantTypeEnum;
import com.koi.system.service.oauth2.Oauth2ClientService;
import com.koi.system.service.oauth2.Oauth2GrantService;
import com.koi.system.service.oauth2.Oauth2TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;

import static com.koi.common.exception.enums.GlobalErrorCodeConstants.BAD_REQUEST;
import static com.koi.common.utils.json.JsonUtils.stringListFromJson;

/**
 * 主要提供给外部应用调用
 *
 * @Author zjl
 * @Date 2023/7/30 15:49
 */
@RestController
@RequestMapping("/system/oauth2")
public class Oauth2OpenController {

    @Resource
    Oauth2OpenConvert oauth2OpenConvert;
    @Resource
    private Oauth2ClientService oauth2ClientService;
    @Resource
    private Oauth2TokenService oauth2TokenService;
    @Resource
    private Oauth2GrantService oauth2GrantService;

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
                throw new ServiceException(BAD_REQUEST.getCode(), "未知授权类型");
        }
        Assert.notNull(oauth2accessToken, "访问令牌不能为空"); // 防御性检查

        return CommonResult.success(oauth2OpenConvert.convertAccessToken(oauth2accessToken));
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
        // 封装
        OAuth2OpenCheckTokenResp oAuth2OpenCheckTokenResp = BeanCopyUtils.copyObject(oauth2AccessToken, OAuth2OpenCheckTokenResp.class);
        oAuth2OpenCheckTokenResp.setScopes(stringListFromJson(oauth2AccessToken.getScopes()));
        oAuth2OpenCheckTokenResp.setExp(DateUtils.toSecond(oauth2AccessToken.getExpiresTime()));

        return CommonResult.success(oauth2OpenConvert.convertCheckToken(oauth2AccessToken));
    }

}
