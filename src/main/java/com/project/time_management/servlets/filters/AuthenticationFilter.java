package com.project.time_management.servlets.filters;

import com.project.time_management.helpers.AuthenticationHelper;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

public class AuthenticationFilter implements Filter {

    private static final Logger LOG = Logger.getLogger(AuthenticationFilter.class);
    private static Map<String, List<String>> accessMap = new HashMap<>();
    private static List<String> commons = new ArrayList<>();
    private static List<String> outOfControl = new ArrayList<>();
    private final AuthenticationHelper authenticationHelper = new AuthenticationHelper();

    @Override
    public void init(FilterConfig conf) throws ServletException {
        LOG.debug("Filter initialization starts");
        // roles
        accessMap.put("admin", asList(conf.getInitParameter("admin")));
        accessMap.put("user", asList(conf.getInitParameter("user")));
        LOG.trace("Access map --> " + accessMap);
        // commons
        commons = asList(conf.getInitParameter("common"));
        LOG.trace("Common commands --> " + commons);
        // out of control
        outOfControl = asList(conf.getInitParameter("out-of-control"));
        LOG.trace("Out of control commands --> " + outOfControl);
        LOG.debug("Filter initialization finished");
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        LOG.trace("Start AuthenticationFilter");

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String command = request.getParameter("command");
        HttpSession session = httpRequest.getSession(false);
        String userRole = (String) session.getAttribute("role");

        LOG.trace("AuthenticationFilter session attribute: 'userRole' = " + userRole);

        boolean accessAllowed = authenticationHelper.accessAllowed(
                command, userRole, outOfControl, commons, accessMap);

        if ( session != null && accessAllowed) {
            LOG.debug("access allowed");
            chain.doFilter(request, response);
        } else {
            LOG.debug("access denied");
            String errorMessage = "Permission denied to access the requested resource.";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("/error_page.jsp").forward(request, response);
        }
        LOG.trace("Finish AuthenticationFilter");
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

//    private boolean accessAllowed(ServletRequest request) {
//
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//
//        String command = request.getParameter("command");
////        System.out.println("command : " + command);
//
//        HttpSession session = httpRequest.getSession(false);
//        String userRole = (String) session.getAttribute("role");
////        System.out.println("userRole : " + userRole);
//
//        if (outOfControl.contains(command)) {
//            return true;
//        }
//
//        if (userRole == null) {
//            System.out.println("user role is null");
//            return commons.contains(command);
//        }
//
//        if (command == null || command.isEmpty()) {
//            return true;
//        }
//
//        if (session == null) {
//            System.out.println("session is null");
//            return false;
//        }
//
////        System.out.println("final return : " + (accessMap.get(userRole).contains(command) || commons.contains(command)));
//        return accessMap.get(userRole).contains(command) || commons.contains(command);
//    }

    private List<String> asList(String str) {
        List<String> list = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(str);
        while (st.hasMoreTokens()) {
            list.add(st.nextToken());
        }
        return list;
    }
}
