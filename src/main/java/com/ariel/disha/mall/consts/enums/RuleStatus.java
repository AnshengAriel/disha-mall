package com.ariel.disha.mall.consts.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @author ariel
 * @apiNote RuleStatus
 * @serial
 */
@Getter
@AllArgsConstructor
public enum RuleStatus {

    DISABLE(0),
    ABLE(1),
    ;
    private final Integer status;

    public static RuleStatus valueOf(Integer status) {
        for (RuleStatus ruleType : values()) {
            if (Objects.equals(ruleType.getStatus(), status)) {
                return ruleType;
            }
        }
        return null;
    }

    public static RuleStatus valueOf(Integer status, RuleStatus defaultType) {
        RuleStatus ruleType = valueOf(status);
        return ruleType != null ? ruleType : defaultType;
    }

}
