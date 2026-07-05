package com.ariel.disha.mall.main.mapper;

import com.ariel.disha.mall.config.sql.CommonMapper;
import com.ariel.disha.mall.consts.entity.Role;
import com.ariel.disha.mall.consts.vo.RoleVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

/**
 * @author ariel
 * @apiNote RuleMapper
 * @serial
 */
public interface RoleMapper extends CommonMapper<Role> {

    IPage<Role> rolePage(IPage<Role> page, @Param("vo") RoleVo vo);

}
