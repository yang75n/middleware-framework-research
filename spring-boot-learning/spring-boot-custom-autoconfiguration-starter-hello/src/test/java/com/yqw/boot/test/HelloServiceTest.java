package com.yqw.boot.test;

import com.yqw.boot.custom.starter.hello.Application;
import com.yqw.boot.custom.starter.hello.HelloService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class HelloServiceTest {

    @Autowired
    private HelloService helloService;

    @Test
    public void testHello() {
        System.out.println(helloService.hello());
    }

}
