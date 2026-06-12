package com.ariel.disha.mall.consts.vo;

import com.ariel.disha.mall.consts.entity.User;
import com.ariel.disha.mall.consts.validate.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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

    @NotBlank(groups = {Name.class, ADD.class})
    private String name;

    @NotBlank(groups = {Password.class, ADD.class})
    private String password;

    @NotBlank(groups = {Token.class})
    private String token;

    private String avatar;
}
