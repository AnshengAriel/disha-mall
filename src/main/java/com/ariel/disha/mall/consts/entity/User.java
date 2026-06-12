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
 * @apiNote User
 * @serial
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("user")
public class User implements Serializable {

    @TableId
    private Integer id;

    private Integer type;

    private String name;

    private String password;

    private String avatar;

    private Long createTime;

    private Long updateTime;

}
