package com.ariel.disha.mall.main.controller;

import com.ariel.disha.mall.config.app.AppProperties;
import com.ariel.disha.mall.consts.HttpResponse;
import com.ariel.disha.mall.consts.entity.Notice;
import com.ariel.disha.mall.config.validator.group.ADD;
import com.ariel.disha.mall.config.validator.group.Id;
import com.ariel.disha.mall.consts.vo.PageInfo;
import com.ariel.disha.mall.consts.vo.NoticeVo;
import com.ariel.disha.mall.main.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ariel
 * @apiNote UserController
 * @serial
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeService NoticeService;
    @Autowired
    private AppProperties appProperties;

    @PostMapping("/add")
    public HttpResponse<Integer> addNotice(@Validated(ADD.class) NoticeVo vo) {
        return NoticeService.addNotice(vo);
    }

    @PostMapping("/delete")
    public HttpResponse<Integer> deleteNotice(@Validated(Id.class) NoticeVo vo) {
        return NoticeService.deleteNotice(vo);
    }

    @PostMapping("/update")
    public HttpResponse<Integer> updateNotice(@Validated({Id.class, ADD.class}) NoticeVo vo) {
        return NoticeService.updateNotice(vo);
    }
    @PostMapping("/page")
    public HttpResponse<PageInfo<Notice>> noticePage(NoticeVo vo) {
        return NoticeService.noticePage(vo);
    }

}
