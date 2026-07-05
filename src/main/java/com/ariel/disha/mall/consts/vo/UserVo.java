package com.ariel.disha.mall.consts.vo;

import com.ariel.disha.mall.config.validator.group.*;
import com.ariel.disha.mall.consts.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author ariel
 * @apiNote UserVo
 * @serial
 */
@Getter
@Setter
public class UserVo extends PageVo<User> {

    @NotNull(groups = {Id.class})
    private Integer id;

    private Integer type;

    private Integer status;

    @NotBlank(groups = {Name.class, ADD.class})
    private String name;

    @NotBlank(groups = {Password.class, ADD.class})
    private String password;

    @NotBlank(groups = {Avatar.class, ADD.class})
    private String avatar;

    @NotBlank(groups = {OldPassword.class})
    private String oldPassword;
}
