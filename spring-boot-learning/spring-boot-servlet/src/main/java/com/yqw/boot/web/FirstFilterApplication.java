package com.yqw.boot.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * Spriing整合Filter方式一：通过注解方式
 */
@SpringBootApplication
@ServletComponentScan
public class FirstFilterApplication {

    public static void main(String[] args) {
        SpringApplication.run(FirstFilterApplication.class, args);
    }
}
