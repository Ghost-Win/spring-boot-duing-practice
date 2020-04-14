package com.duyi.practice.data;

import com.duyi.practice.bean.DataBean;
import com.duyi.practice.bean.GraphBean;
import com.duyi.practice.service.DataService;
import com.duyi.practice.util.HttpClientUtil;
import com.duyi.practice.util.HttpURLConnectionUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class DataHandler {

    //采集常规数据链接地址
    public static String url = "https://view.inews.qq.com/g2/getOnsInfo?name=disease_h5";
    //采集趋势图数据链接地址
    public static String graphUrl = "https://view.inews.qq.com/g2/getOnsInfo?name=disease_other";

    @Autowired
    private DataService dataService;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    //执行顺序：加载Servlet - servlet的构造函数 - PostConstruct - Init
    //  - Service - destroy - PreDestroy - servlet加载完毕

    //如果数据库中第一次没有数据时，如何初始化
    //希望项目在启动时就采集一次数据，在存入数据库中，以后就读取数据库中的数据
    //@PostContruct,修饰方法使用，在服务器加载servlet时运行，只会执行一次
    //初始化数据时常用该注解，修饰的方法会在依赖注入完成后自动调用

    @PostConstruct
    public void saveData(){
        System.out.println("初始化数据存储");
        List<DataBean> dataBeans = getData();
        //表格中只保留最新的数据，每次启动先清空，在保存最新数据
        dataService.remove(null);//清除表格数据
        dataService.saveBatch(dataBeans);//批量存入多条数据
    }

    //cron指的是计划任务：由6或7个时间元素组成，空格符或/分开
    //第1位   秒       0-59
    //第2位   分钟      0-59
    //第3位   小时      0-23
    //第4位   日期      1-31
    //第5位   月份      1-12
    //第6位   星期      1-7
    //第7位   年份      1970-2099

    //固定频率任务    fixedRate = 10000;//按照指定的频率执行（每10秒执行一次）
    //如果方法的执行时间，超过了频率时间，
    //比如方法执行完成要15秒，立即执行下一次任务
    //@Scheduled(fixedRate = 10000)

    //固定间隔任务    fixedDelay = 10000;//上一次任务完成后，间隔到下一次任务开始执行的时间
    //比如第0秒执行任务，执行15秒，则第25秒开始执行下一次任务
    //@Scheduled(fixedDelay = 10000)

    //定时更新数据
    //@Scheduled让方法定时执行，使用cron表达式,每10分钟就更新一次
    @Scheduled(cron = "0 0/10 * * * ? ")
    public void updateData(){
        System.out.println("当前时间："+dateFormat.format(new Date()));
        System.out.println("更新数据存储");
        List<DataBean> dataBeans = getData();
        //表格中只保留最新的数据，每次启动先清空，在保存最新数据
        dataService.remove(null);//清除表格数据
        dataService.saveBatch(dataBeans);//批量存入多条数据
    }



    //采集常规数据方法
    public static List<DataBean> getData() {
        //爬取的实时数据由{ret和data}两部分组成的字符串
        String str = HttpURLConnectionUtil.doGet(url);
        Gson gson = new Gson();
        Map map = gson.fromJson(str,Map.class);

        //而我们只需要取出其中的data部分，并且明确它是一个字符串
        //先用字符串来接收后，再用gson转化为map集合
        String subStr = (String) map.get("data");
        Map subMap = gson.fromJson(subStr,Map.class);
        ArrayList areaList = (ArrayList) subMap.get("areaTree");

        //取出国内的数据
            Map dataMap = (Map) areaList.get(0);
            //取出各省份数据
            ArrayList childrenList = (ArrayList) dataMap.get("children");
            //后续可以处理地市级的数据
            //System.out.println(childrenList);
            ArrayList<DataBean> result = new ArrayList<>();
            for (int i = 0; i < childrenList.size(); i++) {
                Map tmp = (Map) childrenList.get(i);
                //先取个省名字
                String name = (String) tmp.get("name");
                Map totalMap = (Map) tmp.get("total");

                double confirm = (Double) totalMap.get("confirm");
                double dead = (Double) totalMap.get("dead");
                double heal = (Double) totalMap.get("heal");
                Map todayMap = (Map)tmp.get("today");
                //每日新增人数
                double confirmAdd = (Double) todayMap.get("confirm");
                DataBean dataBean = new DataBean(null,name,(int)confirmAdd, (int) confirm, (int) dead, (int) heal);
                result.add(dataBean);
                //System.out.println(dataBean);
        }

            return result;

/*读取静态文档疫情json数据*/
//        FileReader fileReader = null;
//        ArrayList<DataBean> result = new ArrayList<>();
//        try {
//            File file = new File("C:\\Users\\huawei\\Desktop\\疫情json化数据.txt");
//            //System.out.println(ClassLoader.getSystemResource(""));
//            fileReader = new FileReader(file);
//            char[] c = new char[100000];//数据长度大概9万多字符，保证一次读取完
//            String line = null;
//            int count = fileReader.read(c);
//            while(count != -1){
//                line = new String(c, 0, count);
//                System.out.println(line);
//                count = fileReader.read(c);
//            }
//
//
//            Gson gson = new Gson();
//            Map map = gson.fromJson(line, Map.class);
//            //System.out.println(map);
//            //取出出现疫情的个各国家数据
//            ArrayList areaList = (ArrayList) map.get("areaTree");
//            //取出国内的数据
//            Map dataMap = (Map) areaList.get(0);
//            //取出各省份数据
//            ArrayList childrenList = (ArrayList) dataMap.get("children");
//            //后续可以处理地市级的数据
//            //System.out.println(childrenList);
//
//            for (int i = 0; i < childrenList.size(); i++) {
//                Map tmp = (Map) childrenList.get(i);
//                //先取个省名字
//                String name = (String) tmp.get("name");
//
//                Map totalMap = (Map) tmp.get("total");
//
//                double confirm = (Double) totalMap.get("confirm");
//                double dead = (Double) totalMap.get("dead");
//                double heal = (Double) totalMap.get("heal");
//                DataBean dataBean = new DataBean(name, (int) confirm, (int) dead, (int) heal);
//                result.add(dataBean);
//        }
//            //System.out.println(result);
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            if(fileReader != null){
//                    try {
//                        fileReader.close();
//                    } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return result;
    }

    //趋势图数据处理方法
    public static List<GraphBean> getGraphData(){
        String str = HttpClientUtil.doGet(graphUrl);
        Gson gson = new Gson();
        Map map = gson.fromJson(str,Map.class);

        //而我们只需要取出其中的data部分，并且明确它是一个字符串
        //先用字符串来接收后，再用gson转化为map集合
        String subStr = (String) map.get("data");
        Map subMap = gson.fromJson(subStr,Map.class);
        //在取出趋势图所需的chinaDayList数组中的数据，数组的每一个元素又是每天对应的map数据结构
        ArrayList list = (ArrayList) subMap.get("chinaDayList");
        List<GraphBean> graphBeans = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
             Map tmp = (Map)list.get(i);
            String date = (String) tmp.get("date");
            double confirm = (Double)tmp.get("confirm");
            double suspect = (Double)tmp.get("suspect");
            GraphBean graphBean = new GraphBean(date,(int)confirm,(int)suspect);
            graphBeans.add(graphBean);
        }
        return graphBeans;
    }

    public static void main(String[] args) {

        //getData();
        getGraphData();

    }
}
