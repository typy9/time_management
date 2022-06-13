package com.project.time_management.servlets.utils;

import com.project.time_management.dao.UserCredentialsDAO;
import com.project.time_management.dao.UsersActivitiesDAO;
import com.project.time_management.entity.User;
import com.project.time_management.entity.UsersActivityFull;
import com.project.time_management.services.LoginService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@WebServlet("/login")
public class LoginServlet extends HttpServlet{

    private static final Logger LOG = Logger.getLogger(LoginServlet.class);
    private final LoginService loginService = new LoginService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOG.debug("Start executing doPost");

        String login = request.getParameter("login");
        String password = request.getParameter("password");

        @SuppressWarnings("unchecked")
        final AtomicReference<UserCredentialsDAO> dao = (AtomicReference<UserCredentialsDAO>)
                request.getServletContext().getAttribute("dao");

        @SuppressWarnings("unchecked")
        final AtomicReference<UsersActivitiesDAO> usersActivitiesDao =
                (AtomicReference<UsersActivitiesDAO>)
                        request.getServletContext().getAttribute("usersActivitiesDao");

        User user;
        user = loginService.processUser(login, password, dao);

        if (user == null) {
            LOG.trace("user is null");
            request.setAttribute("errorMessage", "Cannot find user with such login/password");
            response.sendRedirect(request.getContextPath() + "/display_registration");
//            request.getRequestDispatcher("/registration.jsp").forward(request, response);
        } else {
            String role =  user.getRole();
            if (role.equals("admin")) {
                LOG.trace("user role is admin");
                request.getSession().setAttribute("user", user);
                request.getSession().setAttribute("role", role);
                response.sendRedirect(request.getContextPath() + "/admin_cabinet");
//                request.getRequestDispatcher("/admin_menu.jsp").forward(request, response);
            } else {
                LOG.trace("user role is user");
                List<UsersActivityFull> userActivities = loginService.processUserActivities(user, usersActivitiesDao);
                request.getSession().setAttribute("user", user);
                request.getSession().setAttribute("role", role);
                request.getSession().setAttribute("userActivities", userActivities);
                response.sendRedirect(request.getContextPath() + "/user_cabinet");
//                request.getRequestDispatcher("/user_menu.jsp").forward(request, response);
                }
            }
        LOG.debug("Finish executing doPost");
    }
}


