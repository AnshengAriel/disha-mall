package com.ariel.disha.mall.main.controller;

import com.ariel.disha.mall.config.validator.group.RuleIds;
import com.ariel.disha.mall.consts.HttpResponse;
import com.ariel.disha.mall.consts.dto.RuleDto;
import com.ariel.disha.mall.consts.entity.Role;
import com.ariel.disha.mall.config.validator.group.ADD;
import com.ariel.disha.mall.config.validator.group.Id;
import com.ariel.disha.mall.consts.vo.PageInfo;
import com.ariel.disha.mall.consts.vo.RoleVo;
import com.ariel.disha.mall.consts.vo.RuleVo;
import com.ariel.disha.mall.main.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ariel
 * @apiNote RoleController
 * @serial
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/add")
    public HttpResponse<Integer> addRole(@Validated(ADD.class) RoleVo vo) {
        return roleService.addRole(vo);
    }

    @PostMapping("/delete")
    public HttpResponse<Integer> deleteRole(@Validated(Id.class) RoleVo vo) {
        return roleService.deleteRole(vo);
    }

    @PostMapping("/update")
    public HttpResponse<Integer> updateRole(@Validated({Id.class, ADD.class}) RoleVo vo) {
        return roleService.updateRole(vo);
    }

    @PostMapping("/page")
    public HttpResponse<PageInfo<Role>> roleList(RoleVo vo) {
        return roleService.rolePage(vo);
    }

    @PostMapping("/update/rule")
    public HttpResponse<Integer> updateRoleRule(@Validated({Id.class, RuleIds.class}) RoleVo vo) {
        return roleService.updateRoleRule(vo);
    }

    @PostMapping("/list/rule")
    public HttpResponse<List<Integer>> roleRuleList(@Validated({Id.class}) RoleVo vo) {
        return roleService.roleRuleList(vo);
    }

}
