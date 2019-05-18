package com.yqw.boot.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * Spriing整合Servlet方式一：通过注解方式
 */
@SpringBootApplication
@ServletComponentScan //在springBoot启动时会扫描@WebServlet，并将该类实例化
public class FirstServletApplication {

    public static void main(String[] args) {
        SpringApplication.run(FirstServletApplication.class, args);
    }
}
