package com.ariel.disha.mall.config.intercepter;

import com.ariel.disha.mall.config.redis.RedisHelper;
import com.ariel.disha.mall.config.redis.RedisLock;
import com.ariel.disha.mall.consts.NumberConst;
import com.ariel.disha.mall.consts.SymbolConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.groups.Default;

/**
 * @author ariel
 * @apiNote RedisLockFilter
 * @serial
 */
@Slf4j
@Component
public class RedisLockInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisHelper redisHelper;

    private static final String REDIS_KEY = "RedisLockInterceptor:REDIS_KEY";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            RedisLock redisLock = method.getMethodAnnotation(RedisLock.class);
            if (redisLock != null) {
                StringBuilder builder = new StringBuilder();
                builder.append(request.getServletPath());
                for (Class<? extends Default> aClass : redisLock.value()) {
                    String fieldName = String.valueOf(aClass.getSimpleName().charAt(NumberConst.INT_0)).toLowerCase()
                            + aClass.getSimpleName().substring(NumberConst.INT_1);
                    for (String parameterValue : request.getParameterValues(fieldName)) {
                        builder.append(SymbolConst.COLON).append(parameterValue);
                    }
                }
                String redisKey = builder.toString();
                while (true) {
                    if (redisHelper.tryLock(redisKey, redisLock.timeout())) {
                        log.info("Redis locked success, key[{}]", redisKey);
                        request.setAttribute(REDIS_KEY, redisKey);
                        return true;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        Object key = request.getAttribute(REDIS_KEY);
        if (key instanceof String) {
            redisHelper.unlock(((String) key));
            log.info("Redis unlocked success, key[{}]", key);
        }
    }
}
