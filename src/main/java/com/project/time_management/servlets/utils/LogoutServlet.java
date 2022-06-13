package com.project.time_management.servlets.utils;

import com.project.time_management.servlets.activity.ActivityTimeUpdateServlet;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Logout.
 * Delete session.
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(LogoutServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        LOG.debug("Start executing doGet");
        final HttpSession session = req.getSession();

        session.removeAttribute("password");
        session.removeAttribute("login");
        session.removeAttribute("role");
        session.removeAttribute("user");

        resp.sendRedirect(super.getServletContext().getContextPath());
        LOG.debug("Finish executing doGet");
    }
}
