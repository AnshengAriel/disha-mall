package com.ariel.disha.mall.main.service.impl;

import cn.hutool.core.util.StrUtil;
import com.ariel.disha.mall.consts.HttpResponse;
import com.ariel.disha.mall.consts.HttpStatus;
import com.ariel.disha.mall.consts.entity.Picture;
import com.ariel.disha.mall.consts.vo.PageInfo;
import com.ariel.disha.mall.consts.vo.PictureVo;
import com.ariel.disha.mall.main.mapper.PictureMapper;
import com.ariel.disha.mall.main.service.PictureService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ariel
 * @apiNote PictureServiceImpl
 * @serial
 */
@Slf4j
@Service
public class PictureServiceImpl implements PictureService {

    @Autowired
    private PictureMapper pictureMapper;
    @Autowired
    private BeanFactory beanFactory;

    @Override
    public HttpResponse<Integer> addPicture(PictureVo vo) {
        List<Picture> pictures = vo.getPictures();
        for (Picture picture : pictures) {
            if (StrUtil.isBlank(picture.getName()) || StrUtil.isBlank(picture.getPath())) {
                return HttpResponse.error(HttpStatus.PARAM_ERROR);
            }
            picture.setCategoryId(vo.getCategoryId());
            picture.setCreateTime(System.currentTimeMillis());
            picture.setUpdateTime(System.currentTimeMillis());
        }
        return HttpResponse.insertedOrNot(pictureMapper.wrapper().insertBatch(pictures));
    }

    @Override
    public HttpResponse<Integer> deletePicture(PictureVo vo) {
        return HttpResponse.success(pictureMapper.deleteById(vo.getId()));
    }

    @Override
    public HttpResponse<Integer> updatePicture(PictureVo vo) {
        return HttpResponse.success(pictureMapper.wrapper()
                .setName(vo.getName())
                .eqId(vo.getId())
                .update());
    }

    @Override
    public HttpResponse<PageInfo<Picture>> picturePage(PictureVo vo) {
        IPage<Picture> pictureIPage = pictureMapper.picturePage(vo.asPage(), vo);
        return HttpResponse.success(PageInfo.of(pictureIPage));
    }
}
