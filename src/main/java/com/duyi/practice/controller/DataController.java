package com.duyi.practice.controller;

import com.duyi.practice.bean.DataBean;
import com.duyi.practice.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class DataController {

    @Autowired
    private DataService dataService;


    /*@PathVariable从路径中获取参数值,/{id}
    * */
    @GetMapping("/list")
    public String list(Model model){
        //接收到数据
        List<DataBean> list = dataService.list();
        //将数据传给页面展示,使用视图模型参数
        model.addAttribute("dataList",list);
        return "list";
    }



}
