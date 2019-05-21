package com.yqw.spring.test.test;

import com.yqw.spring.test.config.Config;
import com.yqw.spring.test.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by iQiwen on 2019/5/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class UserServcieTest {
    @Autowired
    private UserService userService;

    @Test
    public void testGet() {
        System.out.println("testGet......userSerevice=" + userService);
        System.out.println(userService.get());
    }
}
