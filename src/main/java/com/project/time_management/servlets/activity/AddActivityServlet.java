package com.project.time_management.servlets.activity;


import com.project.time_management.entity.Activity;
import com.project.time_management.helpers.PaginationHelper;
import com.project.time_management.services.activity.AddActivityService;
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


@WebServlet("/activity_add")
public class AddActivityServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(AddActivityServlet.class);
//    private List<Activity> activities;
    @Resource(name="jdbc/project")
    DataSource dataSource;

    @Override
    public void init() throws ServletException {
//        final Object activities = getServletContext().getAttribute("activities");
//
//        if (!(activities instanceof CopyOnWriteArrayList)) {
//            throw new IllegalStateException("Repo activities does not initialize!");
//        } else {
//            this.activities = (CopyOnWriteArrayList<Activity>) activities;
//        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        LOG.debug("Start executing doPost");
        final String name = req.getParameter("name");
        final int category = Integer.parseInt(req.getParameter("category"));

        PaginationHelper paginationHelper = new PaginationHelper();
        int recordsPerPage = PaginationHelper.RECORDS_PER_PAGE;
        int page= paginationHelper.getPageParameter(req);

        AddActivityService processRequest = new AddActivityService(dataSource);
        List<Activity> activities = processRequest.addActivity(name, category, page, recordsPerPage);
        int noOfPages = processRequest.getNoOfPages();

        req.setAttribute("noOfPages", noOfPages);
        req.setAttribute("recordsPerPage", recordsPerPage);
        req.setAttribute("currentPage", page);
        req.setAttribute("activities", activities);
        resp.sendRedirect(req.getContextPath() + "/activities_list");
//        req.getRequestDispatcher("/activities_list.jsp").forward(req, resp);
        LOG.debug("Finish executing doPost");
    }

}
