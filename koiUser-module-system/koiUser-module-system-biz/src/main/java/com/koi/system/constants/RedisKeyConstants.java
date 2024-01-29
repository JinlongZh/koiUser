package com.koi.system.constants;

/**
 * Redis Key
 *
 * @Author zjl
 * @Date 2023/8/9 15:18
 */
public class RedisKeyConstants {

    public static final String OAUTH2_ACCESS_TOKEN = "oauth2_access_token:%s";


    public static String formatAccessTokenKey(String accessToken) {
        return String.format(OAUTH2_ACCESS_TOKEN, accessToken);
    }

    /**
     * 未知的
     */
    public static final String UNKNOWN = "未知";

    /**
     * 访客
     */
    public static final String UNIQUE_VISITOR = "unique_visitor";

}
