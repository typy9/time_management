package com.project.time_management.servlets.filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {

    private static final Logger LOG = Logger.getLogger(LoginFilter.class);


    @Override
    public void init(FilterConfig conf) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        LOG.trace("Start LoginFilter");

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);

        String loginURI = "/time_management_war_exploded/front_controller";
        boolean loggedIn = (session != null && session.getAttribute("user") != null);
        boolean loginRequest = request.getRequestURI().equals(loginURI);

        if (loggedIn || loginRequest) {
            chain.doFilter(request, response);
        } else {
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
        LOG.trace("Finish LoginFilter");
    }

}