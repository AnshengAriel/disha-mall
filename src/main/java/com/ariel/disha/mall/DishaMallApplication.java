package com.ariel.disha.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author ariel
 * @apiNote DishaMallApplication
 * @serial
 */
@MapperScan("com.ariel.disha.mall.mapper")
@ComponentScan("com.ariel.easybili")
@EnableAspectJAutoProxy
@SpringBootApplication
public class DishaMallApplication {
    public static void main(String[] args) {
        SpringApplication.run(DishaMallApplication.class, args);
    }
}
