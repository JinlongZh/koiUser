package com.koi.interfacer.api;

import com.koi.interfacer.enums.ApiConstants;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * interfaceInfo API 接口
 *
 * @Author zjl
 * @Date 2023/8/31 14:34
 */
@FeignClient(name = ApiConstants.NAME)
public interface UserInfoApi {

    String PREFIX = ApiConstants.PREFIX + "/interface";



}
