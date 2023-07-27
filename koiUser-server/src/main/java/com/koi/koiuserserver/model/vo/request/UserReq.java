package com.koi.koiuserserver.model.vo.request;

import lombok.Data;

/**
 * 登录用户
 *
 * @Author zjl
 * @Date 2023/7/27 16:51
 */
@Data
public class UserReq {

    private String email;

    private String code;

//    private String password;

}
