package com.imooc.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by tingkl on 2016/12/13.
 */
public class FirstFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(FirstFilter.class);
    private static String filterName;
    public void init(FilterConfig filterConfig) throws ServletException {
        filterName = filterConfig.getFilterName();
        LOGGER.debug("init " + filterName);
        LOGGER.debug(filterConfig.getInitParameter("tingkl"));
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LOGGER.debug(filterName + " Start......doFilter");
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        LOGGER.debug("contextPath: " + req.getContextPath());
        filterChain.doFilter(servletRequest, servletResponse);
        // 重定向
        // res.sendRedirect(req.getContextPath() + "/main.jsp"); // 重定向,又会经过这个request过滤器,死循环了
        // 转发
        // req.getRequestDispatcher("main.jsp").forward(req, res);  // 浏览器无感知,地址无变化,脱离request过滤器,但是要执行forward过滤器
        LOGGER.debug(filterName + " End......doFilter");
    }

    public void destroy() {
        LOGGER.debug("destroy " + filterName);
    }
}
