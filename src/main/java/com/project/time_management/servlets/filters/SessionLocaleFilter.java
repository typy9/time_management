package com.project.time_management.servlets.filters;

import com.project.time_management.servlets.FrontControllerServlet;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


//@WebFilter(value={"/locale_filter"},
//        dispatcherTypes={DispatcherType.REQUEST, DispatcherType.FORWARD})

public class SessionLocaleFilter implements Filter {

    private static final Logger LOG = Logger.getLogger(SessionLocaleFilter.class);

    public void init(FilterConfig config) throws ServletException {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        LOG.trace("Start SessionLocaleFilter");

        HttpServletRequest req = (HttpServletRequest) request;

        if (req.getParameter("sessionLocale") != null) {
            req.getSession().setAttribute("lang", req.getParameter("sessionLocale"));
        }
        chain.doFilter(request, response);
        LOG.trace("Finish SessionLocaleFilter");
    }

    public void destroy() {}
}
