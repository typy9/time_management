package com.project.time_management.servlets.user;

import com.project.time_management.entity.User;
import com.project.time_management.services.user.UpdateUserService;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;


@WebServlet("/user_update")
public class UpdateUserServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(UpdateUserServlet.class);

//    private List<User> users;
    @Resource(name="jdbc/project")
    DataSource dataSource;

    @Override
    public void init() throws ServletException {

//        final Object users = getServletContext().getAttribute("users");
//
//        if (!(users instanceof CopyOnWriteArrayList)) {
//            throw new IllegalStateException("users repo did not initialize in UpdateUserServlet!");
//        } else {
//            this.users = (CopyOnWriteArrayList<User>) users;
//        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOG.debug("Start executing doGet");

        final String id = request.getParameter("id");

        if (idIsInvalid(id)) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        UpdateUserService processRequest = new UpdateUserService(dataSource);
        User user = processRequest.getUserForUpdate(id);

        request.setAttribute("user", user);
        request.getRequestDispatcher("/update_user.jsp").forward(request, response);

        LOG.debug("Finish executing doGet");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOG.debug("Start executing doPost");
        final String id = request.getParameter("id");
        final String name = request.getParameter("name");
        final String role = request.getParameter("role");

        UpdateUserService processRequest = new UpdateUserService(dataSource);
        List<User> users = processRequest.updateUser(id, name, role);

        request.setAttribute("users", users);
        HttpSession session = request.getSession(false);
        session.setAttribute("view", "/user_update");
        response.sendRedirect(request.getContextPath() + "/users_list");
        LOG.debug("Finish executing doPost");
    }

    private boolean idIsInvalid(String id) {
        return !(id != null && id.matches("[+]?\\d+"));
    }
}
