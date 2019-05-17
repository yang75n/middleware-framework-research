package com.yqw.boot.helloWorld.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by iQiwen on 2019/5/15.
 */
@Controller
public class HelloWorldController {

    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        return "Hello World, Hello Spring Boot 2.0!";
    }

    @RequestMapping("/")
    @ResponseBody
    public String hello2() {
        return "Hello World 2, Hello Spring Boot 2.0!";
    }

}
