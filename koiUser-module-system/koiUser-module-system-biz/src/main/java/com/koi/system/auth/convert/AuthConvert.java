package com.koi.system.auth.convert;

import com.koi.common.utils.bean.BeanCopyUtils;
import com.koi.system.auth.domain.vo.response.AuthLoginRespVO;
import com.koi.system.oauth2.domain.entity.Oauth2AccessToken;
import org.springframework.stereotype.Component;

/**
 * -
 *
 * @Author zjl
 * @Date 2023/8/4 21:54
 */
@Component
public class AuthConvert {

    public AuthLoginRespVO convertAuthLogin(Oauth2AccessToken bean) {
        AuthLoginRespVO authLoginRespVO = BeanCopyUtils.copyObject(bean, AuthLoginRespVO.class);
        return authLoginRespVO;
    }

}
