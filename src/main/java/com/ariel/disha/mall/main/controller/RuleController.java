package com.ariel.disha.mall.main.controller;

import com.ariel.disha.mall.consts.HttpResponse;
import com.ariel.disha.mall.consts.dto.RuleDto;
import com.ariel.disha.mall.config.validator.group.ADD;
import com.ariel.disha.mall.config.validator.group.Id;
import com.ariel.disha.mall.consts.entity.Rule;
import com.ariel.disha.mall.consts.vo.RuleVo;
import com.ariel.disha.mall.main.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ariel
 * @apiNote RuleController
 * @serial
 */
@RestController
@RequestMapping("/rule")
public class RuleController {

    @Autowired
    private RuleService ruleService;

    @PostMapping("/add")
    public HttpResponse<Integer> addPicture(@Validated(ADD.class) RuleVo vo) {
        return ruleService.addRule(vo);
    }

    @PostMapping("/delete")
    public HttpResponse<Integer> deletePicture(@Validated(Id.class) RuleVo vo) {
        return ruleService.deleteRule(vo);
    }

    @PostMapping("/update")
    public HttpResponse<Integer> updatePicture(@Validated({Id.class, ADD.class}) RuleVo vo) {
        return ruleService.updateRule(vo);
    }

    @PostMapping("/list")
    public HttpResponse<List<RuleDto>> ruleList(RuleVo vo) {
        return ruleService.ruleList(vo);
    }

    @PostMapping("/list/item")
    public HttpResponse<List<Rule>> ruleItemList(RuleVo vo) {
        return ruleService.ruleItemList(vo);
    }

}
