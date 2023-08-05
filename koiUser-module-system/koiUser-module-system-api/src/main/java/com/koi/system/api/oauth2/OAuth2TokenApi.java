package com.koi.system.api.oauth2;

import com.koi.common.domain.CommonResult;
import com.koi.system.api.oauth2.dto.response.OAuth2AccessTokenCheckRespDTO;
import com.koi.system.api.oauth2.dto.request.OAuth2AccessTokenCreateReqDTO;
import com.koi.system.api.oauth2.dto.response.OAuth2AccessTokenRespDTO;
import com.koi.system.enums.ApiConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * OAuth2.0 Token API 接口
 *
 * @Author zjl
 * @Date 2023/8/1 21:04
 */
@FeignClient(name = ApiConstants.NAME)
public interface OAuth2TokenApi {

    String PREFIX = ApiConstants.PREFIX + "/oauth2/token";

    /**
     * 校验 Token 的 URL 地址，主要是提供给 Gateway 使用
     */
    @SuppressWarnings("HttpUrlsUsage")
    String URL_CHECK = "http://" + ApiConstants.NAME + PREFIX + "/check";

    /**
     * 创建访问令牌
     *
     * @param reqDTO 访问令牌的创建信息
     * @return 访问令牌的信息
     */
    @PostMapping(PREFIX + "/create")
    OAuth2AccessTokenRespDTO createAccessToken(@Valid @RequestBody OAuth2AccessTokenCreateReqDTO reqDTO);

    /**
     * 校验访问令牌
     *
     * @param accessToken 访问令牌
     * @return 访问令牌的信息
     */
    @GetMapping(PREFIX + "/check")
    CommonResult<OAuth2AccessTokenCheckRespDTO> checkAccessToken(@RequestParam("accessToken") String accessToken);

    /**
     * 移除访问令牌
     *
     * @param accessToken 访问令牌
     * @return 访问令牌的信息
     */
    @DeleteMapping(PREFIX + "/remove")
    OAuth2AccessTokenRespDTO removeAccessToken(@RequestParam("accessToken") String accessToken);

    /**
     * 刷新访问令牌
     *
     * @param refreshToken 刷新令牌
     * @param clientId 客户端编号
     * @return 访问令牌的信息
     */
    @PutMapping(PREFIX + "/refresh")
    OAuth2AccessTokenRespDTO refreshAccessToken(@RequestParam("refreshToken") String refreshToken,
                                                @RequestParam("clientId") String clientId);

}
