package com.koi.common.utils.json;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * JSON 工具类
 *
 * @Author zjl
 * @Date 2023/8/1 15:47
 */
public class JsonUtils {

    /**
     * 将 JSON 字符串转换为 List<String>
     *
     * @param jsonStr JSON 字符串
     * @return List<String>
     */
    public static List<String> stringListFromJson(String jsonStr) {
        JSONArray jsonArray = JSONUtil.parseArray(jsonStr);
        List<String> stringList = new ArrayList<>();
        for (Object obj : jsonArray) {
            stringList.add(obj.toString());
        }
        return stringList;
    }

    /**
     * 将 List<String> 转换为 JSON 字符串
     *
     * @param stringList List<String>
     * @return JSON 字符串
     */
    public static String jsonFromStringList(List<String> stringList) {
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(stringList);
        return jsonArray.toString();
    }
}


