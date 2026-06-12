package com.ariel.disha.mall.consts.dto;

import com.ariel.disha.mall.consts.entity.User;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ariel
 * @apiNote LoginDto
 * @serial
 */
@Getter
@Setter
public class LoginDto extends User {

    private String token;
}
