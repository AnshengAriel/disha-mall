package com.ariel.disha.mall.main.service.impl;

import com.ariel.disha.mall.consts.HttpResponse;
import com.ariel.disha.mall.consts.NumberConst;
import com.ariel.disha.mall.consts.entity.PictureCategory;
import com.ariel.disha.mall.consts.vo.PageInfo;
import com.ariel.disha.mall.consts.vo.PictureCategoryVo;
import com.ariel.disha.mall.main.mapper.PictureCategoryMapper;
import com.ariel.disha.mall.main.service.PictureCategoryService;
import com.ariel.disha.mall.util.DateTimeUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ariel
 * @apiNote PictureCategoryServiceImpl
 * @serial
 */
@Slf4j
@Service
public class PictureCategoryServiceImpl implements PictureCategoryService {

    @Autowired
    private PictureCategoryMapper pictureCategoryMapper;

    @Override
    public HttpResponse<Integer> addPictureCategory(PictureCategoryVo vo) {
        PictureCategory pictureCategory = PictureCategory.builder()
                .name(vo.getName())
                .weight(vo.getWeight())
                .createTime(System.currentTimeMillis())
                .updateTime((System.currentTimeMillis()))
                .build();
        return HttpResponse.success(pictureCategoryMapper.insert(pictureCategory));
    }

    @Override
    public HttpResponse<Integer> deletePictureCategory(PictureCategoryVo vo) {
        return HttpResponse.success(pictureCategoryMapper.deleteById(vo.getId()));
    }

    @Override
    public HttpResponse<Integer> updatePictureCategory(PictureCategoryVo vo) {
        return HttpResponse.success(pictureCategoryMapper.wrapper()
                .setName(vo.getName())
                .setUpdateTime()
                .eqId(vo.getId())
                .update());
    }

    @Override
    public HttpResponse<PageInfo<PictureCategory>> pictureCategoryPage(PictureCategoryVo vo) {
        IPage<PictureCategory> iPage = pictureCategoryMapper.pictureCategoryPage(vo.asPage());
        return HttpResponse.success(PageInfo.of(iPage));
    }
}
