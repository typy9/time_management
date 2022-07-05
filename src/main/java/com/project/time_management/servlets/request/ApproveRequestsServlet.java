package com.project.time_management.servlets.request;

import com.project.time_management.entity.Request;
import com.project.time_management.entity.UsersActivity;
import com.project.time_management.services.request.ApproveRequestsService;
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

@WebServlet("/approve_request")
public class ApproveRequestsServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(ApproveRequestsServlet.class);
//    private List<Request> activityRequests;
//    private List<UsersActivity> usersActivities;
    @Resource(name="jdbc/project")
    DataSource dataSource;

    @Override
    public void init() throws ServletException {
//        final Object activityRequests = getServletContext().getAttribute("activityRequests");
//
//        if (!(activityRequests instanceof CopyOnWriteArrayList)) {
//            throw new IllegalStateException("Your repo does not initialize!");
//        } else {
//            this.activityRequests = (CopyOnWriteArrayList<Request>) activityRequests;
//        }
//
//        final Object usersActivities = getServletContext().getAttribute("usersActivities");
//
//        if (!(usersActivities instanceof CopyOnWriteArrayList)) {
//            throw new IllegalStateException("Your repo does not initialize!");
//        } else {
//            this.usersActivities = (CopyOnWriteArrayList<UsersActivity>) usersActivities;
//        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOG.debug("Start executing doPost");
        final int id = Integer.parseInt(request.getParameter("id"));
        final int userId = Integer.parseInt(request.getParameter("user_id"));
        final int activityId = Integer.parseInt(request.getParameter("activity_id"));

        ApproveRequestsService processRequest = new ApproveRequestsService(dataSource);
        List<Request> activityRequests = processRequest.getRequests(id);
        List<UsersActivity> usersActivities = processRequest.getUserActivities(id, userId, activityId);

        request.setAttribute("activityRequests", activityRequests);
        request.setAttribute("usersActivities", usersActivities);
        response.sendRedirect(request.getContextPath() + "/requests");
//        request.getRequestDispatcher("/requests_list.jsp").forward(request, response);
        LOG.debug("Finish executing doPost");
    }


}
