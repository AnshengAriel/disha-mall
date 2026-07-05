package com.ariel.disha.mall.main.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ariel.disha.mall.consts.HttpResponse;
import com.ariel.disha.mall.consts.NumberConst;
import com.ariel.disha.mall.consts.dto.RuleDto;
import com.ariel.disha.mall.consts.entity.Role;
import com.ariel.disha.mall.consts.entity.RoleRule;
import com.ariel.disha.mall.consts.entity.Rule;
import com.ariel.disha.mall.consts.vo.PageInfo;
import com.ariel.disha.mall.consts.vo.RoleVo;
import com.ariel.disha.mall.main.mapper.RoleMapper;
import com.ariel.disha.mall.main.mapper.RoleRuleMapper;
import com.ariel.disha.mall.main.service.RoleService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ariel
 * @apiNote RoleServiceImpl
 * @serial
 */
@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleRuleMapper roleRuleMapper;

    @Override
    public HttpResponse<Integer> addRole(RoleVo vo) {
        Role role = BeanUtil.toBean(vo, Role.class);
        role.setCreateTime(System.currentTimeMillis());
        role.setUpdateTime(System.currentTimeMillis());
        return HttpResponse.success(roleMapper.insert(role));
    }

    @Override
    public HttpResponse<Integer> updateRole(RoleVo vo) {
        Role role = BeanUtil.toBean(vo, Role.class);
        role.setUpdateTime(System.currentTimeMillis());
        return HttpResponse.success(roleMapper.updateById(role));
    }

    @Override
    public HttpResponse<Integer> deleteRole(RoleVo vo) {
        int deleted = roleMapper.deleteById(vo.getId());
        roleRuleMapper.wrapper()
                .eqRoleId(vo.getId())
                .delete();
        return HttpResponse.success(deleted);
    }

    @Override
    public HttpResponse<PageInfo<Role>> rolePage(RoleVo vo) {
        IPage<Role> iPage = roleMapper.rolePage(vo.asPage(), vo);
        return HttpResponse.success(PageInfo.of(iPage));
    }

    @Override
    public HttpResponse<Integer> updateRoleRule(RoleVo vo) {
        List<Integer> ruleIds = vo.getRuleIds();
        List<RoleRule> roleRules = roleRuleMapper.wrapper()
                .eqRoleId(vo.getId())
                .queryList();

        // 被删除的部分
        int updated = NumberConst.INT_0;
        List<Integer> deleteIds = new ArrayList<>();
        for (RoleRule roleRule : roleRules) {
            int i = ruleIds.indexOf(roleRule.getRuleId());
            if (i == NumberConst.INT_1_NEG) {
                deleteIds.add(roleRule.getId());
            }else {
                ruleIds.remove(i);
            }
        }
        if (!deleteIds.isEmpty()) {
            updated += roleRuleMapper.deleteBatchIds(deleteIds);
        }

        // 新增的部分
        roleRules = ruleIds.stream().map(id ->
                RoleRule.builder()
                .ruleId(id)
                .roleId(vo.getId())
                .createTime(System.currentTimeMillis())
                .updateTime(System.currentTimeMillis())
                .build()).collect(Collectors.toList());
        updated += roleRuleMapper.wrapper().insertBatch(roleRules);
        return HttpResponse.success(updated);
    }

    @Override
    public HttpResponse<List<Integer>> roleRuleList(RoleVo vo) {
        List<Integer> list = roleRuleMapper.wrapper()
                .selectRuleId()
                .eqRoleId(vo.getId())
                .queryList()
                .stream()
                .map(RoleRule::getRuleId)
                .collect(Collectors.toList());
        return HttpResponse.success(list);
    }
}
