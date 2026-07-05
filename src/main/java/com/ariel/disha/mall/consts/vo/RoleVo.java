package com.ariel.disha.mall.consts.vo;

import com.ariel.disha.mall.config.validator.group.*;
import com.ariel.disha.mall.consts.entity.Role;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author ariel
 * @apiNote RoleVo
 * @serial
 */
@Getter
@Setter
public class RoleVo extends PageVo<Role> {

    @NotNull(groups = {Id.class})
    private Integer id;

    @NotBlank(groups = {Name.class, ADD.class})
    private String name;

    @NotBlank(groups = {Desc.class, ADD.class})
    private String desc;

    @NotNull(groups = {Status.class, ADD.class})
    private Integer status;

    @NotNull(groups = {RuleIds.class})
    private List<Integer> ruleIds;
}
