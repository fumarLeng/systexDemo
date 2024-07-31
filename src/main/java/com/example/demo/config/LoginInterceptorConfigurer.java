//package com.example.demo.config;
//
//import com.example.demo.interceptor.LoginInterceptor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Configuration
//public class LoginInterceptorConfigurer implements WebMvcConfigurer {
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        HandlerInterceptor interceptor = new LoginInterceptor();
//
//        List<String> patterns = new ArrayList<>();
//        patterns.add("/login");
//        patterns.add("/");
//        patterns.add("/register");
//        patterns.add("/css/**");
//
//        registry.addInterceptor(interceptor) //註冊
//                .addPathPatterns("/**") //黑名單
//                .excludePathPatterns(patterns);
//    }
//}
