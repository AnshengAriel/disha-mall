package com.ariel.disha.mall.consts.vo;

import com.ariel.disha.mall.config.validator.group.ADD;
import com.ariel.disha.mall.config.validator.group.Id;
import com.ariel.disha.mall.config.validator.group.Name;
import com.ariel.disha.mall.config.validator.group.Weight;
import com.ariel.disha.mall.consts.entity.PictureCategory;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author ariel
 * @apiNote PictureCategoryVo
 * @serial
 */
@Getter
@Setter
public class PictureCategoryVo extends PageVo<PictureCategory> {

    @NotNull(groups = {Id.class})
    private Integer id;

    @NotBlank(groups = {Name.class, ADD.class})
    private String name;

    @NotNull(groups = {Weight.class, ADD.class})
    private Integer weight;

}
