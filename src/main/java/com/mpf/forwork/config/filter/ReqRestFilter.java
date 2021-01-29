package com.mpf.forwork.config.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author: MiaoPengfei
 * @date: 2021/1/18 15:10
 * @description:
 */
@Slf4j
@WebFilter(urlPatterns = "/*", filterName = "reqResFilter")
public class ReqRestFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        log.info("=============自定义过滤器============");
        log.info("names:{}, k-v:{}", request.getParameterNames(), request.getParameterMap());
        BufferedReader br = request.getReader();
        String str;
        StringBuilder wholeStr = new StringBuilder();
        while((str = br.readLine()) != null) {
            wholeStr.append(str);
        }
        log.info("wholeStr:{}", wholeStr.toString());
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
