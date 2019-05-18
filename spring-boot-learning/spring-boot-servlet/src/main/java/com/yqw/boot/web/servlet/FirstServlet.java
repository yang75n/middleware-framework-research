package com.yqw.boot.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * SpringBoot整合Servlet方式一
 * <p/>
 * <servlet>
 * <servlet-name>FirstServlet</servlet-name>
 * <servlet-class>com.bjsxt.servlet.FirstServlet</servlet-class>
 * </servlet>
 * <p/>
 * <servlet-mapping>
 * <servlet-name>FirstServlet</servlet-name>
 * <url-pattern>/first</url-pattern>
 * </servlet-mapping>
 */
@WebServlet(name = "firstServlet", urlPatterns = "/first")
public class FirstServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("FirstServlet............");

    }
}
