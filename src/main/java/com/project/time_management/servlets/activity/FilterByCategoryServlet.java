package com.project.time_management.servlets.activity;

import com.project.time_management.entity.Activity;
import com.project.time_management.services.activity.FilterByCategoryService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/filter_by_category")
public class FilterByCategoryServlet extends DisplayActivitiesServlet {

    private static final Logger LOG = Logger.getLogger(FilterByCategoryServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOG.debug("Start executing doPost");
        final int id = Integer.parseInt(request.getParameter("id"));

        FilterByCategoryService processRequest = new FilterByCategoryService(dataSource);

        List<Activity> activities = processRequest.getActivities(id);

        System.out.println("activities ----> " + activities);

        request.setAttribute("currentPage", 1);
        request.setAttribute("activities", activities);
//        response.sendRedirect(request.getContextPath() + "/activities_list");
        request.getRequestDispatcher("/activities_list.jsp").forward(request, response);
        LOG.debug("Finish executing doPost");
    }
}
