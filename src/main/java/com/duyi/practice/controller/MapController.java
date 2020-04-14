package com.duyi.practice.controller;

import com.duyi.practice.bean.DataBean;
import com.duyi.practice.bean.MapBean;
import com.duyi.practice.data.DataHandler;
import com.duyi.practice.service.DataService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MapController {
    @Autowired
    private DataService dataService;

    @GetMapping("/map")
    //@ResponseBody
    public String show(Model model){
        List<DataBean> list = dataService.list();
        List<MapBean> result = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
             DataBean dataBean = list.get(i);
             MapBean mapBean = new MapBean(dataBean.getArea(),dataBean.getConfirm());
             result.add(mapBean);
        }
        model.addAttribute("mapData",new Gson().toJson(result));
        return "map";
    }

}
