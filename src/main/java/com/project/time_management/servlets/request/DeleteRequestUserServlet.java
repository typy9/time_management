package com.project.time_management.servlets.request;

import com.project.time_management.entity.Request;
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
import java.util.List;

@WebServlet("/delete_request_user")
public class DeleteRequestUserServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(DeleteRequestUserServlet.class);

    @Resource(name="jdbc/project")
    DataSource dataSource;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOG.debug("Start executing doGet");
        final String userId = request.getParameter("user_id");
        final String activityId = request.getParameter("activity_id");


        DeleteRequestsService processRequest = new DeleteRequestsService(dataSource);
        List<Request> activityRequests = processRequest.deleteRequestUser(userId, activityId);

        request.setAttribute("activityRequests", activityRequests);
        response.sendRedirect(request.getContextPath() + "/user_cabinet");
        LOG.debug("Finish executing doGet");
    }
}
