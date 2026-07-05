package com.ariel.disha.mall.main.controller;

import com.ariel.disha.mall.config.validator.group.ADD;
import com.ariel.disha.mall.config.validator.group.Id;
import com.ariel.disha.mall.config.validator.group.Name;
import com.ariel.disha.mall.consts.HttpResponse;
import com.ariel.disha.mall.consts.entity.PictureCategory;
import com.ariel.disha.mall.consts.vo.PageInfo;
import com.ariel.disha.mall.consts.vo.PictureCategoryVo;
import com.ariel.disha.mall.main.service.PictureCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ariel
 * @apiNote UserController
 * @serial
 */
@RestController
@RequestMapping("/picture/category")
public class PictureCategoryController {

    @Autowired
    private PictureCategoryService pictureCategoryService;

    @PostMapping("/add")
    public HttpResponse<Integer> addPictureCategory(@Validated(ADD.class) PictureCategoryVo vo) {
        return pictureCategoryService.addPictureCategory(vo);
    }

    @PostMapping("/delete")
    public HttpResponse<Integer> deletePictureCategory(@Validated(Id.class) PictureCategoryVo vo) {
        return pictureCategoryService.deletePictureCategory(vo);
    }

    @PostMapping("/update")
    public HttpResponse<Integer> updatePictureCategory(@Validated({Id.class, Name.class}) PictureCategoryVo vo) {
        return pictureCategoryService.updatePictureCategory(vo);
    }
    @PostMapping("/page")
    public HttpResponse<PageInfo<PictureCategory>> pictureCategoryPage(PictureCategoryVo vo) {
        return pictureCategoryService.pictureCategoryPage(vo);
    }

}
