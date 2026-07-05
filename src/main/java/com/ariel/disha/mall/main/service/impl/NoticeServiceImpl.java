package com.ariel.disha.mall.main.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ariel.disha.mall.consts.HttpResponse;
import com.ariel.disha.mall.consts.entity.Notice;
import com.ariel.disha.mall.consts.vo.NoticeVo;
import com.ariel.disha.mall.consts.vo.PageInfo;
import com.ariel.disha.mall.main.mapper.NoticeMapper;
import com.ariel.disha.mall.main.service.NoticeService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ariel
 * @apiNote NoticeServiceImpl
 * @serial
 */
@Slf4j
@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public HttpResponse<Integer> addNotice(NoticeVo vo) {
        Notice notice = BeanUtil.toBean(vo, Notice.class);
        notice.setCreateTime(System.currentTimeMillis());
        notice.setUpdateTime(System.currentTimeMillis());
        return HttpResponse.success(noticeMapper.insert(notice));
    }

    @Override
    public HttpResponse<Integer> deleteNotice(NoticeVo vo) {
        return HttpResponse.success(noticeMapper.deleteById(vo.getId()));
    }

    @Override
    public HttpResponse<Integer> updateNotice(NoticeVo vo) {
        return HttpResponse.success(noticeMapper.wrapper()
                .setTitle(vo.getTitle())
                .setContent(vo.getContent())
                .setUpdateTime()
                .eqId(vo.getId())
                .update());
    }

    @Override
    public HttpResponse<PageInfo<Notice>> noticePage(NoticeVo vo) {
        IPage<Notice> noticeIPage = noticeMapper.noticePage(vo.asPage(), vo);
        return HttpResponse.success(PageInfo.of(noticeIPage));
    }
}
