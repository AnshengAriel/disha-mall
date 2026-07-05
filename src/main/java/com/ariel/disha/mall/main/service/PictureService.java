package com.ariel.disha.mall.main.service;

import com.ariel.disha.mall.consts.HttpResponse;
import com.ariel.disha.mall.consts.entity.Picture;
import com.ariel.disha.mall.consts.vo.PageInfo;
import com.ariel.disha.mall.consts.vo.PictureVo;

/**
 * @author ariel
 * @apiNote PictureService
 * @serial
 */
public interface PictureService {

    HttpResponse<Integer> addPicture(PictureVo vo);
    HttpResponse<Integer> deletePicture(PictureVo vo);
    HttpResponse<Integer> updatePicture(PictureVo vo);
    HttpResponse<PageInfo<Picture>> picturePage(PictureVo vo);

}
