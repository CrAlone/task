package com.cr.config;


import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("执行了");
        if (request.getSession().getAttribute("user") != null){
            return true;
        }
        request.getRequestDispatcher("task.html").forward(request,response);
        return false;
    }
}
