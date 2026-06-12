package com.ariel.disha.mall.consts.enums;

import com.ariel.disha.mall.config.exception.AppException;
import com.ariel.disha.mall.consts.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @author ariel
 * @apiNote StatsType
 * @serial
 */
@Getter
@AllArgsConstructor
public enum StatsType {
    USER(1, null),
    PLAY(2, ActionType.PLAY),
    COMMENT(4, null),
    BULLET(8, null),
    THUMPS_UP(16, ActionType.THUMPS_UP),
    FAVORITE(32, ActionType.FAVORITE),
    INSERT_COIN(64, ActionType.INSERT_COIN),
    FORWARD(128, ActionType.FORWARD),
    ;
    private final Integer val;
    private final ActionType actionType;

    public static StatsType valueOf(Integer val) {
        for (StatsType statsType : values()) {
            if (Objects.equals(statsType.getVal(), val)) {
                return statsType;
            }
        }
        return null;
    }

    public static StatsType valueOfOrThrow(Integer val) {
        StatsType statsType = valueOf(val);
        if (statsType == null) {
            throw new AppException(HttpStatus.PARAM_ERROR);
        }
        return statsType;
    }

    public static StatsType valueOfActionType(ActionType actionType) {
        for (StatsType statsType : values()) {
            if (statsType.getActionType() == actionType) {
                return statsType;
            }
        }
        throw new AppException(HttpStatus.PARAM_ERROR);
    }
}
