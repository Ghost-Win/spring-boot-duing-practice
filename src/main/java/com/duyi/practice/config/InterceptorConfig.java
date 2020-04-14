package com.duyi.practice.config;

import com.duyi.practice.component.MyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//自定义拦截器配置类
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    //注入我的拦截器
    @Autowired
    private MyInterceptor interceptor;

    //重写拦截器方法
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //-- addPathPatterns--/**拦截所有路径的访问
        //-- excludePathPatterns 需要放行的访问路径
        registry.addInterceptor(interceptor).addPathPatterns("/**")
        .excludePathPatterns("/login","/*.css","/*.js");

    }

}
