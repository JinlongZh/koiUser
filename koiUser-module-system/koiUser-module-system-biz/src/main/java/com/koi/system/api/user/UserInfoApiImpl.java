package com.koi.system.api.user;

import com.koi.common.utils.bean.BeanCopyUtils;
import com.koi.system.api.oauth2.dto.response.UserInfoRespDTO;
import com.koi.system.domain.user.entity.User;
import com.koi.system.mapper.mysql.user.UserMapper;
import com.koi.system.service.user.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户信息 Api实现类
 *
 * @Author zjl
 * @Date 2024/8/4
 */
@Service
public class UserInfoApiImpl implements UserInfoApi {

    @Resource
    private UserMapper userMapper;

    @Override
    public UserInfoRespDTO getUserInfoByUserId(Long id) {
        User user = userMapper.selectById(id);
        return BeanCopyUtils.copyObject(user, UserInfoRespDTO.class);
    }

    @Override
    public Map<Long, UserInfoRespDTO> getUserInfoByUserIds(Set<Long> ids) {
        List<User> userList = userMapper.selectBatchIds(ids);
        List<UserInfoRespDTO> userInfoRespDTOList = BeanCopyUtils.copyList(userList, UserInfoRespDTO.class);
        return userInfoRespDTOList.stream().collect(Collectors.toMap(UserInfoRespDTO::getId, dto -> dto));
    }
}
