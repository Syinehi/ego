package com.ego.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ego.commons.pojo.EasyUiTree;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.utils.IDUtils;
import com.ego.dubbo.service.TbContentCategoryDubboService;
import com.ego.manage.service.TbContentCategoryService;
import com.ego.pojo.TbContentCategory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TbContentCategoryServiceImpl implements TbContentCategoryService {
    @Reference
    private TbContentCategoryDubboService tbContentCategoryDubboServiceImpl;
    @Override
    public List<EasyUiTree> showCategory(long id) {
        List<EasyUiTree> listTree = new ArrayList<EasyUiTree>();
        List<TbContentCategory> list = tbContentCategoryDubboServiceImpl.selByPid(id);
        for (TbContentCategory cate : list) {
            EasyUiTree tree = new EasyUiTree();

            tree.setId(cate.getId());
            tree.setText(cate.getName());
            tree.setState(cate.getIsParent()?"closed":"open");

            listTree.add(tree);
        }
        return listTree;
    }

    @Override
    public EgoResult create(TbContentCategory cate) {
        EgoResult er = new EgoResult();
        List<TbContentCategory> children = tbContentCategoryDubboServiceImpl.selByPid(cate.getParentId());
        for (TbContentCategory child : children) {
            if (cate.getName().equals(child.getName())){
                er.setData("该分类名已存在");
                return er;
            }
        }

        Date date = new Date();
        cate.setCreated(date);
        cate.setUpdated(date);
        cate.setStatus(1);
        cate.setSortOrder(1);
        cate.setIsParent(false);
        long id = IDUtils.genItemId();
        cate.setId(id);
        int index = tbContentCategoryDubboServiceImpl.insTbContentCategory(cate);
        if (index > 0){
            TbContentCategory parent = new TbContentCategory();
            parent.setId(cate.getParentId());
            parent.setIsParent(true);
            tbContentCategoryDubboServiceImpl.updIsParentById(parent);
        }
        er.setStatus(200);
        Map<String,Long> map = new HashMap<>();
        map.put("id",id);
        er.setData(id);
        return er;
    }

    @Override
    public EgoResult update(TbContentCategory cate) {
        EgoResult er = new EgoResult();
        //查询当前节点信息
        TbContentCategory cateSelect = tbContentCategoryDubboServiceImpl.selById(cate.getId());
        //通过当前节点的父节点id查询所有子节点信息
        List<TbContentCategory> children = tbContentCategoryDubboServiceImpl.selByPid(cateSelect.getParentId());
        for (TbContentCategory child : children) {
            if (cate.getName().equals(child.getName())){
                er.setData("该分类名已存在");
                return er;
            }
        }
        int index = tbContentCategoryDubboServiceImpl.updIsParentById(cate);
        if (index > 0){
            er.setStatus(200);
        }
        return er;
    }

    @Override
    public EgoResult delete(TbContentCategory cate) {
        EgoResult er = new EgoResult();
        cate.setStatus(0);
        int index = tbContentCategoryDubboServiceImpl.updIsParentById(cate);
        if (index > 0){
            TbContentCategory ccate = tbContentCategoryDubboServiceImpl.selById(cate.getId());
            List<TbContentCategory> listCate = tbContentCategoryDubboServiceImpl.selByPid(ccate.getParentId());
            if (listCate.size() == 0||listCate == null){
                TbContentCategory parent = new TbContentCategory();
                parent.setId(ccate.getParentId());
                parent.setIsParent(false);
                int result = tbContentCategoryDubboServiceImpl.updIsParentById(parent);
                if (result > 0){
                    er.setStatus(200);
                    return er;
                }
            }else {
                er.setStatus(200);
            }
        }
        return er;
    }
}
