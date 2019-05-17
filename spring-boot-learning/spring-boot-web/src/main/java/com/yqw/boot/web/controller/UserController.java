package com.yqw.boot.web.controller;

import com.yqw.boot.web.domain.User;
import com.yqw.boot.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by iQiwen on 2019/5/16.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/addUser")
    public User addUser() {
        User user = new User("xiaoMing", "pachira123", "a@b.com", "mingming", "2019-01-01");
        userRepository.save(user);
        System.out.println("user=" + user);
        return user;
    }


    @RequestMapping("/getUser")
    public User getUser() {
        User user = userRepository.findByUserName("xiaoMing");
        System.out.println("user=" + user);
        return user;
    }


    @RequestMapping("/getAllUsers")
    public List<User> getAllUsers() {
        List<User> users=userRepository.findAll();
        return users;
    }
}
