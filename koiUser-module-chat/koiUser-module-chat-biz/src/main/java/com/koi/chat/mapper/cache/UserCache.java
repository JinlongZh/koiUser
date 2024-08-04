package com.koi.chat.mapper.cache;

import com.koi.framework.redis.core.constant.RedisKey;
import com.koi.framework.redis.core.utils.RedisUtils;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * 用户缓存
 *
 * @Author zjl
 * @Date 2023/10/6 21:42
 */
@Repository
public class UserCache {


    /**
     * 用户上线
     *
     * @param uid     uid
     * @param optTime 选择时间
     */
    public void online(Long uid, Date optTime) {
        String onlineKey = RedisKey.getKey(RedisKey.ONLINE_UID_ZET);
        //更新上线表
        RedisUtils.zAdd(onlineKey, uid, optTime.getTime());
    }

    /**
     * 是否在线
     *
     * @param uid uid
     * @return boolean
     */
    public boolean isOnline(Long uid) {
        String onlineKey = RedisKey.getKey(RedisKey.ONLINE_UID_ZET);
        return RedisUtils.zIsMember(onlineKey, uid);
    }


}
