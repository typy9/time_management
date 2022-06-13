package com.project.time_management.servlets.activity;

import com.project.time_management.entity.Activity;
import com.project.time_management.services.activity.UpdateActivityService;
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


@WebServlet("/activity_update")
public class UpdateActivityServlet extends HttpServlet {


    private static final Logger LOG = Logger.getLogger(UpdateActivityServlet.class);

    @Resource(name="jdbc/project")
    DataSource dataSource;

    @Override
    public void init() throws ServletException {
//        final Object activities = getServletContext().getAttribute("activities");
//
//        if (activities == null || !(activities instanceof CopyOnWriteArrayList)) {
//            throw new IllegalStateException("Repo activities does not initialize!");
//        } else {
//            this.activities = (CopyOnWriteArrayList<Activity>) activities;
//        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOG.debug("Start executing doGet");
        final String id = request.getParameter("id");

        if (idIsInvalid(id)) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        UpdateActivityService processRequest = new UpdateActivityService(dataSource);
        Activity activity = processRequest.getActivityForUpdate(id);
        request.setAttribute("activity", activity);
        request.getRequestDispatcher("/update_activity.jsp").forward(request, response);
        LOG.debug("Finish executing doGet");
    }

    private boolean idIsInvalid(String id) {
        return !(id != null && id.matches("[+]?\\d+"));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOG.debug("Start executing doPost");
        final String id = request.getParameter("id");
        final String name = request.getParameter("name");
        final int category = Integer.parseInt(request.getParameter("category"));

        UpdateActivityService processRequest = new UpdateActivityService(dataSource);
        List<Activity> activities = processRequest.updateActivity(id, name, category);

        request.setAttribute("activities", activities);
        response.sendRedirect(request.getContextPath() + "/activities_list");
//        request.getRequestDispatcher("/activities_list.jsp").forward(request, response);
        LOG.debug("Finish executing doPost");
    }
}
