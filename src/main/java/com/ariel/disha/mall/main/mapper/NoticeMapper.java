package com.ariel.disha.mall.main.mapper;

import com.ariel.disha.mall.config.sql.CommonMapper;
import com.ariel.disha.mall.consts.entity.Notice;
import com.ariel.disha.mall.consts.vo.NoticeVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * @author ariel
 * @apiNote NoticeMapper
 * @serial
 */
public interface NoticeMapper extends CommonMapper<Notice> {

    IPage<Notice> noticePage(Page<Notice> page, @Param("vo") NoticeVo vo);
}
