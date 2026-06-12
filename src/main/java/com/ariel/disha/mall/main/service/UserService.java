package com.ariel.disha.mall.main.service;

import com.ariel.disha.mall.consts.HttpResponse;
import com.ariel.disha.mall.consts.dto.LoginDto;
import com.ariel.disha.mall.consts.vo.UserVo;

/**
 * @author ariel
 * @apiNote UserService
 * @serial
 */
public interface UserService {

    HttpResponse<LoginDto> login(UserVo vo);
}
