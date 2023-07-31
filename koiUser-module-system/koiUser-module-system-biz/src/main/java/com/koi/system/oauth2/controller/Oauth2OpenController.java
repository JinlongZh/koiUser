package com.koi.system.oauth2.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.koi.common.exception.ServiceException;
import com.koi.common.domain.CommonResult;
import com.koi.common.utils.BeanCopyUtils;
import com.koi.common.utils.http.HttpUtils;
import com.koi.system.oauth2.domain.entity.Oauth2AccessToken;
import com.koi.system.oauth2.domain.entity.Oauth2Client;
import com.koi.system.oauth2.domain.vo.resp.OAuth2OpenCheckTokenResp;
import com.koi.system.oauth2.enums.OAuth2GrantTypeEnum;
import com.koi.system.oauth2.domain.vo.resp.OAuth2OpenAccessTokenResp;
import com.koi.system.oauth2.service.Oauth2TokenService;
import com.koi.system.oauth2.service.Oauth2ClientService;
import com.koi.system.oauth2.service.Oauth2GrantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.koi.common.exception.enums.GlobalErrorCodeConstants.BAD_REQUEST;

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
    private Oauth2ClientService oauth2ClientService;
    @Resource
    private Oauth2TokenService oauth2TokenService;
    @Resource
    private Oauth2GrantService oauth2GrantService;

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
        Oauth2Client client = oauth2ClientService.validOAuthClientFromCache(clientIdAndSecret[0], clientIdAndSecret[1],
                grantType, null, redirectUri);

        // 根据授权模式，获取访问令牌
        Oauth2AccessToken oauth2accessToken;
        switch (grantTypeEnum) {
            case AUTHORIZATION_CODE:
                oauth2accessToken = oauth2GrantService.grantAuthorizationCodeForAccessToken(client.getClientId(), code, redirectUri, state);
                break;
            default:
                throw new ServiceException(BAD_REQUEST.getCode(), "未知授权类型");
        }
        Assert.notNull(oauth2accessToken, "访问令牌不能为空"); // 防御性检查
        return CommonResult.success(BeanCopyUtils.copyObject(oauth2accessToken, OAuth2OpenAccessTokenResp.class));
    }

    /**
     * 校验访问令牌
     */
    @PostMapping("/check-token")
    @Operation(summary = "校验访问令牌")
    @Parameter(name = "token", required = true, description = "访问令牌", example = "biu")
    public CommonResult<OAuth2OpenCheckTokenResp> checkToken(HttpServletRequest request,
                                                             @RequestParam("token") String token) {
        // 校验客户端
        String[] clientIdAndSecret = HttpUtils.obtainBasicAuthorization(request);
        oauth2ClientService.validOAuthClientFromCache(clientIdAndSecret[0], clientIdAndSecret[1],
                null, null, null);

        // 校验令牌
        Oauth2AccessToken oauth2AccessToken = oauth2TokenService.checkAccessToken(token);
        Assert.notNull(oauth2AccessToken, "访问令牌不能为空"); // 防御性检查
        return CommonResult.success(BeanCopyUtils.copyObject(oauth2AccessToken, OAuth2OpenCheckTokenResp.class));
    }

}
