package com.koi.framework.limitrate.core.utils;

import com.koi.common.utils.http.IpUtils;
import com.koi.framework.limitrate.core.enums.LimitTypeEnum;
import com.koi.framework.limitrate.core.enums.LimitUnitEnum;
import com.koi.framework.limitrate.core.service.LimitRateService;
import com.koi.framework.limitrate.core.service.LimitTime;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 限流工具类
 *
 * @Author zjl
 * @Date 2023/9/18 12:04
 */
@RequiredArgsConstructor
public class LimitRateUtil {

    /**
     * 机器ID限流
     **/
    public static String MACHINE_ID = "";
    /**
     * 全局限流
     */
    public static String GLOBAL = "limit:00:";

    static {
        String localIpAddress = IpUtils.getLocalIpAddress();
        if (!StringUtils.isEmpty(localIpAddress)) {
            MACHINE_ID += localIpAddress.replace(".", "_").replace(":", "_");
        }
        MACHINE_ID += "_" + System.currentTimeMillis();
        MACHINE_ID = "limit:m" + Math.abs(MACHINE_ID.hashCode()) + ":";
    }

    private final LimitRateService limitRateService;


//    /**
//     * @param request
//     * @param key key
//     * @param rate 令牌生成速率
//     * @param interval 令牌或漏桶产生的单位间隔, 小于1000毫秒则采用单位时间
//     * @param unit 单位时间内无操作，重置令牌桶
//     * @param type
//     * @param capacity
//     * @return
//     */
//    public Long consumeCount(HttpServletRequest request, String key, Long rate, Long interval, LimitUnitEnum unit, LimitTypeEnum type, Long capacity) {
//        // 获取前缀
//        String keySuffix = getKeySuffix(type, request);
//        long ts = System.currentTimeMillis();
//        // 计算时间单位
//        LimitTime time = LimitTime.calculateTime(ts, interval, unit);
//        return consumeCount(keySuffix + key, 1L, rate, type, capacity, time);
//    }

    /**
     * 消耗次数
     *
     * @param key key
     * @param num 消费数
     * @param rate 令牌生成速率
     * @param type 类型
     * @param capacity 容量
     * @param time
     * @return
     */
    public Long consumeCount(String key, Long num, Long rate, LimitTypeEnum type, Long capacity, LimitTime time) {
        if (capacity < 1) {
            capacity = rate;
        }
        return limitRateService.consumeCount(key, num, time.getTs(), time.getExpire(), rate, time.getInterval(), capacity, type.getCode(), time.getCompareTime());
    }


    /**
     * 获取前缀
     *
     * @param type
     * @param request
     * @return
     */
    public String getKeySuffix(LimitTypeEnum type, HttpServletRequest request) {
        if (LimitTypeEnum.GLOBAL_LEAKY.equals(type) || LimitTypeEnum.GLOBAL_TOKEN.equals(type)) {
            return GLOBAL;
        } else if (LimitTypeEnum.APP_LEAKY.equals(type) || LimitTypeEnum.APP_TOKEN.equals(type)) {
            return MACHINE_ID;
        } else {
            String ipAddress = IpUtils.getIpAddress(request);
            return "limit:i" + ipAddress.replace(".", "_").replace(":", "_") + ":";
        }
    }

    /**
     * 添加count 用于回滚
     *
     * @param key
     * @param num
     * @param capacity
     * @param type
     * @return
     */
    public Long addCount(String key, Long num, Long capacity, LimitTypeEnum type) {
        return limitRateService.addCount(key, num, capacity, type.getCode());
    }

    public static void main(String[] args) {
        System.out.println(MACHINE_ID);
    }

}
