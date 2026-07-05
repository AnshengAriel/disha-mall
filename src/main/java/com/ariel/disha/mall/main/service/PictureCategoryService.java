package com.ariel.disha.mall.main.service;

import com.ariel.disha.mall.consts.HttpResponse;
import com.ariel.disha.mall.consts.entity.PictureCategory;
import com.ariel.disha.mall.consts.vo.PageInfo;
import com.ariel.disha.mall.consts.vo.PictureCategoryVo;

/**
 * @author ariel
 * @apiNote PictrueCategoryService
 * @serial
 */
public interface PictureCategoryService {

    HttpResponse<Integer> addPictureCategory(PictureCategoryVo vo);
    HttpResponse<Integer> deletePictureCategory(PictureCategoryVo vo);
    HttpResponse<Integer> updatePictureCategory(PictureCategoryVo vo);
    HttpResponse<PageInfo<PictureCategory>> pictureCategoryPage(PictureCategoryVo vo);
}
