package com.yqw.boot.ehcache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 没有controller层，通过测试类进行service层单元测试
 * Ehcache是一个纯Java的进程内缓存框架，具有快速、精干等特点。
 */
@SpringBootApplication
@EnableCaching
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
