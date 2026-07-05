package com.ariel.disha.mall.main.service;

import com.ariel.disha.mall.consts.HttpResponse;
import com.ariel.disha.mall.consts.entity.Notice;
import com.ariel.disha.mall.consts.vo.PageInfo;
import com.ariel.disha.mall.consts.vo.NoticeVo;

/**
 * @author ariel
 * @apiNote NoticeService
 * @serial
 */
public interface NoticeService {

    HttpResponse<Integer> addNotice(NoticeVo vo);
    HttpResponse<Integer> deleteNotice(NoticeVo vo);
    HttpResponse<Integer> updateNotice(NoticeVo vo);
    HttpResponse<PageInfo<Notice>> noticePage(NoticeVo vo);

}
