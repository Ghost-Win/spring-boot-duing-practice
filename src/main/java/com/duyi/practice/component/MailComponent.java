package com.duyi.practice.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Component
public class MailComponent {

    @Autowired
    private JavaMailSender mailSender;

    public void send(){
        System.out.println("执行发送邮件");
        //简易的邮件消息
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setSubject("主题：测试邮件");
        mailMessage.setText("内容：测试内容");
        mailMessage.setTo("317020936@qq.com");
        mailMessage.setFrom("147893503@qq.com");
        mailSender.send(mailMessage);
    }

    @Autowired
    private TemplateEngine templateEngine;

    public void send1(){
        System.out.println("执行send1发送邮件");
        try {
            MimeMessage mailMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true);
            helper.setSubject("测试邮件2：加入模板");
            //使用thymeleaf渲染的容器
            Context context = new Context();
            Map<String,Object> valueMap = new HashMap<>();
            valueMap.put("title","邮件正文标题");
            valueMap.put("content","邮件正文内容");

            context.setVariables(valueMap);
            //拿到渲染结果,将数据与页面结合
            String content = templateEngine.process("mail",context);
            helper.setText(content,true);
            helper.setTo("1213535299@qq.com");
            helper.setFrom("147893503@qq.com");
            //附件
            FileSystemResource fileSystemResource = new FileSystemResource("C:\\Users\\huawei\\Pictures\\Saved Pictures\\IMG_0616(20190801-204559).JPG");
            helper.addAttachment("附件名字",fileSystemResource);


            //执行发送
            mailSender.send(mailMessage);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
