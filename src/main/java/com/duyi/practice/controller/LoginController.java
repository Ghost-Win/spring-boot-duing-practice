package com.duyi.practice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    //访问登录页
    @RequestMapping("/login")
    public String toLogin(){
        return "login";
    }

    //@RequestParam接收页面传递的参数
    //登录验证
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session){
        if(!StringUtils.isEmpty(username) && "123456".equals(password)){
            session.setAttribute("loginUser",username);
            //登录成功
            return "redirect:/list";
        }else{
            //登录失败重新登录
            return "login";
        }

    }


}
