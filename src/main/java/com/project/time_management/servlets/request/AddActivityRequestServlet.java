package com.project.time_management.servlets.request;


import com.project.time_management.entity.Request;
import com.project.time_management.services.request.AddActivityRequestService;
import com.project.time_management.servlets.activity.ActivityTimeUpdateServlet;
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


@WebServlet("/add_activity_request")
public class AddActivityRequestServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(AddActivityRequestServlet.class);
//    private List<Request> activityRequests;
    @Resource(name="jdbc/project")
    DataSource dataSource;

    @Override
    public void init() throws ServletException {

//        final Object activityRequests = getServletContext().getAttribute("activityRequests");
//        if (!(activityRequests instanceof CopyOnWriteArrayList)) {
//            throw new IllegalStateException("activityRequests repo did not initialize!");
//        } else {
//            this.activityRequests = (CopyOnWriteArrayList<Request>) activityRequests;
//        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        LOG.debug("Start executing doPost");
        final String activity = req.getParameter("activity");
        final int userId = Integer.parseInt(req.getParameter("user_id"));

        AddActivityRequestService processRequest = new AddActivityRequestService(dataSource);
        List<Request> activityRequests = processRequest.addActivityRequest(activity, userId);

        req.setAttribute("activityRequests", activityRequests);
        resp.sendRedirect(req.getContextPath() + "/user_cabinet");
        LOG.debug("Finish executing doPost");
    }


}
