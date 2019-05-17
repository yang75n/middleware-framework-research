package com.yqw.boot.mybatisXml;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by iQiwen on 2019/5/16.
 */
@SpringBootApplication
@MapperScan("com.yqw.boot.mybatisXml.mapper")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
