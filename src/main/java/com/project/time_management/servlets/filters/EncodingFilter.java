package com.project.time_management.servlets.filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {

    private static final Logger LOG = Logger.getLogger(EncodingFilter.class);

    public EncodingFilter() {
    }

    @Override
    public void init(FilterConfig fConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        LOG.trace("Start EncodingFilter");
        request.setCharacterEncoding("UTF-8");
        chain.doFilter(request, response);
        LOG.trace("Finish EncodingFilter");
    }

    @Override
    public void destroy() {

    }
}
