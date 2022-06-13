package com.project.time_management.servlets.user;

import com.project.time_management.entity.User;
import com.project.time_management.helpers.PaginationHelper;
import com.project.time_management.services.user.DisplayUserService;
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

@WebServlet("/users_list")
public class DisplayUserServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(DisplayUserServlet.class);
//    private List<User> users;
    @Resource(name="jdbc/project")
    DataSource dataSource;

    public DisplayUserServlet() {
        super();
    }

    @Override
    public void init() throws ServletException {

//        final Object users = getServletContext().getAttribute("users");
//        if ( !(users instanceof CopyOnWriteArrayList) ) {
//            throw new IllegalStateException("Users repo did not initialize!");
//        } else {
//            this.users = (CopyOnWriteArrayList<User>) users;
//        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOG.debug("Start executing doGet");

        PaginationHelper paginationHelper = new PaginationHelper();
        int recordsPerPage = PaginationHelper.RECORDS_PER_PAGE;
        int page= paginationHelper.getPageParameter(request);

        DisplayUserService processRequest = new DisplayUserService(dataSource);
        List<User> users = processRequest.getUsers(page, recordsPerPage);
        int noOfPages = processRequest.getNoOfPages();

        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("recordsPerPage", recordsPerPage);
        request.setAttribute("currentPage", page);
        request.setAttribute("users", users);

        HttpSession session = request.getSession(false);
        session.setAttribute("view", "/users_list");

        request.getRequestDispatcher("/users_list.jsp").forward(request, response);

        LOG.debug("Finish executing doGet");
    }

    @Override
    public void destroy() {

    }
}
