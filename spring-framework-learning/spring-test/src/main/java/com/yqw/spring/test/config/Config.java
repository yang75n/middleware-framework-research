package com.yqw.spring.test.config;

import com.yqw.spring.test.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by iQiwen on 2019/5/20.
 */
@Configuration
public class Config {

    @Bean
    public UserService getUserService() {
        System.out.println("Config->getUserService");
        return new UserService();
    }
}
