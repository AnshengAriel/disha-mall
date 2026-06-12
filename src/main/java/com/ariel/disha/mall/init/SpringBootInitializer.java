package com.ariel.disha.mall.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author ariel
 * @apiNote SpringBootInitializer
 * @serial
 */
@Slf4j
@Component
public class SpringBootInitializer implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("App started. args[{}]", args);
    }
}
