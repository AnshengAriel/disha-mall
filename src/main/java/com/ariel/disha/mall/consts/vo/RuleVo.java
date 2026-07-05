package com.ariel.disha.mall.consts.vo;

import com.ariel.disha.mall.config.validator.group.*;
import com.ariel.disha.mall.consts.entity.Rule;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author ariel
 * @apiNote RuleVo
 * @serial
 */
@Getter
@Setter
public class RuleVo extends PageVo<Rule> {

    @NotNull(groups = {Id.class})
    private Integer id;

    @NotNull(groups = {Pid.class})
    private Integer pid;

    @NotBlank(groups = {Name.class, ADD.class})
    private String name;

    @NotNull(groups = {Type.class, ADD.class})
    private Integer type;

    @NotNull(groups = {Status.class})
    private Integer status;

    @NotNull(groups = {Weight.class, ADD.class})
    private Integer weight;

    @NotBlank(groups = {Content.class})
    private String content;

    @NotBlank(groups = {Method.class})
    private String method;

    @NotBlank(groups = {Icon.class})
    private String icon;

    private Integer[] excludeIds = {};

}
