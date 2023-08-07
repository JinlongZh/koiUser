package com.koi.member.convert.auth;

import com.koi.common.utils.bean.BeanCopyUtils;
import com.koi.member.domain.auth.vo.response.AppAuthLoginRespVO;
import com.koi.system.api.oauth2.dto.response.OAuth2AccessTokenRespDTO;

/**
 * -
 *
 * @Author zjl
 * @Date 2023/8/7 17:09
 */
public interface AuthConvert {

    static AppAuthLoginRespVO convertAppAuthLogin(OAuth2AccessTokenRespDTO bean) {
        return BeanCopyUtils.copyObject(bean, AppAuthLoginRespVO.class);
    }

}
