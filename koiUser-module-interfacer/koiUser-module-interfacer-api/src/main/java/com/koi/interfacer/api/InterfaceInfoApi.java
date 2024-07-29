package com.koi.interfacer.api;

import com.koi.interfacer.api.dto.response.InterfaceInfoRespDTO;

/**
 * InterfaceInfo API 接口
 *
 * @Author zjl
 * @Date 2023/9/1 15:23
 */
public interface InterfaceInfoApi {

    InterfaceInfoRespDTO getInterfaceInfo(String path, String method);

}
