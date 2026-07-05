package com.ariel.disha.mall.main.controller;

import com.ariel.disha.mall.config.validator.group.*;
import com.ariel.disha.mall.consts.HttpResponse;
import com.ariel.disha.mall.consts.dto.LoginDto;
import com.ariel.disha.mall.consts.dto.UserDto;
import com.ariel.disha.mall.consts.vo.PageInfo;
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

    @PostMapping("/update/password")
    public HttpResponse<Integer> updatePassword(@Validated({Password.class, OldPassword.class}) UserVo vo) {
        return userService.updatePassword(vo);
    }

    @PostMapping("/add")
    public HttpResponse<Integer> addUser(@Validated({ADD.class}) UserVo vo) {
        return userService.addUser(vo);
    }

    @PostMapping("/update")
    public HttpResponse<Integer> updateUser(@Validated({Id.class, ADD.class}) UserVo vo) {
        return userService.updateUser(vo);
    }

    @PostMapping("/delete")
    public HttpResponse<Integer> deleteUser(@Validated(Id.class) UserVo vo) {
        return userService.deleteUser(vo);
    }

    @PostMapping("/page")
    public HttpResponse<PageInfo<UserDto>> userPage(UserVo vo) {
        return userService.userPage(vo);
    }

    interface LOGIN extends Name, Password {}
}
