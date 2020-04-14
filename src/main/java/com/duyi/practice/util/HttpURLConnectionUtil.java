package com.duyi.practice.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpURLConnectionUtil {

    public static void main(String[] args) {
        String str = doGet("https://view.inews.qq.com/g2/getOnsInfo?name=disease_h5");
        System.out.println(str);
    }

    public static String doGet(String urlStr){
        HttpURLConnection conn = null;
        //使用IO来接收数据
        InputStream is = null;
        BufferedReader br = null;
        StringBuilder result = new StringBuilder();

        try {
            //创建远程url的链接对象
            URL url = new URL(urlStr);
            //通过链接对象打开链接
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            //设置连接超时时间：从发送请求开始到连接上主机地址的时间
            conn.setConnectTimeout(15000);
            //设置读取超时时间：从连接成功后到进行内容数据获取的时间
            conn.setReadTimeout(60000);
            //设置接收请求参数的格式为json
            conn.setRequestProperty("Accept","application/json");
            //发送请求
            conn.connect();

            //识别返回码
            if(200 == conn.getResponseCode()){
                is = conn.getInputStream();
                br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
                String line;
                while((line = br.readLine()) != null){
                    result.append(line);
                }
            }else{
                System.out.println("返回了错误码："+conn.getResponseCode());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(br != null){
                    br.close();
                }
                if(is != null){
                    is.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //关闭连接
            conn.disconnect();
        }
        return result.toString();
    }

}
