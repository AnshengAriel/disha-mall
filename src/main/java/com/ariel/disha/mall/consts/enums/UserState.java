package com.ariel.disha.mall.consts.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @author ariel
 * @apiNote UserState
 * @serial
 */
@AllArgsConstructor
public enum UserState {
    NORMAL(1),
    DISABLED(2),
    ;
    @Getter
    private final Integer code;

    public static UserState valueOf(Integer state, UserState defaultVal) {
        for (UserState userState : values()) {
            if (Objects.equals(userState.getCode(), state)) {
                return userState;
            }
        }
        return defaultVal;
    }

    public boolean equals(Integer code) {
        return code != null && this.equals(valueOf(code, null));
    }
}
