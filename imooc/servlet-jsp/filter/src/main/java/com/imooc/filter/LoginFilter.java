package com.imooc.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by tingkl on 2016/12/13.
 */
public class LoginFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginFilter.class);
    private static String filterName;
    private static String[] noLoginPaths;
    private static String charset;
    public void init(FilterConfig filterConfig) throws ServletException {
        filterName = filterConfig.getFilterName();
        noLoginPaths = filterConfig.getInitParameter("noLoginPaths").split(";");
        charset = filterConfig.getInitParameter("charset");
        LOGGER.debug("init " + filterName);
        LOGGER.debug(filterConfig.getInitParameter("tingkl"));
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LOGGER.debug(filterName + " Start......doFilter");
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession();

        req.setCharacterEncoding(charset);//修改编码

        LOGGER.debug("charset:" + charset);
        String uri = req.getRequestURI();
        boolean find = false;
        for (int index = 0; index < noLoginPaths.length; index++) {
            if (uri.indexOf(noLoginPaths[index]) > -1) {
                find = true;
                break;
            }
        }
        if (find) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        if (session.getAttribute("username") != null) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        else {
            res.sendRedirect("login.jsp");
        }
        LOGGER.debug(filterName + " End......doFilter");
    }

    public void destroy() {
        LOGGER.debug("destroy " + filterName);
    }
}
