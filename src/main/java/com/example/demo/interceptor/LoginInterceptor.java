//package com.example.demo.interceptor;
//
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//
//public class LoginInterceptor implements HandlerInterceptor {
//
//    @Override
//    public boolean preHandle(HttpServletRequest request,
//                             HttpServletResponse response,
//                             Object handler) throws Exception {
//        Object obj = request.getSession().getAttribute("username");
//        if (obj == null) {
//            response.sendRedirect("/login");
//            return false;
//        }
//        return true;
//    }
//
//}
