package com.yqw.boot.web.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * SpringBoot整合Filter 方式二
 */
public class SecondFilter implements Filter {
    @Override
    public void destroy() {
        // TODO Auto-generated method stub
    }

    @Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
            throws IOException, ServletException {
        System.out.println("进入SecondFilter");
        arg2.doFilter(arg0, arg1);
        System.out.println("离开SecondFilter");
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub
    }
}
