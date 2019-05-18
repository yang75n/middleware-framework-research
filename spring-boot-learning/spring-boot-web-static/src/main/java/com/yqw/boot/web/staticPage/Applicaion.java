package com.yqw.boot.web.staticPage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBoot访问静态资源默认两个路径下查找
 * 1、从classpath:/static(必须是static)目录下查找(resources/)
 * 2、从ServletContext根目录下查找(webapp/)，对于maven来讲ServletContext目录就是webapp（必须是webapp）目录下
 */
@SpringBootApplication
public class Applicaion {

    public static void main(String[] args) {
        SpringApplication.run(Applicaion.class, args);
    }
}
