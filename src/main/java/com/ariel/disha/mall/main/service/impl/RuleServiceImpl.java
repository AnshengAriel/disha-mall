package com.ariel.disha.mall.main.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ariel.disha.mall.consts.HttpResponse;
import com.ariel.disha.mall.consts.HttpStatus;
import com.ariel.disha.mall.consts.dto.RuleDto;
import com.ariel.disha.mall.consts.entity.Rule;
import com.ariel.disha.mall.consts.enums.RuleStatus;
import com.ariel.disha.mall.consts.enums.RuleType;
import com.ariel.disha.mall.config.validator.group.Content;
import com.ariel.disha.mall.config.validator.group.Icon;
import com.ariel.disha.mall.config.validator.group.Method;
import com.ariel.disha.mall.config.validator.Validator;
import com.ariel.disha.mall.consts.vo.RuleVo;
import com.ariel.disha.mall.main.mapper.RuleMapper;
import com.ariel.disha.mall.main.service.RuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author ariel
 * @apiNote RuleServiceImpl
 * @serial
 */

@Slf4j
@Service
public class RuleServiceImpl implements RuleService {

    @Autowired
    private RuleMapper ruleMapper;

    @Override
    public HttpResponse<Integer> addRule(RuleVo vo) {
        Rule rule = BeanUtil.toBean(vo, Rule.class);
        rule.setStatus(RuleStatus.ABLE.getStatus());
        rule.setCreateTime(System.currentTimeMillis());
        rule.setUpdateTime(System.currentTimeMillis());
        RuleType ruleType = RuleType.valueOf(vo.getType(), RuleType.SERVICE);
        if (ruleType == RuleType.MENU) {
            Validator.validate(vo, Icon.class);
        }else {
            Validator.validate(vo, Method.class, Content.class);
        }
        return HttpResponse.success(ruleMapper.insert(rule));
    }

    @Override
    public HttpResponse<Integer> updateRule(RuleVo vo) {
        if (vo.getId().equals(vo.getPid())) {
            return HttpResponse.error(HttpStatus.PARAM_ERROR);
        }
        Rule rule = BeanUtil.toBean(vo, Rule.class);
        return HttpResponse.success(ruleMapper.updateById(rule));
    }

    @Override
    public HttpResponse<Integer> deleteRule(RuleVo vo) {
        return HttpResponse.success(ruleMapper.deleteById(vo.getId()));
    }

    @Override
    public HttpResponse<List<RuleDto>> ruleList(RuleVo vo) {
        List<Rule> all = ruleMapper.wrapper()
                .configAllowEmptyCondition()
                .inType(RuleType.getTypes(vo.getType()))
                .eqStatus(vo.getStatus())
                .orderAscByWeight()
                .queryList();
        List<RuleDto> result = new ArrayList<>();
        ArrayList<Rule> children = new ArrayList<>();

        // 排除自己的id
        List<Integer> excludeIds = List.of(vo.getExcludeIds());

        // 区分头节点和其他子节点
        all.forEach(rule -> {
            if (!excludeIds.contains(rule.getId())) {
                if (rule.getPid() == null) {
                    result.add(BeanUtil.toBean(rule, RuleDto.class));
                }else if (!excludeIds.contains(rule.getPid())) {
                    children.add(rule);
                }
            }
        });

        // 从头节点开始, 遍历子节点
        result.forEach(ruleDto -> ruleDto.setChildren(toList(children, ruleDto)));

        return HttpResponse.success(result);
    }

    @Override
    public HttpResponse<List<Rule>> ruleItemList(RuleVo vo) {
        List<Rule> items = ruleMapper.wrapper()
                .configAllowEmptyCondition()
                .eqType(vo.getType())
                .eqStatus(vo.getStatus())
                .orderAscByWeight()
                .queryList();
        return HttpResponse.success(items);
    }

    // 递归处理
    private List<RuleDto> toList(ArrayList<Rule> children, RuleDto child) {
        List<RuleDto> dtos = new ArrayList<>();
        Iterator<Rule> iterator = children.iterator();
        while (iterator.hasNext()) {
            Rule rule = iterator.next();
            if (Objects.equals(rule.getPid(), child.getId())) {
                iterator.remove();
                RuleDto dto = BeanUtil.toBean(rule, RuleDto.class);
                dto.setChildren(toList(new ArrayList<>(children), dto));
                dtos.add(dto);
            }
        }
        return dtos.isEmpty() ? null : dtos;
    }
}
