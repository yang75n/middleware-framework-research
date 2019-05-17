package com.yqw.boot.xmlConfig.service;

import org.springframework.beans.factory.annotation.Value;

/**
 * Created by iQiwen on 2019/5/17.
 */

public class HelloService {
    @Value("${name:World}")
    private String name;

    public String getHelloMessage() {
        return "Hello , My name is " + name;
    }
}
