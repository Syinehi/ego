package com.ego.manage.controller;

import com.ego.commons.pojo.EasyUiTree;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.TbContentCategoryService;
import com.ego.pojo.TbContentCategory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class TbContentCategoryController {
    @Resource
    private TbContentCategoryService tbContentCategoryServiceImpl;

    /**
     * 查询商品类目
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/content/category/list")
    public List<EasyUiTree> showCategory(@RequestParam(defaultValue = "0") long id){
        return tbContentCategoryServiceImpl.showCategory(id);
    }

    /**
     * 新增内容类目
     * @param cate
     * @return
     */
    @RequestMapping("/content/category/create")
    @ResponseBody
    public EgoResult create(TbContentCategory cate){
        return tbContentCategoryServiceImpl.create(cate);
    }

    @ResponseBody
    @RequestMapping("/content/category/update")
    public EgoResult update(TbContentCategory cate){
        return tbContentCategoryServiceImpl.update(cate);
    }

    @ResponseBody
    @RequestMapping("/content/category/delete")
    public EgoResult delete(TbContentCategory cate){
        return tbContentCategoryServiceImpl.delete(cate);
    }
}
