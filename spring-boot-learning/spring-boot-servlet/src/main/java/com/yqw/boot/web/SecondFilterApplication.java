package com.yqw.boot.web;


import com.yqw.boot.web.filter.SecondFilter;
import com.yqw.boot.web.servlet.SecondServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * SpringBoot整合Filter方式二
 */
@SpringBootApplication
public class SecondFilterApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecondFilterApplication.class, args);
    }

    /**
     * 注册Servlet
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean getServletRegistrationBean2() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new SecondServlet());
        bean.addUrlMappings("/second2");
        return bean;
    }

    /**
     * 注册Filter
     */
    @Bean
    public FilterRegistrationBean getFilterRegistrationBean() {
        FilterRegistrationBean bean = new FilterRegistrationBean(new SecondFilter());
        //bean.addUrlPatterns(new String[]{"*.do","*.jsp"});
        bean.addUrlPatterns("/second2");
        return bean;
    }
}
