package com.ariel.disha.mall.consts.enums;

import com.ariel.disha.mall.config.exception.AppException;
import com.ariel.disha.mall.consts.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @author ariel
 * @apiNote CommentActionType
 * @serial
 */
@Getter
@AllArgsConstructor
public enum CommentActionType {
    THUMPS_UP(1),
    THUMPS_DOWN(2),
    ;
    private final Integer val;

    public static CommentActionType valueOf(Integer val) {
        for (CommentActionType actionType : values()) {
            if (Objects.equals(actionType.getVal(), val)) {
                return actionType;
            }
        }
        return null;
    }

    public static CommentActionType valueOfOrThrow(Integer val) {
        CommentActionType actionType = valueOf(val);
        if (actionType == null) {
            throw new AppException(HttpStatus.PARAM_ERROR);
        }
        return actionType;
    }

}
