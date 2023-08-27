package com.koi.koiAuth.client;

import com.koi.common.domain.CommonResult;
import com.koi.koiAuth.framework.config.KoiAuthProperties;
import com.koi.koiAuth.framework.core.dto.LoginUser;
import com.koi.koiAuth.framework.core.dto.user.UserInfoRespDTO;
import com.koi.koiAuth.framework.core.dto.user.UserUpdateReqDTO;
import com.koi.koiAuth.framework.core.utils.KoiAuthSecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * 用户 User 信息的客户端
 *
 * 对应调用 OAuth2UserController 接口
 *
 * @Author zjl
 * @Date 2023/8/27 17:23
 */
public class UserClient {

    private static final String BASE_URL = "http://127.0.0.1:48080/app-api/system/oauth2/user";

    //    @Resource // 可优化，注册一个 RestTemplate Bean，然后注入
    private final RestTemplate restTemplate = new RestTemplate();

    public CommonResult<UserInfoRespDTO> getUser() {
        // 构建请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        addTokenHeader(headers);
        //构建请求参数
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        // 执行请求
        ResponseEntity<CommonResult<UserInfoRespDTO>> exchange = restTemplate.exchange(
                BASE_URL + "/get",
                HttpMethod.GET,
                new HttpEntity<>(body, headers),
                new ParameterizedTypeReference<CommonResult<UserInfoRespDTO>>() {}); // 解决 CommonResult 的泛型丢失
        Assert.isTrue(exchange.getStatusCode().is2xxSuccessful(), "响应必须是 200 成功");
        return exchange.getBody();
    }

    public CommonResult<Boolean> updateUser(UserUpdateReqDTO updateReqDTO) {
        // 构建请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        addTokenHeader(headers);
        // 构建请求参数
        // 使用 updateReqDTO 即可

        // 执行请求
        ResponseEntity<CommonResult<Boolean>> exchange = restTemplate.exchange(
                BASE_URL + "/update",
                HttpMethod.PUT,
                new HttpEntity<>(updateReqDTO, headers),
                new ParameterizedTypeReference<CommonResult<Boolean>>() {}); // 解决 CommonResult 的泛型丢失
        Assert.isTrue(exchange.getStatusCode().is2xxSuccessful(), "响应必须是 200 成功");
        return exchange.getBody();
    }


    private void addTokenHeader(HttpHeaders headers) {
        LoginUser loginUser = KoiAuthSecurityUtils.getLoginUser();
        Assert.notNull(loginUser, "登录用户不能为空");
        headers.add("Authorization", "Bearer " + loginUser.getAccessToken());
    }

}
