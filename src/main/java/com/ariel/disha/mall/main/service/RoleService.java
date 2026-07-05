package com.ariel.disha.mall.main.service;

import com.ariel.disha.mall.consts.HttpResponse;
import com.ariel.disha.mall.consts.entity.Role;
import com.ariel.disha.mall.consts.vo.PageInfo;
import com.ariel.disha.mall.consts.vo.RoleVo;

import java.util.List;

/**
 * @author ariel
 * @apiNote RoleService
 * @serial
 */
public interface RoleService {

    HttpResponse<Integer> addRole(RoleVo vo);
    HttpResponse<Integer> updateRole(RoleVo vo);
    HttpResponse<Integer> deleteRole(RoleVo vo);
    HttpResponse<PageInfo<Role>> rolePage(RoleVo vo);
    HttpResponse<Integer> updateRoleRule(RoleVo vo);
    HttpResponse<List<Integer>> roleRuleList(RoleVo vo);
}
