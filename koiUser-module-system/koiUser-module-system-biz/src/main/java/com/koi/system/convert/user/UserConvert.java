package com.koi.system.convert.user;

import com.koi.common.utils.bean.BeanCopyUtils;
import com.koi.system.domain.user.entity.User;
import com.koi.system.domain.user.vo.response.UserInfoRespVO;

/**
 * -
 *
 * @Author zjl
 * @Date 2023/8/17 21:28
 */
public interface UserConvert {

    static UserInfoRespVO convertUserInfo(User user) {
        return BeanCopyUtils.copyObject(user, UserInfoRespVO.class);
    }

}
