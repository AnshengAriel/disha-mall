package com.ariel.disha.mall.main.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ariel.disha.mall.config.app.AppProperties;
import com.ariel.disha.mall.config.redis.RedisService;
import com.ariel.disha.mall.consts.HttpConst;
import com.ariel.disha.mall.consts.HttpResponse;
import com.ariel.disha.mall.consts.HttpStatus;
import com.ariel.disha.mall.consts.TokenVo;
import com.ariel.disha.mall.consts.dto.LoginDto;
import com.ariel.disha.mall.consts.dto.UserDto;
import com.ariel.disha.mall.consts.entity.User;
import com.ariel.disha.mall.consts.enums.UserStatus;
import com.ariel.disha.mall.consts.vo.PageInfo;
import com.ariel.disha.mall.consts.vo.UserVo;
import com.ariel.disha.mall.main.mapper.UserMapper;
import com.ariel.disha.mall.main.service.UserService;
import com.ariel.disha.mall.util.JwtUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author ariel
 * @apiNote UserServiceImpl
 * @serial
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AppProperties appProperties;

    @Override
    public HttpResponse<LoginDto> login(UserVo vo) {
        User user = userMapper.wrapper()
                .eqName(vo.getName())
                .eqPassword(vo.getPassword())
                .queryOne();
        if (user == null) {
            return HttpResponse.error(HttpStatus.USER_NOT_EXISTS_OR_PASSWORD_WRONG);
        }
        // 封装token
        TokenVo tokenVo = new TokenVo();
        tokenVo.setAccountId(user.getId().toString());
        tokenVo.setAccountName(user.getName());
        tokenVo.setAccountType(user.getType());
        tokenVo.setExpireTime(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1));
        String token = JwtUtil.encodeToken(tokenVo, appProperties.getSecret());

        LoginDto dto = BeanUtil.toBean(user, LoginDto.class);
        dto.setToken(token);
        dto.setPassword(null);
        RedisService.USER_INFO.setData(user.getId(), dto);
        return HttpResponse.success(dto);
    }

    @Override
    public HttpResponse<Integer> updatePassword(UserVo vo) {
        return HttpResponse.updatedOrNot(userMapper.wrapper()
                .eqId(vo.getAccountId())
                .eqPassword(vo.getOldPassword())
                .setPassword(vo.getPassword())
                .update());
    }

    @Override
    public HttpResponse<Integer> addUser(UserVo vo) {
        User user = BeanUtil.toBean(vo, User.class);
        user.setCreateTime(System.currentTimeMillis());
        user.setUpdateTime(System.currentTimeMillis());
        return HttpResponse.success(userMapper.insert(user));
    }

    @Override
    public HttpResponse<Integer> updateUser(UserVo vo) {
        if (vo.getStatus() != null && UserStatus.valueOf(vo.getStatus()) == null) {
            return HttpResponse.error(HttpStatus.PARAM_ERROR);
        }
        return HttpResponse.success(userMapper.wrapper()
                .eqId(vo.getAccountId())
                .setName(vo.getName())
                .setAvatar(vo.getAvatar())
                .setStatus(vo.getStatus())
                .update());
    }

    @Override
    public HttpResponse<Integer> deleteUser(UserVo vo) {
        return HttpResponse.success(userMapper.deleteById(vo.getId()));
    }

    @Override
    public HttpResponse<PageInfo<UserDto>> userPage(UserVo vo) {
        IPage<UserDto> iPage = userMapper.userPage(vo.asPage(), vo);
        return HttpResponse.success(PageInfo.of(iPage));
    }


}
