package com.project.time_management.servlets.activity;


import com.project.time_management.entity.Activity;
import com.project.time_management.helpers.PaginationHelper;
import com.project.time_management.services.activity.DeleteActivityService;
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

@WebServlet("/activity_delete")
public class DeleteActivityServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(DeleteActivityServlet.class);
//    private List<Activity> activities;
    @Resource(name="jdbc/project")
    DataSource dataSource;

    @Override
    public void init() throws ServletException {
//        final Object activities = getServletContext().getAttribute("activities");
//
//        if (!(activities instanceof CopyOnWriteArrayList)) {
//            throw new IllegalStateException("activities repo does not initialize!");
//        } else {
//            this.activities = (CopyOnWriteArrayList<Activity>) activities;
//        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOG.debug("Start executing doPost");
        String id = request.getParameter("id");

        PaginationHelper paginationHelper = new PaginationHelper();
        int recordsPerPage = PaginationHelper.RECORDS_PER_PAGE;
        int page= paginationHelper.getPageParameter(request);

        DeleteActivityService processRequest = new DeleteActivityService(dataSource);
        List<Activity> activities = processRequest.deleteActivity(id, page, recordsPerPage);
        int noOfPages = processRequest.getNoOfPages();

        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("recordsPerPage", recordsPerPage);
        request.setAttribute("currentPage", page);
        request.setAttribute("activities", activities);
        response.sendRedirect(request.getContextPath() + "/activities_list");
//        request.getRequestDispatcher("/activities_list.jsp").forward(request, response);
        LOG.debug("Finish executing doPost");
    }

}
