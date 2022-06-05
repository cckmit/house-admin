package com.house.common.bean;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
public class PageResultBean<T> extends ResultBean<T> implements Serializable {

    /**
     * 总记录数
     */
    private long totalRecord;

    /**
     * 总页数
     */
    private long pageCount;

    /**
     * 当前页码
     */
    private long pageNo;

    /**
     * 当前页的记录数量
     */
    private long pageSize;

    public static <T> PageResultBean<List<T>> buildIPage(IPage<T> page) {
        PageResultBean<List<T>> pageResultBean = new PageResultBean<>();
        pageResultBean.setPageNo(page.getCurrent());
        pageResultBean.setPageSize(page.getSize());
        pageResultBean.setPageCount(page.getPages());
        pageResultBean.setTotalRecord(page.getTotal());
        pageResultBean.setData(page.getRecords());
        return pageResultBean;
    }
}
