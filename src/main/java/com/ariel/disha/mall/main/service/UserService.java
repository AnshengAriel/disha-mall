package com.ariel.disha.mall.main.service;

import com.ariel.disha.mall.consts.HttpResponse;
import com.ariel.disha.mall.consts.dto.LoginDto;
import com.ariel.disha.mall.consts.dto.UserDto;
import com.ariel.disha.mall.consts.vo.PageInfo;
import com.ariel.disha.mall.consts.vo.UserVo;

/**
 * @author ariel
 * @apiNote UserService
 * @serial
 */
public interface UserService {

    HttpResponse<LoginDto> login(UserVo vo);

    HttpResponse<Integer> updatePassword(UserVo vo);

    HttpResponse<Integer> addUser(UserVo vo);

    HttpResponse<Integer> updateUser(UserVo vo);

    HttpResponse<Integer> deleteUser(UserVo vo);

    HttpResponse<PageInfo<UserDto>> userPage(UserVo vo);
}
