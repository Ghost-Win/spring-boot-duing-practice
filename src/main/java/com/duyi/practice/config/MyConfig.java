package com.duyi.practice.config;

import com.duyi.practice.component.MyLocalResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;

@Configuration
public class MyConfig {

    @Bean
    public LocaleResolver localeResolver(){
        return new MyLocalResolver();
    }

}
