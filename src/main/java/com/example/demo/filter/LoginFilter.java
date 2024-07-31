package com.example.demo.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@WebFilter
public class LoginFilter implements Filter {

    private final List<String> excludedUrls = Arrays.asList("/login", "/", "/register", "/static");

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
        log.warn("Request URL: {}", url);
        Object session = request.getSession().getAttribute("username");
        log.warn("拿到目前session裡面的username為 : {}", session);

        if (isExcluded(url)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (session == null) {
            response.sendRedirect("/login");
        } else {
            filterChain.doFilter(request, response);
        }

    }

    @Override
    public void destroy() {
        log.warn("LoginFilter destroy");
        Filter.super.destroy();
    }

    public Boolean isExcluded(String url) {
        return excludedUrls.contains(url);
    }
}
