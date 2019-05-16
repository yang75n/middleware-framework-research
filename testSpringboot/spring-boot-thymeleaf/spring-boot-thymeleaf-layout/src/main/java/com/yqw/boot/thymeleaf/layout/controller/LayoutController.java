package com.yqw.boot.thymeleaf.layout.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by iQiwen on 2019/5/16.
 */
@Controller
public class LayoutController {
    @RequestMapping("/index")
      public String index() {
        return "index";
    }

    @RequestMapping("/fragment")
    public String fragment() {
        return "fragment";
    }

    @RequestMapping("/layout")
    public String layout() {
        return "layout";
    }

    @RequestMapping("/home")
    public String home() {
        return "home";
    }
}
