package com.imooc.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by tingkl on 2016/12/13.
 */
public class ForwardFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(FirstFilter.class);
    private static String filterName;
    public void init(FilterConfig filterConfig) throws ServletException {
        filterName = filterConfig.getFilterName();
        LOGGER.debug("init " + filterName);
        LOGGER.debug(filterConfig.getInitParameter("tingkl"));
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LOGGER.debug(filterName + " Start......doFilter");
        filterChain.doFilter(servletRequest, servletResponse);
        LOGGER.debug("run only when forward");
        LOGGER.debug(filterName + " End......doFilter");
    }

    public void destroy() {
        LOGGER.debug("destroy " + filterName);
    }
}
