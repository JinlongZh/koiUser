package com.koi.framework.limitrate.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.Collections;

/**
 * -
 *
 * @Author zjl
 * @Date 2023/9/18 11:36
 */
@RequiredArgsConstructor
public class LimitRateServiceImpl implements LimitRateService{

    private final RedisTemplate<String, Object> redisTemplate;

    private static final String CONSUME_TOKEN_SCRIP = "local r_key = tostring(KEYS[1]);\n" +
            "local num = tonumber(ARGV[1]);\n" +
            "local interval = tonumber(ARGV[2]);\n" +
            "local rate = tonumber(ARGV[3]);\n" +
            "local capacity = tonumber(ARGV[4]);\n" +
            "local compareTime = tonumber(ARGV[5]);\n" +
            "local ts = tonumber(ARGV[6]);\n" +
            "local expire = tonumber(ARGV[7]);\n" +
            "local haskey=redis.call('exists', r_key);" +
            "if tonumber(haskey)==1 then\n " +
            "    local gen_ts = redis.call('hget', r_key, 'gen_ts');\n" +
            "    local count = redis.call('hget', r_key, 'count');\n" +
            "    local factor = math.ceil((compareTime - gen_ts) / interval - 1);\n" +
            "    if -factor > 0 then\n" +
            "        local factor = 0 \n" +
            "    end;\n" +
            "    local add_count = factor * rate;\n" +
            "    if add_count > 0 then\n" +
            "        count = count + add_count;\n" +
            "        if capacity > 0 and count > capacity then\n" +
            "            count = capacity;\n" +
            "        end;\n" +
            "        redis.call('hset', r_key, 'count', count);\n" +
            "        redis.call('hset', r_key, 'gen_ts', ts);\n" +
            "        redis.call('pexpireat', r_key, expire);\n" +
            "    end; \n" +
            "    if (count - num) > -1 then\n" +
            "        redis.call('hset', r_key, 'count', count - num);\n" +
            "        redis.call('hset', r_key, 'get_ts', ts);\n" +
            "        return (count - num);\n" +
            "    else \n" +
            "        return -1;\n" +
            "    end;\n" +
            "else\n " +
            "    redis.call('hset', r_key, 'gen_ts', ts);\n" +
            "    redis.call('hset', r_key, 'count', capacity - num);\n" +
            "    redis.call('hset', r_key, 'get_ts', ts);\n" +
            "    redis.call('pexpireat', r_key, expire); \n" +
            "    return (capacity - num);\n" +
            " end;";

