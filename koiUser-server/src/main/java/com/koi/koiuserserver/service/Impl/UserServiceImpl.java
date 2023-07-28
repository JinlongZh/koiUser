package com.koi.koiuserserver.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.koi.koiuserserver.domain.entity.User;
import com.koi.koiuserserver.service.UserService;
import com.koi.koiuserserver.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 * @author zjl
 * @description 针对表【user(用户)】的数据库操作Service实现
 * @createDate 2023-07-28 14:16:56
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}




