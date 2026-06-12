package com.ariel.disha.mall.consts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author ariel
 * @apiNote TokenVo
 * @serial
 */
@Getter
@Setter
public class TokenVo implements Serializable {
    /**
     * id
     */
    private String accountId;
    /**
     * 名称
     */
    private String accountName;
    /**
     * 是管理员吗
     */
    private Integer accountType;
    /**
     * 过期时间
     */
    private Long expireTime;

    @JsonIgnore
    public Integer getIntId() {
        return Integer.valueOf(getAccountId());
    }

    @JsonIgnore
    public Integer getIntIdExcludeRoot() {
        return Objects.equals(getAccountType(), AccountType.ROOT.getVal()) ? null : getIntId();
    }
}
