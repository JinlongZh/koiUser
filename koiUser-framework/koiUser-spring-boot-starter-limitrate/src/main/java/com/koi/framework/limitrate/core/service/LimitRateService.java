package com.koi.framework.limitrate.core.service;

/**
 * -
 *
 * @Author zjl
 * @Date 2023/9/18 11:35
 */
public interface LimitRateService {

    /**
     * 消费指定数量的key
     * @param key 对应key
     * @param num 消费的数量
     * @param ts 最后一次消费时间 毫秒级时间戳
     * @param expire 令牌桶重置时间,时间毫秒数
     * @param rate 速度,令牌token生成的速度或漏桶流速
     * @param interval 间隔,生成令牌的时间间隔,为0则直接增加单位速度量
     * @param capacity 令牌桶或漏桶的最大容量,-1不限制,
     * @param type 限流类型 0-令牌桶,1-漏桶
     * @param compareTime 时间间隔比较时间,为0时默认当前时间 毫秒数
     * @return
     */
    Long consumeCount(String key, Long num, Long ts, Long expire, Long rate, Long interval, Long capacity, int type, Long compareTime);

    /**
     * 查询当前容量,不涉及加减数量
     * @param key 对应key
     * @return 如果不存在返回,或数量为0均返回0,其他 大于0
     */
    Long getCurrentCount(String key);

    /**
     * 增加容量
     * @param key 对应key
     * @param num 增加的数量
     * @param capacity 容量限制  0不限制,大于0 限制
     * @param type 限流类型 0-令牌桶,1-漏桶
     * @return
     */
    Long addCount(String key,long num,long capacity,int type);

}
