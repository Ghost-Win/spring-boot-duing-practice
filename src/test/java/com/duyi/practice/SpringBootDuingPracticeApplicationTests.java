package com.duyi.practice;

import com.duyi.practice.component.MailComponent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootDuingPracticeApplicationTests {

    @Autowired
    private MailComponent mailComponent;
    @Test
    void contextLoads() {
        //mailComponent.send();
        mailComponent.send1();
    }



}
