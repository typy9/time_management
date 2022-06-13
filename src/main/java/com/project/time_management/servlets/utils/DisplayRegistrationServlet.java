package com.project.time_management.servlets.utils;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/display_registration")
public class DisplayRegistrationServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(DisplayRegistrationServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        LOG.debug("Start executing doGet");

        HttpSession session = req.getSession(false);
        session.setAttribute("view", "/display_registration");

        req.getRequestDispatcher("/registration.jsp").forward(req, resp);
        LOG.debug("Finish executing doGet");
    }
}
