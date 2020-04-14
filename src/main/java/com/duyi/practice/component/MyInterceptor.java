package com.duyi.practice.component;


import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//自定义拦截器
@Component
public class MyInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws ServletException, IOException {

        //接收session
        Object user = request.getSession().getAttribute("loginUser");
        if(user != null){//判断当前是否登录
            return true;
        }

        //如果为空，则未登录，需要跳转登录页
        request.getRequestDispatcher("/login").forward(request,response);
        //response.sendRedirect("/login");
        return false;
    }


}