    /**
     * 增加令牌数量
     */
    private static final String ADD_TOKEN_SCRIP = "local num = tonumber(ARGV[1]);\n" +
            "local capacity=tonumber(ARGV[2]);\n"
            + "if redis.call('exists',KEYS[1])=='1' then" +
            "local count = redis.call('hget',KEYS[1], 'count');" +
            "count=count+num;" +
            "if  capacity >0 and count>capacity then count=capacity; end;" +
            "local res=redis.call('hset',KEYS[1], 'count',count);" +
            " return res;" +
            "else  return -1; end;";
    /**
     * 漏桶redis脚本ARGV[1]:num ARGV[2]:interval,ARGV[3]:rate,ARGV[4]:capacity ARGV[5]:compareTime ARGV[6]:ts ARGV[7]:expire
     **/
    private static final String CONSUME_LEAKY_SCRIP = "local r_key = tostring(KEYS[1]);\n" +
            "local num = tonumber(ARGV[1]);\n" +
            "local interval = tonumber(ARGV[2]);\n" +
            "local rate = tonumber(ARGV[3]);\n" +
            "local capacity = tonumber(ARGV[4]);\n" +
            "local compareTime = tonumber(ARGV[5]);\n" +
            "local ts = tonumber(ARGV[6]);\n" +
            "local expire = tonumber(ARGV[7]);\n" +
            "local haskey=redis.call('exists', r_key);\n" +
            "if tonumber(haskey)==1 then \n" +
            "   local gen_ts = redis.call('hget', r_key, 'gen_ts');\n" +
            "   local count = redis.call('hget', r_key, 'count');\n" +
            "   local factor = math.ceil((compareTime - gen_ts) / interval - 1);\n" +
            "   if -factor > 0 then\n" +
            "       local factor = 0 \n" +
            "   end;\n" +
            "   local de_count = factor * rate;\n" +
            "   if de_count > 0 then\n" +
            "       count = count - de_count;\n" +
            "       if -count>0 then\n" +
            "           count=0;\n" +
            "       end;\n" +
            "       redis.call('hset', r_key, 'count', count);\n" +
            "       redis.call('hset', r_key, 'gen_ts', ts);\n" +
            "       redis.call('pexpireat', r_key, expire);\n" +
            "   end;\n" +
            "    local total=(count+num);\n" +
            "   if capacity-total >-1  then\n" +
            "       redis.call('hset', r_key, 'count', total);\n" +
            "       redis.call('hset', r_key, 'get_ts', ts);\n" +
            "       return (capacity - total);\n" +
            "   else \n" +
            "       return -1;\n" +
            "   end;\n" +
            "else \n" +
            "   redis.call('hset', r_key, 'gen_ts', ts);\n" +
            "   redis.call('hset', r_key, 'count', num);\n" +
            "   redis.call('hset', r_key, 'get_ts', ts);\n" +
            "   redis.call('pexpireat', r_key, expire); \n" +
            "   return (capacity - num);\n" +
            "end;";
    /**
     * 减少漏桶数量
     */
    private static final String DEDUCE_LEAKY_SCRIP = "local num = tonumber(ARGV[1]);\n" +
            "local capacity=tonumber(ARGV[2]);\n" +
            "if redis.call('exists',KEYS[1])=='1' then" +
            "local count = redis.call('hget',KEYS[1], 'count');" +
            "count=count-num;" +
            "if  count <0 then count=0; end;" +
            "local res=redis.call('hset',KEYS[1], 'count',count);" +
            " return capacity-res;" +
            "else  return -1; end;";


    /**
     * 消费指定数量的key
     *
     * @param key         对应key
     * @param num         消费的数量
     * @param ts          最后一次消费时间
     * @param expire      令牌桶重置时间,多久没操作会重置,毫秒数
     * @param rate        速度,令牌token生成的速度或漏桶流速
     * @param interval    间隔,生成令牌的时间,为0则直接增加单位速度量
     * @param capacity    令牌桶或漏桶的最大容量,-1不限制,
     * @param type        限流类型 0-令牌桶,1-漏桶
     * @param compareTime 时间间隔比较时间,为0时默认当前时间
     * @return
     */
    @Override
    public Long consumeCount(String key, Long num, Long ts, Long expire, Long rate, Long interval, Long capacity, int type, Long compareTime) {
        long millis = System.currentTimeMillis();
        compareTime = compareTime < 1 ? millis : compareTime;
        ts = ts < 1 ? millis : ts;

        RedisScript<Long> redisScript = new DefaultRedisScript<>(type == 0 ? CONSUME_TOKEN_SCRIP : CONSUME_LEAKY_SCRIP, Long.class);

        return redisTemplate.execute(redisScript, Collections.singletonList(key), num, interval, rate, capacity, compareTime, ts, expire);
    }

    /**
     * 查询当前容量,不涉及加减数量
     *
     * @param key 对应key
     * @return 如果不存在返回, 或数量为0均返回0, 其他 大于0
     */
    public Long getCurrentCount(String key) {
        Object count = redisTemplate.opsForHash().get(key, "count");
        if (count != null) {
            return Long.valueOf(count.toString());
        }
        return 0L;
    }

    /**
     * 增加容量
     *
     * @param key      对应key
     * @param num      增加的令牌数量或减少漏桶中的数量
     * @param capacity 容量限制  对于令牌桶 小于1 不限制,大于0 限制,对于漏桶 为剩余最小值,建议为0
     * @param type     限流类型 0-令牌桶,1-漏桶
     * @return 如果key 过期则返回-1
     */
    @Override
    public Long addCount(String key, long num, long capacity, int type) {
        RedisScript<Long> redisScript = new DefaultRedisScript<>(type == 0 ? ADD_TOKEN_SCRIP : DEDUCE_LEAKY_SCRIP, Long.class);

        return redisTemplate.execute(redisScript, Collections.singletonList(key), num, capacity);
    }

}
