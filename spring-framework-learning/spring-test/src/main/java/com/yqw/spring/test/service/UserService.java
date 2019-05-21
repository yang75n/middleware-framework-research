package com.yqw.spring.test.service;

import org.springframework.stereotype.Service;

/**
 * Created by iQiwen on 2019/5/20.
 */
@Service
public class UserService {

    public String get() {
        System.out.println("UserService-> add");
        return "YangQiwen";
    }

}
