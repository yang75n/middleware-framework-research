package com.yqw.boot.web.jsp.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.Map;

/**
 * Created by iQiwen on 2019/5/17.
 */
@Controller
public class WelcomeController {

    @Value("${application.message:Hello World}")
    private String message = "Hello World";

    @GetMapping("/")
    public String welcome(Map<String, Object> model) {
        System.out.println("access welcome model=" + model);
        model.put("time", new Date());
        model.put("message", this.message);
        System.out.println("model=" + model);
        return "welcome";
    }

    @RequestMapping("/err")
    public String err() {
        System.out.println("access err");
        throw new RuntimeException("MyException");
    }


}
