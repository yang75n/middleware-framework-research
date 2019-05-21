package com.yqw.boot.custom.starter.hello;

/**
 * Created by iQiwen on 2019/5/21.
 */
//@Component   这里很重要，如果我们添加了这个注解那么，按照我们下面的设置SpringBoot会优先使用我们配置的这个Bean，这是符合SpringBoot框架优先使用自定义Bean的原则的。
public class HelloService {
    private String name;

    public String hello() {

        return "Hello,My name is " + name + " ,Welcome to Spring Boot!";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
