package com.yqw.boot.data.redis.service;

import org.springframework.cache.annotation.Cacheable;

/**
 * Created by iQiwen on 2019/5/20.
 */
public class UserService {


    @Cacheable
    public String getUserName() {
        System.out.println("UserService - >getUserName");
        return "yangqiwen";
    }
}
