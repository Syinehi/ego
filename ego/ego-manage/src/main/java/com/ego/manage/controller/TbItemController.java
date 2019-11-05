package com.ego.manage.controller;

import com.ego.commons.pojo.EgoResult;
import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.manage.service.TbItemService;
import com.ego.pojo.TbItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
@Controller
public class TbItemController{
    @Resource
    private TbItemService tbItemServiceImpl;

    /**
     * 分页查询
     * @param page
     * @param rows
     * @return
     */
    @ResponseBody
    @RequestMapping("/item/list")
    public EasyUIDataGrid show(int page, int rows){
        return tbItemServiceImpl.show(page,rows);
    }

    /**
     * 显示商品修改
     * @return
     */
    @RequestMapping("/rest/page/item-edit")
    public String showItemEdit(){
        return "item-edit";
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("/rest/item/delete")
    public EgoResult delete(String ids){
        EgoResult er = new EgoResult();
        int index = tbItemServiceImpl.update(ids, (byte) 3);
        if (index == 1){
            er.setStatus(200);
        }
        return er;
    }

    /**
     * 下架商品
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("/rest/item/reshelf")
    public EgoResult reshelf(String ids){
        EgoResult er = new EgoResult();
        int index = tbItemServiceImpl.update(ids, (byte) 1);
        if (index == 1){
            er.setStatus(200);
        }
        return er;
    }

    /**
     * 商品下架
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping("/rest/item/instock")
    public EgoResult instock(String ids){
        EgoResult er = new EgoResult();
        int index = tbItemServiceImpl.update(ids, (byte) 2);
        if (index == 1){
            er.setStatus(200);
        }
        return er;
    }

    /**
     * 商品新增
     * @param item
     * @param desc
     * @return
     */
    @RequestMapping("/item/save")
    @ResponseBody
    public EgoResult insert(TbItem item,String desc,String itemParams){
        EgoResult er = new EgoResult();
        int index;
        try {
            index = tbItemServiceImpl.save(item, desc,itemParams);
            if(index==1){
                er.setStatus(200);
            }
        } catch (Exception e) {
//			e.printStackTrace();
            er.setData(e.getMessage());
        }
        return er;
    }

}
