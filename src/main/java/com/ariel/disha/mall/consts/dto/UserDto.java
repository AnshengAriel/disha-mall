package com.ariel.disha.mall.consts.dto;

import com.ariel.disha.mall.consts.entity.User;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ariel
 * @apiNote UserDto
 * @serial
 */
@Getter
@Setter
public class UserDto extends User {

    private Integer roleId;

    private String roleName;
}
