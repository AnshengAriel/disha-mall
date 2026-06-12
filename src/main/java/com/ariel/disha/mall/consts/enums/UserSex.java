package com.ariel.disha.mall.consts.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ariel
 * @apiNote UserSex
 * @serial
 */
@AllArgsConstructor
public enum UserSex {
    FEMALE(1),
    MALE(2),
    SECRET(4),
    ;
    @Getter
    private final int code;
}
