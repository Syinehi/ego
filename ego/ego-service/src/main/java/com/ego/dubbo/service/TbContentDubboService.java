package com.ego.dubbo.service;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbContent;

import java.util.List;

public interface TbContentDubboService {
    /**
     * 分页查询
     * @param categoryId
     * @param page
     * @param rows
     * @return
     */
    EasyUIDataGrid selContentByPage(long categoryId,int page,int rows);

    /**
     * 新增
     * @param content
     * @return
     */
    int insContent(TbContent content);

    /**
     * 查询出最近的前n个t
     * @param count
     * @param isSort
     * @return
     */
    List<TbContent> selByCount(int count,boolean isSort);
}
