package com.duyi.practice.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GraphBean {

    private String date;//日期
    private int confirm;//确诊人数
    private int suspect;//疑似人数


}
