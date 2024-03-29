package com.ego.item.controller;

import com.ego.item.service.TbItemCatService;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class TbItemCatController {
    @Resource
    private TbItemCatService tbItemCatServiceImpl;

    /**
     * 返回jsonp数据格式包含所有菜单信息
     * @param callback
     * @return
     */
    @ResponseBody
    @RequestMapping("/rest/itemcat/all")
    public MappingJacksonValue showMenu(String callback){
        MappingJacksonValue mjv = new MappingJacksonValue(tbItemCatServiceImpl.showCatMenu());
        mjv.setJsonpFunction(callback);
        return mjv;
    }
}
