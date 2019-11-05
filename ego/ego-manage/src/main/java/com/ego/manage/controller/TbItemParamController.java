package com.ego.manage.controller;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.manage.service.impl.TbItemParamServiceImpl;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
@Controller
public class TbItemParamController {
    @Resource
    private TbItemParamServiceImpl tbItemParamServiceImpl;

    /**
     * 规格参数-分页显示
     * @param page
     * @param rows
     * @return
     */
    @ResponseBody
    @RequestMapping("/item/param/list")
    public EasyUIDataGrid showPage(int page,int rows){
        return tbItemParamServiceImpl.showPage(page,rows);
    }

    /**
     * 批量删除规格参数
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("/item/param/delete")
    public EgoResult delete(String ids){
        EgoResult er = new EgoResult();
        try {
            int index = tbItemParamServiceImpl.delete(ids);
            if (index == 1){
                er.setStatus(200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            er.setData(e.getMessage());
        }
        return er;
    }

    /**
     * 点击商品类目按钮显示添加分组按钮
     * 判断类目是否已经添加模板
     * @param catId
     * @return
     */
    @ResponseBody
    @RequestMapping("/item/param/query/itemcatid/{catId}")
    public EgoResult show(@PathVariable Long catId){
        return tbItemParamServiceImpl.showParam(catId);
    }

    /**
     * 商品类目新增
     * @param param
     * @param catId
     * @return
     */
    @ResponseBody
    @RequestMapping("/item/param/save/{catId}")
    public EgoResult save(TbItemParam param,@PathVariable Long catId){
        param.setItemCatId(catId);
        return tbItemParamServiceImpl.save(param);
    }
}
