package com.project.time_management.servlets.request;


import com.project.time_management.entity.Request;
import com.project.time_management.helpers.PaginationHelper;
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
    @Resource(name="jdbc/project")
    DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void init() throws ServletException {

    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {

        LOG.debug("Start executing doGet");

        PaginationHelper paginationHelper = new PaginationHelper();
        int recordsPerPage = PaginationHelper.RECORDS_PER_PAGE;
        int page= paginationHelper.getPageParameter(request);

        DisplayRequestsService processRequest = new DisplayRequestsService(dataSource);
        List<Request> activityRequests = processRequest.getRequests(page, recordsPerPage);
        int noOfPages = processRequest.getNoOfPages();

        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("recordsPerPage", recordsPerPage);
        request.setAttribute("currentPage", page);
        request.setAttribute("activityRequests", activityRequests);
        HttpSession session = request.getSession(false);
        session.setAttribute("view", "/requests");

        request.getRequestDispatcher("/requests_list.jsp").forward(request, response);
        LOG.debug("Finish executing doGet");
    }

    @Override
    public void destroy() {

    }
}
