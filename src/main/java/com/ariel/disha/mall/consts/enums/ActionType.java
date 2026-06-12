package com.ariel.disha.mall.consts.enums;

import com.ariel.disha.mall.config.exception.AppException;
import com.ariel.disha.mall.consts.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @author ariel
 * @apiNote ActionType
 * @serial
 */
@Getter
@AllArgsConstructor
public enum ActionType {
    THUMPS_UP(1),
    INSERT_COIN(2),
    FAVORITE(4),
    FORWARD(8),
    PLAY(16),
    ;
    private final Integer val;
    public static final int SPECIAL_TYPE = THUMPS_UP.getVal() + INSERT_COIN.getVal() + FAVORITE.getVal();

    public static ActionType valueOf(Integer val) {
        for (ActionType actionType : values()) {
            if (Objects.equals(actionType.getVal(), val)) {
                return actionType;
            }
        }
        return null;
    }

    public static ActionType valueOfOrThrow(Integer val) {
        ActionType actionType = valueOf(val);
        if (actionType == null) {
            throw new AppException(HttpStatus.PARAM_ERROR);
        }
        return actionType;
    }

    public static boolean canDelete(ActionType type) {
        return Objects.equals(THUMPS_UP, type) || Objects.equals(FAVORITE, type);
    }
}
