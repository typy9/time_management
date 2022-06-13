package com.project.time_management.servlets.user;

import com.project.time_management.entity.User;
import com.project.time_management.helpers.PaginationHelper;
import com.project.time_management.services.user.DeleteUserService;
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

@WebServlet("/user_delete")
public class DeleteUserServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(DeleteUserServlet.class);
//    private List<User> users;
    @Resource(name="jdbc/project")
    DataSource dataSource;

    @Override
    public void init() throws ServletException {

//        final Object users = getServletContext().getAttribute("users");
//
//        if (!(users instanceof CopyOnWriteArrayList)) {
//            throw new IllegalStateException("users repo does not initialize!");
//        } else {
//            this.users = (CopyOnWriteArrayList<User>) users;
//        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOG.debug("Start executing doPost");
        final String id = request.getParameter("id");

        PaginationHelper paginationHelper = new PaginationHelper();
        int recordsPerPage = PaginationHelper.RECORDS_PER_PAGE;
        int page= paginationHelper.getPageParameter(request);

        DeleteUserService processRequest = new DeleteUserService(dataSource);
        List<User> users = processRequest.deleteUser(id, page, recordsPerPage);
        int noOfPages = processRequest.getNoOfPages();

        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("recordsPerPage", recordsPerPage);
        request.setAttribute("currentPage", page);
        request.setAttribute("users", users);
        response.sendRedirect(request.getContextPath() + "/users_list");
        LOG.debug("Finish executing doPost");
    }

}
