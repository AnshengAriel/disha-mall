package com.ariel.disha.mall.main.controller;

import com.ariel.disha.mall.consts.HttpResponse;
import com.ariel.disha.mall.consts.dto.LoginDto;
import com.ariel.disha.mall.consts.validate.CheckCode;
import com.ariel.disha.mall.consts.validate.Name;
import com.ariel.disha.mall.consts.validate.Nickname;
import com.ariel.disha.mall.consts.validate.Password;
import com.ariel.disha.mall.consts.vo.UserVo;
import com.ariel.disha.mall.main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ariel
 * @apiNote UserController
 * @serial
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public HttpResponse<LoginDto> login(@Validated(LOGIN.class) UserVo vo) {
        return userService.login(vo);
    }


    interface LOGIN extends Name, Password {}
}
