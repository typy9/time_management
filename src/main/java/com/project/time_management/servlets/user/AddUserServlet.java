package com.project.time_management.servlets.user;


import com.project.time_management.entity.User;
import com.project.time_management.helpers.PaginationHelper;
import com.project.time_management.services.user.AddUserService;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@WebServlet("/add_user")
public class AddUserServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(AddUserServlet.class);
//    private List<User> users;
    @Resource(name="jdbc/project")
    DataSource dataSource;

    @Override
    public void init() throws ServletException {
//        final Object users = getServletContext().getAttribute("users");
//
//        if (!(users instanceof CopyOnWriteArrayList)) {
//            throw new IllegalStateException("Repo users did not initialize!");
//        } else {
//            this.users = (CopyOnWriteArrayList<User>) users;
//        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        LOG.debug("Start executing doPost");
        final String name = req.getParameter("name");
        final String role = req.getParameter("role");
        final String login = req.getParameter("login");
        final String password = req.getParameter("password");


        PaginationHelper paginationHelper = new PaginationHelper();
        int recordsPerPage = PaginationHelper.RECORDS_PER_PAGE;
        int page= paginationHelper.getPageParameter(req);

        AddUserService processRequest = new AddUserService(dataSource);
        List<User> users = processRequest.addUser(name, role, login, password, page, recordsPerPage);
        int noOfPages = processRequest.getNoOfPages();

        req.setAttribute("noOfPages", noOfPages);
        req.setAttribute("recordsPerPage", recordsPerPage);
        req.setAttribute("currentPage", page);
        req.setAttribute("users", users);
        resp.sendRedirect(req.getContextPath() + "/users_list");
        LOG.debug("Finish executing doPost");
    }

}
