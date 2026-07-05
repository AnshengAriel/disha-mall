package com.ariel.disha.mall.main.service;

import com.ariel.disha.mall.consts.HttpResponse;
import com.ariel.disha.mall.consts.dto.RuleDto;
import com.ariel.disha.mall.consts.entity.Rule;
import com.ariel.disha.mall.consts.vo.RuleVo;

import java.util.List;

/**
 * @author ariel
 * @apiNote RuleService
 * @serial
 */
public interface RuleService {

    HttpResponse<Integer> addRule(RuleVo vo);
    HttpResponse<Integer> updateRule(RuleVo vo);
    HttpResponse<Integer> deleteRule(RuleVo vo);
    HttpResponse<List<RuleDto>> ruleList(RuleVo vo);
    HttpResponse<List<Rule>> ruleItemList(RuleVo vo);
}
