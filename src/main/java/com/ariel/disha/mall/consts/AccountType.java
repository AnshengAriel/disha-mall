package com.ariel.disha.mall.consts;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ariel
 * @apiNote AccountType
 * @serial
 */
@AllArgsConstructor
public enum AccountType {
    ROOT(1), USER(2)
    ;
    @Getter
    private final Integer val;
}
