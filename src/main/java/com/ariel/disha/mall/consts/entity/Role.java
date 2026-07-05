package com.ariel.disha.mall.consts.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author ariel
 * @apiNote Role
 * @serial
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("role")
public class Role implements Serializable {

    @TableId
    private Integer id;

    private String name;

    @TableField(value = "`desc`")
    private String desc;

    private Integer status;

    private Long createTime;

    private Long updateTime;

}
