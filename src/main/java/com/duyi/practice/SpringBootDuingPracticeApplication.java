package com.duyi.practice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

//程序入口类
@SpringBootApplication
//开启定时器的使用
@EnableScheduling
//扫描mapper接口所在包
@MapperScan("com.duyi.practice.mapper")
public class SpringBootDuingPracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDuingPracticeApplication.class, args);
    }

}
