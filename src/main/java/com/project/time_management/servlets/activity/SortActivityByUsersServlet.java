package com.project.time_management.servlets.activity;

import com.project.time_management.entity.Activity;
import com.project.time_management.services.activity.SortActivityByUsersService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/activity_sort_by_number_of_users")
public class SortActivityByUsersServlet extends DisplayActivitiesServlet {

    private static final Logger LOG = Logger.getLogger(SortActivityByUsersServlet.class);
//    List<UsersActivity> usersActivities;

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {

        LOG.debug("Start executing doGet");
        SortActivityByUsersService processRequest = new SortActivityByUsersService(dataSource);
        List<Activity> activities = processRequest.getSortedActivities();

        request.setAttribute("currentPage", 1);
        request.setAttribute("activities", activities);
        HttpSession session = request.getSession(false);
        session.setAttribute("view", "/activity_sort_by_number_of_users");

        request.getRequestDispatcher("/activities_list.jsp").forward(request, response);
//        response.sendRedirect(request.getContextPath() + "/activities_list");
        LOG.debug("Finish executing doGet");
    }

}
