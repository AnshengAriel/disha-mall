package com.ariel.disha.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author ariel
 * @apiNote DishaMallApplication
 * @serial ("com.ariel.disha.mall.mapper")
 */
@MapperScan("com.ariel.disha.mall.main.mapper")
@ComponentScan("com.ariel.disha.mall")
@ServletComponentScan("com.ariel.disha.mall.config.filter")
@EnableAspectJAutoProxy
@SpringBootApplication
public class DishaMallApplication {
    public static void main(String[] args) {
        SpringApplication.run(DishaMallApplication.class, args);
    }
}
