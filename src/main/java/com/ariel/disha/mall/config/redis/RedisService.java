package com.ariel.disha.mall.config.redis;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.ariel.disha.mall.consts.dto.LoginDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author ariel
 * @apiNote RedisService
 * @serial
 */
@Getter
@Component
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RedisService<T> {

    public static final RedisService<LoginDto> USER_INFO =
            new RedisService<>("user:info:", 1, TimeUnit.DAYS, LoginDto.class);

    private final String keyPrefix;

    private final long timeout;

    private final TimeUnit timeUnit;

    private final Class<T> castClass;

    private static RedisHelper redisHelper;

    @Autowired
    public RedisService(RedisHelper redisHelper) {
        this(null, 0, null, null);
        RedisService.redisHelper = redisHelper;
    }

    public void setData(Object suffix, T data) {
        redisHelper.set(getKeyPrefix() + suffix, data, getTimeout(), getTimeUnit());
    }

    public T getData(Object suffix) {
        return redisHelper.get(getKeyPrefix() + suffix, getCastClass());
    }

    /**
     * 更新数据的属性，源数据如果是null会被忽略
     * @param suffix redis key后缀
     * @param source 源数据
     */
    public void updateDataProps(Object suffix, Object source) {
        T data = getData(suffix);
        if (data != null) {
            BeanUtil.copyProperties(source, data, CopyOptions.create().setIgnoreNullValue(true));
            setData(suffix, data);
        }
    }

}

