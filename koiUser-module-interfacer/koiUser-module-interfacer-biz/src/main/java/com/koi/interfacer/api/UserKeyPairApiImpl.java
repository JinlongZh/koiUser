package com.koi.interfacer.api;

import com.koi.common.domain.CommonResult;
import com.koi.common.utils.bean.BeanCopyUtils;
import com.koi.interfacer.api.dto.response.UserKeyPairRespVO;
import com.koi.interfacer.domain.entity.UserKeyPair;
import com.koi.interfacer.service.UserKeyPairService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 提供 RESTful API 接口，给 Feign 调用
 *
 * @Author zjl
 * @Date 2023/8/31 15:12
 */
@RestController
@Validated
public class UserKeyPairApiImpl implements UserKeyPairApi{

    @Resource
    private UserKeyPairService userKeyPairService;

    @Override
    public CommonResult<UserKeyPairRespVO> getUserKeyPairByAccessKey(String accessKey) {
        UserKeyPair userKeyPair = userKeyPairService.getUserKeyPairByAccessKey(accessKey);
        return CommonResult.success(BeanCopyUtils.copyObject(userKeyPair, UserKeyPairRespVO.class));
    }
}
