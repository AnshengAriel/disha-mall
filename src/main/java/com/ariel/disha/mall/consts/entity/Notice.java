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
 * @apiNote Notice
 * @serial
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("notice")
public class Notice implements Serializable {

    @TableId
    private Integer id;

    private String title;

    private Integer type;

    private String content;

    private Long createTime;

    private Long updateTime;

}
