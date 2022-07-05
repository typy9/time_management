package com.project.time_management.servlets.request;

import com.project.time_management.entity.Request;
import com.project.time_management.entity.UsersActivity;
import com.project.time_management.services.report.UsersReportService;
import com.project.time_management.services.request.DeleteRequestsService;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/delete_request_admin")
public class DeleteRequestAdminServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(DeleteRequestAdminServlet.class);

    @Resource(name="jdbc/project")
    DataSource dataSource;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOG.debug("Start executing doPost");
        final int requestId = Integer.parseInt(request.getParameter("id"));
        final int userId = Integer.parseInt(request.getParameter("user_id"));
        final int activityId = Integer.parseInt(request.getParameter("activity_id"));

        DeleteRequestsService requestsService = new DeleteRequestsService(dataSource);

        try {
            requestsService.deleteRequest(requestId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<Request> activityRequests;
        try {
            activityRequests = requestsService.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        UsersReportService usersActivitiesService = new UsersReportService(dataSource);

        if ( usersActivitiesService.findByUserIdActivity(userId, activityId) ) {

            usersActivitiesService.deleteRecord(requestId, userId, activityId);

        }

        List<UsersActivity> usersActivities = usersActivitiesService.findAll();

        request.setAttribute("activityRequests", activityRequests);
        request.setAttribute("usersActivities", usersActivities);
        response.sendRedirect(request.getContextPath() + "/requests");
        LOG.debug("Finish executing doPost");
    }
}
