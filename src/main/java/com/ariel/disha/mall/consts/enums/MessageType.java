package com.ariel.disha.mall.consts.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @author ariel
 * @apiNote MessageType
 * @serial
 */
@AllArgsConstructor
@Getter
public enum MessageType {
    SYSTEM(1, null),
    THUMBS_UP(2, ActionType.THUMPS_UP.getVal()),
    FAVORITE(4, ActionType.FAVORITE.getVal()),
    COMMENT(8, null),
    ;
    private final Integer value;
    private final Integer actionValue;

    public static MessageType valueOfActionType(ActionType actionType) {
        if (actionType != null) {
            for (MessageType type : values()) {
                if (Objects.equals(type.getActionValue(), actionType.getVal())) {
                    return type;
                }
            }
        }
        return null;
    }
}
