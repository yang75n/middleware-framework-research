package com.yqw.boot.data.jdbc;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by iQiwen on 2019/5/17.
 */
@Controller
public class CustomerController {
    private final CustomerRepository customerRepository;

    /**
     * 只有一个构造函数的哦时候会自动 AutoWired
     */
    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/add")
    @ResponseBody
    public String add() {
        Customer customer = new Customer();
        customer.setId(123L);
        customer.setDateOfBirth(LocalDate.now());
        customer.setFirstName("yangqiwen");

        return customerRepository.save(customer) == null ? "add failed" : "add ok";
    }

    /**
     * 访问时候需要加入参数：?name=xxx
     *
     * @param name
     * @return
     */
    @GetMapping("/findByName")
    @ResponseBody
    @Transactional(readOnly = true)
    public List<Customer> customers(@RequestParam String name) {
        System.out.println("findByName.......");
        return this.customerRepository.findByName(name);
    }


    /**
     * restful 风格访问
     *
     * @param name
     * @return
     */
    @GetMapping("/list/{name}")
    @ResponseBody
    @Transactional(readOnly = true)
    public List<Customer> listCustomers(@PathVariable String name) {
        System.out.println("listCustomers.......");
        return this.customerRepository.findByName(name);
    }


    @GetMapping("/count")
    @ResponseBody
    @Transactional(readOnly = true)
    public long count() {
        System.out.println("count.......");
        return this.customerRepository.count();
    }

}
