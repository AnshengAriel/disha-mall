package com.ariel.disha.mall.config.redis;

import javax.validation.groups.Default;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ariel
 * @apiNote RedisLock
 * @serial
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisLock {

    /**
     * 锁key
     */
    Class<? extends Default>[] value();

    /**
     * 超时时间, 秒
     */
    long timeout() default 10;
}
