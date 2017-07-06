package com.imooc.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by tingkl on 2016/12/13.
 */
@WebFilter(filterName = "AsynFilter", asyncSupported = true, value={"/servlet/AsynServlet"}, dispatcherTypes = {DispatcherType.REQUEST,DispatcherType.ASYNC})
public class AsynFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AsynFilter.class);
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
        filterChain.doFilter(servletRequest, servletResponse);
        LOGGER.debug(filterName + " End......doFilter");
    }

    public void destroy() {
        LOGGER.debug("destroy " + filterName);
    }
}
