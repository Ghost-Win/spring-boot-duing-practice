package com.duyi.practice.data;

import com.duyi.practice.bean.DataBean;
import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsoupHandler {

    public static void main(String[] args) {
        String htmlStr = "<html><head><title>Hello Jsoup</title></head><body>parse doc</body></html>";
        Document doc = Jsoup.parse(htmlStr);
        //Jsoup有强大的选择器，来定位页面元素
        Elements elements = doc.select("body");
        //id选择器
        //Element element = doc.getElementById("");
        //标签选择器
        //Elements elementTag = doc.getElementsByTag("");
        getData();

    }


    public static ArrayList<DataBean> getData() {

        ArrayList<DataBean> result = new ArrayList<>();

        try {//直接传递url获取返回结果，为dom
            Document doc1 = Jsoup.connect("https://ncov.dxy.cn/ncovh5/view/pneumonia").get();
            Element element = doc1.getElementById("getAreaStat");
            //拿到定位标签的内容
            String data = element.data();

            //通过截取：中括号的方式，拿到json数据
            String subData = data.substring(data.indexOf("["),data.lastIndexOf("]")+1);
            System.out.println();
            Gson gson = new Gson();
            ArrayList list = gson.fromJson(subData,ArrayList.class);
            for (int i = 0; i < list.size(); i++) {
                Map map = (Map) list.get(i);
                String name = (String) map.get("provinceName");
                double confirm = (Double) map.get("confirmedCount");
                double heal = (Double) map.get("curedCount");
                double dead = (Double) map.get("deadCount");

                DataBean dataBean = new DataBean(null,name,0,(int)confirm,
                        (int)heal,(int)dead);
                result.add(dataBean);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

       return result;
    }
}
