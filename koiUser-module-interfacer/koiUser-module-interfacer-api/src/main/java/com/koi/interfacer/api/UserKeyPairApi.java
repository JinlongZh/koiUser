package com.koi.interfacer.api;

import com.koi.interfacer.api.dto.response.UserKeyPairRespDTO;

/**
 * userKeyPair API 接口
 *
 * @Author zjl
 * @Date 2023/8/31 14:34
 */
public interface UserKeyPairApi {

    UserKeyPairRespDTO getUserKeyPairByAccessKey(String accessKey);

}
