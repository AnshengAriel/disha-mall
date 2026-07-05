package com.ariel.disha.mall.consts.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author ariel
 * @apiNote ActionType
 * @serial
 */
@Getter
@AllArgsConstructor
public enum RuleType {

    MENU(1),
    PAGE(2),
    SERVICE(4),
    ;
    private final Integer type;

    public static RuleType valueOf(Integer type) {
        for (RuleType ruleType : values()) {
            if (Objects.equals(ruleType.getType(), type)) {
                return ruleType;
            }
        }
        return null;
    }

    public static RuleType valueOf(Integer type, RuleType defaultType) {
        RuleType ruleType = valueOf(type);
        return ruleType != null ? ruleType : defaultType;
    }

    public static Integer[] getTypes(Integer type) {
        Integer[] r = {};
        if (type == null) {
            return r;
        }
        List<Integer> list = new ArrayList<>();
        for (RuleType ruleType : values()) {
            if ((ruleType.getType() & type) == ruleType.getType()) {
                list.add(ruleType.getType());
            }
        }
        return list.toArray(r);
    }
}
