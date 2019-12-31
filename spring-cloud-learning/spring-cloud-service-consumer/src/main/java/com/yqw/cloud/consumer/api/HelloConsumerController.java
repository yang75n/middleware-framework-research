package com.yqw.cloud.consumer.api;

import com.yqw.cloud.consumer.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by iQiwen on 2019/5/15.
 */
@RestController
@RequestMapping("/hello")
public class HelloConsumerController {
    @Autowired
    private HelloService helloService;

    /**
     * Hello
     *
     * @return
     */
    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public String hello(@PathVariable String name) {
        System.out.println("HelloConsumerController ->hello ,我在consumer，我被 调用了. helloService="+helloService);
        return this.helloService.hello(name);
    }
}
