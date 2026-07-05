package com.ariel.disha.mall.main.controller;

import com.ariel.disha.mall.config.app.AppProperties;
import com.ariel.disha.mall.config.validator.group.ADD;
import com.ariel.disha.mall.config.validator.group.CategoryId;
import com.ariel.disha.mall.config.validator.group.Id;
import com.ariel.disha.mall.config.validator.group.Name;
import com.ariel.disha.mall.consts.*;
import com.ariel.disha.mall.consts.entity.Picture;
import com.ariel.disha.mall.consts.vo.PageInfo;
import com.ariel.disha.mall.consts.vo.PictureVo;
import com.ariel.disha.mall.main.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ariel
 * @apiNote UserController
 * @serial
 */
@RestController
@RequestMapping("/picture")
public class PictureController {

    @Autowired
    private PictureService pictureService;
    @Autowired
    private AppProperties appProperties;

    @PostMapping("/add")
    public HttpResponse<Integer> addPicture(@Validated(ADD.class) @RequestBody PictureVo vo) {
        return pictureService.addPicture(vo);
    }

    @PostMapping("/delete")
    public HttpResponse<Integer> deletePicture(@Validated(Id.class) PictureVo vo) {
        return pictureService.deletePicture(vo);
    }

    @PostMapping("/update")
    public HttpResponse<Integer> updatePicture(@Validated({Id.class, Name.class}) PictureVo vo) {
        return pictureService.updatePicture(vo);
    }
    @PostMapping("/page")
    public HttpResponse<PageInfo<Picture>> picturePage(@Validated(CategoryId.class) PictureVo vo) {
        return pictureService.picturePage(vo);
    }

}
