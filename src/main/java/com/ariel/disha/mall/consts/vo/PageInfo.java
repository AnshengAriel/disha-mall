package com.ariel.disha.mall.consts.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author ariel
 * @apiNote PageInfo
 * @serial
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageInfo<T> implements Serializable {

    private Integer pageNum = 1;

    private Integer pageSize = 10;

    private List<T> list;

    private Long total;

    private Boolean hasNext;

    public static <T> PageInfo<T> of(IPage<T> iPage) {
        PageInfo<T> pageInfo = new PageInfo<>();
        pageInfo.setPageNum((int) iPage.getCurrent());
        pageInfo.setPageSize((int) iPage.getSize());
        pageInfo.setTotal(iPage.getTotal());
        pageInfo.setHasNext(iPage.getCurrent() < iPage.getPages());
        pageInfo.setList(iPage.getRecords());
        return pageInfo;
    }

}
