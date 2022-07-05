package com.project.time_management.servlets.activity;

import com.project.time_management.entity.Activity;
import com.project.time_management.helpers.PaginationHelper;
import com.project.time_management.services.activity.DisplayActivitiesService;
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

@WebServlet("/activities_list")
public class DisplayActivitiesServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(DisplayActivitiesServlet.class);
    @Resource(name="jdbc/project")
    DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void init() throws ServletException {
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {

        LOG.debug("Start executing doGet");
        PaginationHelper paginationHelper = new PaginationHelper();
        int recordsPerPage = PaginationHelper.RECORDS_PER_PAGE;
        int page= paginationHelper.getPageParameter(request);

        DisplayActivitiesService processRequest = new DisplayActivitiesService(dataSource);
        List<Activity> activities = processRequest.getActivities(page, recordsPerPage);
        int noOfPages = processRequest.getNoOfPages();

        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("recordsPerPage", recordsPerPage);
        request.setAttribute("currentPage", page);
        request.setAttribute("activities", activities);
        HttpSession session = request.getSession(false);
        session.setAttribute("view", "/activities_list");

        request.getRequestDispatcher("/activities_list.jsp").forward(request, response);
        LOG.debug("Finish executing doGet");
    }

    public void destroy() {

    }
}
