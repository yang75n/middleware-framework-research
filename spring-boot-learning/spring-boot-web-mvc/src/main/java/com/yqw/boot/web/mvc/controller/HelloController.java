package com.yqw.boot.web.mvc.controller;

import com.yqw.boot.web.mvc.domian.HaveUser;
import com.yqw.boot.web.mvc.domian.ListUser;
import com.yqw.boot.web.mvc.domian.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Qiwen on 2019/5/18.
 */
@Controller
public class HelloController {

    @RequestMapping("/hello")
    public String hello() {
        System.out.println("HelloController -> hello ,");
        return "/";
    }

    @RequestMapping("hello2")
    public String hello2(String name, int age) {
        System.out.println("HelloController -> hello2 ,name=" + name + " ,age=" + age);
        return "/index.html";
    }

    /**
     * 只要写，就会尽力赋值。只要传的参数和user的字段名称一样就会赋值
     */
    @RequestMapping("/hello3")
    public String hello3(User user, String name, HttpServletRequest req, HttpSession session) {
        System.out.println("HelloController ->hello3 user=" + user + " ,name=" + name);
        System.out.println(req.getSession());
        System.out.println("req=" + req + " HttpSession=" + session);
        return "/index.html";
    }


    @RequestMapping("hello4")
    public String hello4(@RequestParam(value = "name", defaultValue = "qiwen", required = true) String username, @RequestParam(value = "age", defaultValue = "3") int myAge) {
        System.out.println("HelloController -> hello4 ,username=" + username + " ,myAge=" + myAge);
        return "index.html";
    }

    /**
     * 接收单选按钮传值
     *
     * @param name
     * @param age
     * @param havor
     * @return
     */
    @RequestMapping("hello5")
    public String hello5(String name, int age, @RequestParam(value = "havor") List<String> havor) {
        System.out.println("HelloController -> hello4 ,name=" + name + " ,age=" + age + " ,havor=" + havor);
        return "index.html";
    }

    @RequestMapping("hello6")
    public String hello6(HaveUser haveUser) {
        System.out.println("HelloController -> hello6,haveUser=" + haveUser);
        return "/index.html";
    }

    @RequestMapping("hello7")
    public String hello7(ListUser listUser) {
        System.out.println("HelloController -> hello6,listUser=" + listUser);
        return "/index.html";
    }

    /**
     * RESTful 风格
     *
     * @return
     */
    @RequestMapping("hello7/{name}/{age}")
    public String hello8(@PathVariable String name, @PathVariable int age) {
        System.out.println("HelloController -> hello8,name=" + name + " ,age=" + age);
        return "/index.html";
    }

    /**
     * 跳转方式
     * 默认是跳转"forward:/index.html"
     */
    @RequestMapping("hello9")
    public String hello9() {
        return "redirect:/index.html";
    }


    /**
     * produces 设置响应头contenttype值
     */
    @RequestMapping(value = "hello10")
    @ResponseBody
    public User hello10() {
        User user = new User();
        user.setName("杨其文");
        user.setAge(18);
        return user;
    }


}
