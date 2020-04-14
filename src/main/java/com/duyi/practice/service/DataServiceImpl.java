package com.duyi.practice.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duyi.practice.bean.DataBean;
import com.duyi.practice.data.DataHandler;
import com.duyi.practice.data.JsoupHandler;
import com.duyi.practice.mapper.DataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataServiceImpl extends ServiceImpl<DataMapper,DataBean> implements DataService{


//    @Override
//    public List<DataBean> list() {
//        return DataHandler.getData();
//    }
//
//    @Override
//    public List<DataBean> listById(int id) {
//        if(id == 1){
//            return DataHandler.getData();
//        }else {
//            return JsoupHandler.getData();
//        }
//
//    }
}
