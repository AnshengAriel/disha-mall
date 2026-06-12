package com.ariel.disha.mall.config.redis;

import cn.hutool.json.JSONUtil;
import com.ariel.disha.mall.util.CastUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author ariel
 * @apiNote RedisHelper
 * @serial
 */
@Component
public class RedisHelper {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public boolean expire(String key, Long time) {
        return booleanOf(redisTemplate.expire(key, time, TimeUnit.SECONDS));
    }

    public boolean expireDays(String key, Long time) {
        return booleanOf(redisTemplate.expire(key, time, TimeUnit.DAYS));
    }

    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    public void set(String key, Object value, Integer time, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(value), time, timeUnit);
    }

    public void set(String key, Object value, Long time, TimeUnit timeUnit) {
        set(key, value, time.intValue(), timeUnit);
    }

    public void set(String key, Object value, Integer time) {
        set(key, value, time, TimeUnit.SECONDS);
    }

    public boolean setnx(String key, Object value, Integer time) {
        return setnx(key, value, time, TimeUnit.SECONDS);
    }

    public boolean setnx(String key, Object value, Integer time, TimeUnit timeUnit) {
        return booleanOf(redisTemplate.opsForValue().setIfAbsent(key, JSONUtil.toJsonStr(value), time, timeUnit));
    }

    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public <T> T get(String key, Class<T> tClass) {
        return CastUtil.cast(get(key), tClass);
    }

    public String delete(String key) {
        return redisTemplate.opsForValue().getAndDelete(key);
    }

    public <T> T delete(String key, Class<T> tClass) {
        return CastUtil.cast(delete(key), tClass);
    }

    public Long increment(String key, Supplier<Integer> supplier) {
        Long value = redisTemplate.opsForValue().increment(key);
        if (value == null || value == 1) {
            value = supplier.get().longValue();
            // 1用于判空，从2开始计数
            value = value  > 1 ? value + 1 : 2;
            redisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(value));
        }
        return value;
    }

    public boolean hset(String key, String hashKey, Object value) {
        return redisTemplate.<String, String>opsForHash().putIfAbsent(key, hashKey, JSONUtil.toJsonStr(value));
    }

    public String hget(String key, String hashKey) {
        return redisTemplate.<String, String>opsForHash().get(key, hashKey);
    }

    public <T> T hget(String key, String hashKey, Class<T> tClass) {
        return CastUtil.cast(hget(key, hashKey), tClass);
    }

    public Long lpush(String key, Object value) {
        return redisTemplate.opsForList().leftPush(key, JSONUtil.toJsonStr(value));
    }

    public Long sadd(String key, Object value) {
        return redisTemplate.opsForSet().add(key, JSONUtil.toJsonStr(value));
    }

    public Long sremove(String key, Object... values) {
        return redisTemplate.opsForSet().remove(key, values);
    }

    public Set<String> sget(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    public Boolean del(String key) {
        return redisTemplate.delete(key);
    }

    public String rpop(String key) {
        return redisTemplate.opsForList().rightPop(key);
    }

    public List<String> rpop(String key, Long count) {
        return redisTemplate.opsForList().rightPop(key, count);
    }

    public <T> T rpop(String key, Class<T> tClass) {
        return CastUtil.cast(rpop(key), tClass);
    }

    public <T> List<T> rpop(String key, Long count, Class<T> tClass) {
        return CastUtil.cast(rpop(key, count), tClass);
    }

    public boolean zadd(String key, String value, Long score) {
        return zadd(key, value, score.doubleValue());
    }

    public boolean zadd(String key, String value, Double score) {
        return booleanOf(redisTemplate.opsForZSet().add(key, value, score));
    }

    public boolean zaddIfAbsent(String key, String value, Long score) {
        return zaddIfAbsent(key, value, score.doubleValue());
    }

    public boolean zaddIfAbsent(String key, String value, Double score) {
        return booleanOf(redisTemplate.opsForZSet().addIfAbsent(key, value, score));
    }

    public Long zinc(String key, String value, Long score) {
        return zinc(key, value, score.doubleValue()).longValue();
    }

    public Double zinc(String key, String value, Double score) {
        return redisTemplate.opsForZSet().incrementScore(key, value, score);
    }

    public Set<String> zreverseRange(String key, Long start, Long end) {
        return redisTemplate.opsForZSet().reverseRange(key, start, end);
    }

    public Set<String> zrangeByScore(String key, Long start, Long end) {
        return redisTemplate.opsForZSet().rangeByScore(key, start, end);
    }

    public Set<String> zreverseRangeByScore(String key, Long start, Long end) {
        return redisTemplate.opsForZSet().reverseRangeByScore(key, start, end);
    }

    public long zrmRangeByScore(String key, Long min, Long max) {
        return zrmRangeByScore(key, min.doubleValue(), max.doubleValue());
    }

    public long zrmRangeByScore(String key, Double min, Double max) {
        return longOf(redisTemplate.opsForZSet().removeRangeByScore(key, min, max));
    }

    public Long zrm(String key, Object... values) {
        return redisTemplate.opsForZSet().remove(key, values);
    }

    public long zcard(String key) {
        return longOf(redisTemplate.opsForZSet().zCard(key));
    }
    
    private long longOf(Long l) {
        return l != null ? l : 0;
    }
    
    public boolean booleanOf(Boolean b) {
        return b != null && b;
    }

    public boolean tryLock(String key, long timeout) {
        Boolean locked = redisTemplate.opsForValue().setIfAbsent(key, String.valueOf(System.currentTimeMillis()), timeout, TimeUnit.MILLISECONDS);
        return booleanOf(locked);
    }

    public void unlock(String key) {
        redisTemplate.delete(key);
    }

    public <T,R> R lockClient(String key, Supplier<T> getData, Predicate<T> doubleCheck, Function<T, R> checkedTask, Supplier<R> orElseTask) {
        T t = getData.get();
        // 双检
        if (doubleCheck.test(t)) {
            return checkedTask.apply(t);
        }
        while (true) {
            if (tryLock(key, 1000)) {
                t = getData.get();
                // 双检
                if (doubleCheck.test(t)) {
                    return checkedTask.apply(t);
                }else {
                    return orElseTask.get();
                }
            }
        }
    }
}
