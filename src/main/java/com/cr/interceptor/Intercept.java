package com.cr.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Intercept  extends HandlerInterceptorAdapter {
//        @Override
//        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
//                HttpSession session = request.getSession();
//                String user = (String) session.getAttribute("user");
//                if(user!=null){
//                    //登陆成功的用户
//                    return true;
//                }else{
//                    //没有登陆，转向登陆界面
//                    response.sendRedirect("login.html");
//                    return false;
////                }
//
//    }
}
