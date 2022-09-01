package com.example.demo.handler;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author iCoderLad
 * @date 2022/09/01 16:50
 */
@Component
@WebFilter(filterName = "CommonFilter", urlPatterns = "/query/*")
@Order(1)
public class CommonFilter implements Filter {
    @Autowired
    UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("filter init");
        // 获取ServletContext
        ServletContext context = filterConfig.getServletContext();
        //获取WebApplicationContext上下文
        WebApplicationContext cxt = WebApplicationContextUtils.getWebApplicationContext(context);
        UserService bean = cxt.getBean(UserService.class);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("filter doFilter");
        chain.doFilter(request, response);

    }
}
