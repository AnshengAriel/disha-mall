package com.ariel.disha.mall.consts.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author ariel
 * @apiNote Rule
 * @serial
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("rule")
public class Rule implements Serializable {

    @TableId
    private Integer id;

    private Integer pid;

    private String name;

    private Integer type;

    private Integer status;

    private String content;

    private String method;

    private String icon;

    private Integer weight;

    private Long createTime;

    private Long updateTime;

}
