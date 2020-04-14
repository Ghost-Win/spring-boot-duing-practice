package com.duyi.practice.controller;

import com.duyi.practice.bean.GraphBean;
import com.duyi.practice.data.DataHandler;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GraphController {
    @GetMapping("/graph")
    public String graph(Model model){

        List<GraphBean> list = DataHandler.getGraphData();
        
        List<String> dateList = new ArrayList<>();
        List<Integer> confirmList = new ArrayList<>();
        List<Integer> suspectList = new ArrayList<>();

        //遍历集合，给图形所需三组数据赋值
        for (int i = 0; i < list.size(); i++) {
            GraphBean graphBean = list.get(i);
            dateList.add(graphBean.getDate());
            confirmList.add(graphBean.getConfirm());
            suspectList.add(graphBean.getSuspect());
        }

        model.addAttribute("dateList",new Gson().toJson(dateList));
        model.addAttribute("confirmList",new Gson().toJson(confirmList));
        model.addAttribute("suspectList",new Gson().toJson(suspectList));

        return "graph";
    }
}
