package com.ariel.disha.mall.consts.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author ariel
 * @apiNote RoleRule
 * @serial
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("role_rule")
public class RoleRule implements Serializable {

    @TableId
    private Integer id;

    private Integer roleId;

    private Integer ruleId;

    private Long createTime;

    private Long updateTime;

}
