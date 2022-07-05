package com.project.time_management.servlets.utils;

import com.project.time_management.services.RegistrationService;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;


@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(RegistrationServlet.class);
    @Resource(name="jdbc/project")
    DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        LOG.debug("Start executing doPost");
        RegistrationService registrationService = new RegistrationService(dataSource);

        final String name = req.getParameter("user_name");
        final String login = req.getParameter("login");
        final String password = req.getParameter("password");

        boolean userRegistrationPermitted = registrationService.checkCredentials(name, login, password);

        if (userRegistrationPermitted) {
            req.getSession().setAttribute("role", "user");
        }

        req.getRequestDispatcher("/index.jsp").forward(req, resp);
        LOG.debug("Finish executing doPost");
    }
}
