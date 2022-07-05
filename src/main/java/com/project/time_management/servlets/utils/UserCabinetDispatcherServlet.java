package com.project.time_management.servlets.utils;


import com.project.time_management.entity.User;
import com.project.time_management.entity.UsersActivityFull;
import com.project.time_management.helpers.PaginationHelper;
import com.project.time_management.services.report.UsersReportService;
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


@WebServlet("/user_cabinet")
public class UserCabinetDispatcherServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(UserCabinetDispatcherServlet.class);
    @Resource(name="jdbc/project")
    DataSource dataSource;

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {

        LOG.debug("Start executing doGet");

        HttpSession session = request.getSession(false);

        User user = (User) session.getAttribute("user");

        PaginationHelper paginationHelper = new PaginationHelper();
        int recordsPerPage = PaginationHelper.RECORDS_PER_PAGE;
        int page= paginationHelper.getPageParameter(request);

        UsersReportService service = new UsersReportService(dataSource);

        List<UsersActivityFull> userActivities =
                service.getUserReport(user.getUserId(), page, recordsPerPage);

        int noOfPages = service.getNoOfPages();


        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("recordsPerPage", recordsPerPage);
        request.setAttribute("currentPage", page);

        session.setAttribute("view", "/user_cabinet");
        session.setAttribute("userActivities", userActivities);
        request.getRequestDispatcher("/user_menu.jsp").forward(request, response);

        LOG.debug("Finish executing doGet");
    }
}