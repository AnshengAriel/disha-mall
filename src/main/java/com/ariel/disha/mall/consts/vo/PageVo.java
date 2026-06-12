package com.ariel.disha.mall.consts.vo;

import com.ariel.disha.mall.consts.NumberConst;
import com.ariel.disha.mall.consts.TokenVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;

/**
 * @author ariel
 * @apiNote PageVo
 * @serial
 */
@Getter
@Setter
public class PageVo<T> extends TokenVo {

    private Integer pageNum = 1;

    private Integer pageSize = 10;

    public Page<T> asPage() {
        Page<T> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        return page;
    }

    public PageRequest asPageRequest() {
        return PageRequest.of(pageNum - NumberConst.INT_1, pageSize);
    }

    public static <E> Page<E> asPage(PageVo<?> vo) {
        PageVo<E> page = new PageVo<>();
        page.setPageNum(vo.getPageNum());
        page.setPageSize(vo.getPageSize());
        return page.asPage();
    }

    /**
     * 不优化查询语句，可以跳过语句分析，防止报错
     */
    public Page<T> asNormalPage() {
        Page<T> page = asPage();
        page.setOptimizeCountSql(false);
        return page;
    }
}
