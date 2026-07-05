package com.ariel.disha.mall.consts.vo;

import com.ariel.disha.mall.config.validator.group.*;
import com.ariel.disha.mall.consts.NumberConst;
import com.ariel.disha.mall.consts.entity.Notice;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author ariel
 * @apiNote NoticeVo
 * @serial
 */
@Getter
@Setter
public class NoticeVo extends PageVo<Notice> {

    @NotNull(groups = {Id.class})
    private Integer id;

    @NotBlank(groups = {Title.class, ADD.class})
    private String title;

    @NotBlank(groups = {Content.class, ADD.class})
    private String content;

    @NotBlank(groups = {Type.class})
    private Integer type = NumberConst.INT_0;

}
