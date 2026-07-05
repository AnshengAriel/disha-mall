package com.ariel.disha.mall.main.controller;

import cn.hutool.json.JSONUtil;
import com.ariel.disha.mall.main.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author ariel
 * @apiNote UserController
 * @serial
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private UserService userService;

    @GetMapping("/1")
    public String test() {
        return "Success";
    }

    @PostMapping("/post")
    public String post(Map<String, Object> params) {
        log.info("prams[{}]", JSONUtil.toJsonStr(params));
        return "Success";
    }
}
