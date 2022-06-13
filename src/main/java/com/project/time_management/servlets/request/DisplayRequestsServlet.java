package com.project.time_management.servlets.request;


import com.project.time_management.entity.Request;
import com.project.time_management.services.request.DisplayRequestsService;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;


@WebServlet("/requests")
public class DisplayRequestsServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(DisplayRequestsServlet.class);
//    private List<Request> activityRequests;
    @Resource(name="jdbc/project")
    DataSource dataSource;

    @Override
    public void init() throws ServletException {
//        final Object activityRequests = getServletContext().getAttribute("activityRequests");
//
//        if (!(activityRequests instanceof CopyOnWriteArrayList)) {
//            throw new IllegalStateException("activityRequests repo did not initialize!");
//        } else {
//            this.activityRequests = (CopyOnWriteArrayList<Request>) activityRequests;
//        }
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {
        LOG.debug("Start executing doGet");
        DisplayRequestsService processRequest = new DisplayRequestsService(dataSource);
        List<Request> activityRequests = processRequest.displayRequests();

        request.setAttribute("activityRequests", activityRequests);
        HttpSession session = request.getSession(false);
        session.setAttribute("view", "/requests");

        request.getRequestDispatcher("/requests_list.jsp").forward(request, response);
        LOG.debug("Finish executing doGet");
    }

    public void destroy() {

    }
}
