package com.project.time_management.servlets.report;

import com.project.time_management.entity.UsersActivityFull;
import com.project.time_management.helpers.PaginationHelper;
import com.project.time_management.services.report.UsersReportService;
import com.project.time_management.servlets.filters.AuthenticationFilter;
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

@WebServlet("/users_report")
public class UsersReportServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(UsersReportServlet.class);

//    private List<UsersActivityFull> usersActivitiesFull;
    @Resource(name="jdbc/project")
    DataSource dataSource;

    @Override
    public void init() throws ServletException {

//        final Object usersActivitiesFull = getServletContext().getAttribute("usersActivitiesFull");
//
//        if (usersActivitiesFull == null || !(usersActivitiesFull instanceof CopyOnWriteArrayList)) {
//            throw new IllegalStateException("usersActivitiesFull repo did not initialize!");
//        } else {
//            this.usersActivitiesFull = (CopyOnWriteArrayList<UsersActivityFull>) usersActivitiesFull;
//        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOG.debug("Start doGet of the UsersReportServlet");

        PaginationHelper paginationHelper = new PaginationHelper();
        int recordsPerPage = PaginationHelper.RECORDS_PER_PAGE;
        int page= paginationHelper.getPageParameter(request);

        UsersReportService processRequest = new UsersReportService(dataSource);
        List<UsersActivityFull> usersActivitiesFull = processRequest.displayUserReport(page, recordsPerPage);
        int noOfPages = processRequest.getNoOfPages();

        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("recordsPerPage", recordsPerPage);
        request.setAttribute("currentPage", page);
        request.setAttribute("usersActivitiesFull", usersActivitiesFull);

        HttpSession session = request.getSession(false);
        session.setAttribute("view", "/users_report");

        request.getRequestDispatcher("/users_activities.jsp").forward(request, response);

        LOG.debug("Finish doGet of the UsersReportServlet");
    }

    public void destroy() {

    }
}
