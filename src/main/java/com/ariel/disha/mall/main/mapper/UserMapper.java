package com.ariel.disha.mall.main.mapper;

import com.ariel.disha.mall.config.sql.CommonMapper;
import com.ariel.disha.mall.consts.dto.UserDto;
import com.ariel.disha.mall.consts.entity.User;
import com.ariel.disha.mall.consts.vo.UserVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * @author ariel
 * @apiNote UserMapper
 * @serial
 */
public interface UserMapper extends CommonMapper<User> {

    IPage<UserDto> userPage(Page<User> page,@Param("vo") UserVo vo);
}
