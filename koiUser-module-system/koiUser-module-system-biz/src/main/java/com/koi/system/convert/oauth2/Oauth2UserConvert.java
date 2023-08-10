package com.koi.system.convert.oauth2;

import com.koi.common.utils.bean.BeanCopyUtils;
import com.koi.member.api.user.dto.response.OAuth2UserInfoRespDTO;
import com.koi.system.domain.oauth2.vo.response.OAuth2UserInfoRespVO;

/**
 * -
 *
 * @Author zjl
 * @Date 2023/8/9 20:59
 */
public interface Oauth2UserConvert {

    static OAuth2UserInfoRespVO convertOAuth2UserInfo(OAuth2UserInfoRespDTO bean) {
        return BeanCopyUtils.copyObject(bean, OAuth2UserInfoRespVO.class);
    }
}
