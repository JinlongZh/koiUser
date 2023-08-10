package com.koi.member.convert.user;

import com.koi.common.utils.bean.BeanCopyUtils;
import com.koi.member.api.user.dto.response.OAuth2UserInfoRespDTO;
import com.koi.member.domain.user.entity.MemberUser;

/**
 * -
 *
 * @Author zjl
 * @Date 2023/8/9 20:45
 */
public interface MemberUserConvert {

    static OAuth2UserInfoRespDTO convertOAuth2UserInfo(MemberUser bean) {
        return BeanCopyUtils.copyObject(bean, OAuth2UserInfoRespDTO.class);
    }

}
