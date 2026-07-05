package com.ariel.disha.mall.main.mapper;

import com.ariel.disha.mall.config.sql.CommonMapper;
import com.ariel.disha.mall.consts.dto.RuleDto;
import com.ariel.disha.mall.consts.entity.RoleRule;
import com.ariel.disha.mall.consts.vo.RoleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ariel
 * @apiNote RuleMapper
 * @serial
 */
public interface RoleRuleMapper extends CommonMapper<RoleRule> {

    List<RuleDto> ruleList(@Param("vo") RoleVo vo);
}
