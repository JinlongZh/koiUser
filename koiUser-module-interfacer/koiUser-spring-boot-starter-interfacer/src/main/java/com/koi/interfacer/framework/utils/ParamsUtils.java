package com.koi.interfacer.framework.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 请求参数工具类
 *
 * @Author zjl
 * @Date 2023/9/7 11:43
 */
public class ParamsUtils {

    /**
     * 实现将参数字符串转换为Map< string, string >的逻辑 <br>
     * 例如：key1=value1&key2=value2&key3=value3
     *
     * @param params
     * @Return Map<String, String>
     */
    public static Map<String, String> convertParamsToMap(String params) {
        Map<String, String> paramsMap = new HashMap<>();
        String[] keyValuePairs = params.split("&");
        for (String pair : keyValuePairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                String key = keyValue[0];
                String value = keyValue[1];
                paramsMap.put(key, value);
            }
        }
        return paramsMap;
    }

}
