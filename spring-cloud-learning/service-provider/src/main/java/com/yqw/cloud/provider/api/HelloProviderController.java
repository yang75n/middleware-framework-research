package com.yqw.cloud.provider.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello provider
 * Created by iQiwen on 2019/5/15.
 */
@RestController
@RequestMapping("/hello")
public class HelloProviderController {

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public String hello(@PathVariable String name) {
        System.out.println("HelloProviderController->hello name=" + name);
        return "Hello," + name + ", Nice to meet you";
    }
}
