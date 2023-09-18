package com.koi.framework.limitrate.core.service;

import com.koi.framework.limitrate.core.enums.LimitUnitEnum;
import lombok.Data;

/**
 * 限流单位计算
 *
 * @Author zjl
 * @Date 2023/9/18 12:09
 */
@Data
public class LimitTime {
    /**
     * 当前时间
     */
    private Long ts;

    /**
     * 过期时间戳
     */
    private Long expire;

    /**
     * 令牌或漏桶产生的单位间隔
     */
    private Long interval;

    /**
     * 时间间隔比较时间,为0时默认当前时间
     */
    private Long compareTime;

    public LimitTime(Long ts, Long expire, Long intermission, Long compareTime) {
        this.ts = ts;
        this.expire = expire;
        this.interval = intermission;
        this.compareTime = compareTime;
    }

    /**
     * 计算时间范围
     *
     * @param ts 当前时间戳
     * @param interval
     * @param unit
     * @return 时间单位对象
     */
    public static LimitTime calculateTime(long ts, Long interval, LimitUnitEnum unit) {
        LimitTime time = new LimitTime(ts, -1L, interval, ts);
        Long expireTs = unit.getExpireTs(ts);
        time.setExpire(expireTs);
        if (interval < 1000L) {
            time.setInterval(expireTs - ts);
        }

        return time;
    }
}
