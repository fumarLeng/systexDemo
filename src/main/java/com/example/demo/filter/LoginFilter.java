package com.example.demo.filter;

import com.example.demo.model.Users;
import com.example.demo.service.UserService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Slf4j
@WebFilter
@Component
public class LoginFilter implements Filter {

    private final Set<String> excludedUrls = new HashSet<>(Set.of("/", "/register", "/register/add", "/static", "/logout" ));

    @Autowired
    private UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.warn("LoginFilter init");
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        String url = request.getRequestURI();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
//        log.info("Request URL: {}", url);
        Object sessionUsername = request.getSession().getAttribute("username");

//        log.info("sessionUsername : {}", sessionUsername);
        if (isExcluded(url)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (sessionUsername == null) {
            if (url.equals("/login") || url.equals("/ajax/login")) {

                Users user = userService.checkUser(username, password);
                if (user != null) {
                    session.setAttribute("username", username);
                    session.setAttribute("user", user);
                    response.sendRedirect("/loginSuccess");
                } else {
                    session.setAttribute("error", "帳號或密碼錯誤");
                    response.sendRedirect("/");
                }
            } else {
                response.sendRedirect("/");
            }

        } else {
            filterChain.doFilter(request, response);
        }

    }

    @Override
    public void destroy() {
        log.info("LoginFilter destroy");
        Filter.super.destroy();
    }

    public Boolean isExcluded(String url) {
        return excludedUrls.contains(url);
    }
}
