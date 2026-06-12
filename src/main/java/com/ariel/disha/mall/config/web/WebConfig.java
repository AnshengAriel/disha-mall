package com.ariel.disha.mall.config.web;

import com.ariel.disha.mall.config.intercepter.LoginInterceptor;
import com.ariel.disha.mall.config.intercepter.RedisLockInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ariel
 * @apiNote WebConfig
 * @serial
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private RedisLockInterceptor redisLockInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                //放行哪些原始域
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .exposedHeaders("*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login", "/open/**");
    }

}
