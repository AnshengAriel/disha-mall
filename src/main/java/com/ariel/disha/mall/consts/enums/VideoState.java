package com.ariel.disha.mall.consts.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author ariel
 * @apiNote VideoState
 * @serial
 */
@Getter
@AllArgsConstructor
public enum VideoState {
    NOT_TRANSCODED(1),
    TRANSCODE_SUCCESS(2),
    AUDITED(4),
    AUDITED_FAIL(8),
    DELETED(16),
    ;
    private final Integer value;

    public static VideoState of(Integer state) {
        for (VideoState value : values()) {
            if (Objects.equals(value.getValue(), state)) {
                return value;
            }
        }
        return NOT_TRANSCODED;
    }

    public static Integer[] valuesOf(Integer state) {
        if (state == null) {
            return null;
        }
        List<Integer> list = new ArrayList<>();
        for (VideoState videoState : values()) {
            if ((videoState.getValue() & state) == videoState.getValue()) {
                list.add(videoState.getValue());
            }
        }
        return list.toArray(new Integer[0]);
    }

    public static Integer isAudit(Boolean audit) {
        return Boolean.TRUE.equals(audit) ? AUDITED.getValue() : AUDITED_FAIL.getValue();
    }
}
