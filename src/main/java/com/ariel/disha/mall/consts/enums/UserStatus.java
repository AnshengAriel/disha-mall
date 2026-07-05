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
public enum UserStatus {
    DISABLED(0),
    NORMAL(1),
    ;
    @Getter
    private final Integer code;

    public static UserStatus valueOf(Integer state, UserStatus defaultVal) {
        for (UserStatus userStatus : values()) {
            if (Objects.equals(userStatus.getCode(), state)) {
                return userStatus;
            }
        }
        return defaultVal;
    }

    public static UserStatus valueOf(Integer state) {
        return valueOf(state, null);
    }

    public boolean equals(Integer code) {
        return code != null && this.equals(valueOf(code, null));
    }
}
