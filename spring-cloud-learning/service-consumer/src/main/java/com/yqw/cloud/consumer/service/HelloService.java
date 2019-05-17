package com.yqw.cloud.consumer.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * created by iQiwen on 2019/5/15.
 */
@FeignClient(value = "HP-SERVICE", fallback = HelloServiceFallback.class)
public interface HelloService {
    @RequestMapping(value = "/hello/{name}", method = RequestMethod.GET)
    String hello(@PathVariable("name") String name);
}
