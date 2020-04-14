package com.duyi.practice.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


//第一，需要有无参构造
//第二，需要实现序列化
//第三，需要声明实体类和数据库对应的表格
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("illness")
public class DataBean implements Serializable {
    //private static final long serialVersionUID = ;
//    @TableId(type = IdType.AUTO)
    private Long id;
    private String area;
    private int confirmAdd;
    private int confirm;
    private int dead;
    private int heal;

}
