package com.ariel.disha.mall.main.mapper;

import com.ariel.disha.mall.config.sql.CommonMapper;
import com.ariel.disha.mall.consts.entity.Picture;
import com.ariel.disha.mall.consts.vo.PictureVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * @author ariel
 * @apiNote UserMapper
 * @serial
 */
public interface PictureMapper extends CommonMapper<Picture> {

    IPage<Picture> picturePage(Page<Picture> page, @Param("vo") PictureVo vo);
}
