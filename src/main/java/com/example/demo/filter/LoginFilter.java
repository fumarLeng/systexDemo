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

    private final Set<String> excludedUrls = new HashSet<>(Set.of("/", "/register", "/register/add", "/static", "/logout", "/loginAjax.html"));

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
        String url = request.getRequestURI();

        if (isExcluded(url)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (url.equals("/login")) {
            handleThymeleafLogin(request, response);
        } else if (url.equals("/ajax/login")) {
            handleAjaxLogin(request, response);
        } else {
            if (isUserLoggedIn(request)) {
                filterChain.doFilter(request, response);
            } else {
                response.sendRedirect("/");
            }
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

    private boolean isUserLoggedIn(HttpServletRequest request) {
        return request.getSession().getAttribute("user") != null;
    }

    private void handleThymeleafLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();

        Users user = userService.checkUser(username, password);
        if (user != null) {
            session.setAttribute("username", username);
            session.setAttribute("user", user);
            request.getRequestDispatcher("/loginSuccess").forward(request, response);
        } else {
            session.setAttribute("error", "帳號或密碼錯誤");
            response.sendRedirect("/");
        }
    }

    private void handleAjaxLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();

        Users user = userService.checkUser(username, password);
        System.out.println("handleAjaxLogin :　" + user);
        if (user != null) {
            session.setAttribute("username", username);
            session.setAttribute("user", user);
            System.out.println("handleAjaxLogin 成功!!");
            response.setStatus(200);
            response.getWriter().write("{\"success\": true}");
        } else {
            response.getWriter().write("{\"success\": false, \"error\": Wrong account password\"\"}");
        }
    }
}

