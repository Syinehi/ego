package com.ego.dubbo.service;

import com.ego.pojo.TbContentCategory;

import java.util.List;

public interface TbContentCategoryDubboService {
    /**
     * 根据父id查询所有子类目
     * @param id
     * @return
     */
    List<TbContentCategory> selByPid(long id);

    /**
     * 新增
     * @param cate
     * @return
     */
    int insTbContentCategory(TbContentCategory cate);

    /**
     * 修改isParent通过id
     * @param cate
     * @return
     */
    int updIsParentById(TbContentCategory cate);

    /**
     * 根据id查询内容类目详细信息
     */
    TbContentCategory selById(long id);
}
