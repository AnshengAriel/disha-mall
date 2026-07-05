package com.ariel.disha.mall.main.mapper;

import com.ariel.disha.mall.config.sql.CommonMapper;
import com.ariel.disha.mall.consts.entity.PictureCategory;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author ariel
 * @apiNote UserMapper
 * @serial
 */
public interface PictureCategoryMapper extends CommonMapper<PictureCategory> {

    IPage<PictureCategory> pictureCategoryPage(Page<PictureCategory> page);
}
