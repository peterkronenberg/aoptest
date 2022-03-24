package com.torchai.service.filter;

import lombok.NonNull;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthFilter extends OncePerRequestFilter {

    @PostConstruct
    public void postConstruct() {
        System.out.println("*** Loading authFilter");
    }

    @Override
    protected void doFilterInternal(final @NonNull HttpServletRequest httpServletRequest, final @NonNull HttpServletResponse httpServletResponse, final @NonNull FilterChain filterChain) throws ServletException, IOException {
        System.out.println("*** calling filter");
        filterChain.doFilter(httpServletRequest, httpServletResponse);
        System.out.println("*** return from filter");
    }
}
