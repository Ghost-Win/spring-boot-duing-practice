package com.duyi.practice.component;

import com.sun.corba.se.spi.orbutil.closure.Closure;
import com.sun.corba.se.spi.resolver.LocalResolver;
import org.omg.CORBA.Object;
import org.springframework.web.servlet.LocaleResolver;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Set;
//自定义国际化处理器
public class MyLocalResolver implements LocaleResolver{

    @Override
    public Locale resolveLocale(HttpServletRequest httpServletRequest) {
        //进行具体处理
        System.out.println("自定义解析器");
        String lan = httpServletRequest.getParameter("l");
        Locale locale = Locale.getDefault();

        if(!StringUtils.isEmpty(lan)){
            String[] split = lan.split("_");
            locale = new Locale(split[0],split[1]);
        }

        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

    }
}
