package com.project.time_management.servlets.request;

import com.project.time_management.entity.Request;
import com.project.time_management.services.request.DeclineRequestsService;
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

@WebServlet("/decline_request")
public class DeclineRequestsServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(DeclineRequestsServlet.class);
//    private List<Request> activityRequests;

    @Resource(name="jdbc/project")
    DataSource dataSource;

    @Override
    public void init() throws ServletException {

//        final Object activityRequests = getServletContext().getAttribute("activityRequests");
//        if (!(activityRequests instanceof CopyOnWriteArrayList)) {
//            throw new IllegalStateException("Your repo does not initialize!");
//        } else {
//            this.activityRequests = (CopyOnWriteArrayList<Request>) activityRequests;
//        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOG.debug("Start executing doPost");
        final String id = request.getParameter("id");

        DeclineRequestsService processRequest = new DeclineRequestsService(dataSource);
        List<Request> activityRequests = processRequest.declineRequest(id);

        request.setAttribute("activityRequests", activityRequests);
        request.getRequestDispatcher("/requests_list.jsp").forward(request, response);
        LOG.debug("Finish executing doPost");
    }
}
