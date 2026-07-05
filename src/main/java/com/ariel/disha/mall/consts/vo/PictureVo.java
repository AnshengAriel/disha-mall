package com.ariel.disha.mall.consts.vo;

import com.ariel.disha.mall.config.validator.group.ADD;
import com.ariel.disha.mall.config.validator.group.CategoryId;
import com.ariel.disha.mall.config.validator.group.Id;
import com.ariel.disha.mall.config.validator.group.Name;
import com.ariel.disha.mall.consts.NumberConst;
import com.ariel.disha.mall.consts.entity.Picture;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author ariel
 * @apiNote PictureVo
 * @serial
 */
@Getter
@Setter
public class PictureVo extends PageVo<Picture> {

    @NotNull(groups = {Id.class})
    private Integer id;

    @NotNull(groups = {CategoryId.class, ADD.class})
    private Integer categoryId;

    private String path;

    @NotBlank(groups = {Name.class})
    private String name;

    @NotNull(groups = {ADD.class})
    @Size(min = NumberConst.INT_1, groups = {ADD.class})
    private List<Picture> pictures;

}
