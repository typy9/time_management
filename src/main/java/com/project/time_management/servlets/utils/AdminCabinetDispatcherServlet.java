package com.project.time_management.servlets.utils;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/admin_cabinet")
public class AdminCabinetDispatcherServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(AdminCabinetDispatcherServlet.class);

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {
        LOG.debug("Start executing doGet");
        HttpSession session = request.getSession(false);
        session.setAttribute("view", "/admin_cabinet");
        request.getRequestDispatcher("/admin_menu.jsp").forward(request, response);
        LOG.debug("Finish executing doGet");
    }
}
