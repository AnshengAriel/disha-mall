package com.ariel.disha.mall.consts.dto;

import com.ariel.disha.mall.consts.entity.Rule;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author ariel
 * @apiNote RuleDto
 * @serial
 */
@Getter
@Setter
public class RuleDto extends Rule {

    private List<RuleDto> children;

    private Boolean on;
}
