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
 * @apiNote Picture
 * @serial
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("picture")
public class Picture implements Serializable {

    @TableId
    private Integer id;

    private Integer categoryId;

    private String path;

    private String name;

    private Long createTime;

    private Long updateTime;

}
