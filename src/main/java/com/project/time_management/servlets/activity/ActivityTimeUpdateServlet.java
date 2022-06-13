package com.project.time_management.servlets.activity;

import com.project.time_management.entity.UsersActivityFull;
import com.project.time_management.services.activity.ActivityTimeUpdateService;
import com.project.time_management.services.request.ApproveRequestsService;
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


@WebServlet("/update_time")
public class ActivityTimeUpdateServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(ActivityTimeUpdateServlet.class);
//    private List<UsersActivityFull> userActivities;
    @Resource(name="jdbc/project")
    DataSource dataSource;

    @Override
    public void init() throws ServletException {

//        final Object userActivities = getServletContext().getAttribute("userActivities");
//
//        if (userActivities == null || !(userActivities instanceof CopyOnWriteArrayList)) {
//            throw new IllegalStateException("Your repo does not initialize!");
//        } else {
//            this.userActivities = (CopyOnWriteArrayList<UsersActivityFull>) userActivities;
//        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOG.debug("Start executing doGet");
        final String id = request.getParameter("activity_id");
        final int userId = Integer.parseInt(request.getParameter("user_id"));

        if (idIsInvalid(id)) {
            LOG.debug("'activity_id' is invalid");
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        ActivityTimeUpdateService processRequest = new ActivityTimeUpdateService(dataSource);
        UsersActivityFull userActivity = processRequest.getUserActivityForUpdate(id, userId);

        HttpSession session = request.getSession(false);
        session.setAttribute("view", "/update_time");

        request.getSession().setAttribute("userActivity", userActivity);
        request.getRequestDispatcher("/update_time.jsp").forward(request, response);
        LOG.debug("Finish executing doGet");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOG.debug("Start executing doPost");
        final String id = request.getParameter("activity_id");
        final int userId = Integer.parseInt(request.getParameter("user_id"));
        final int time = Integer.parseInt(request.getParameter("time"));

        if (idIsInvalid(id)) {
            LOG.debug("'activity_id' is invalid");
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        ActivityTimeUpdateService processRequest = new ActivityTimeUpdateService(dataSource);
        List<UsersActivityFull> userActivities = processRequest.updateActivityTime(id, userId, time);

        request.getSession().setAttribute("userActivities", userActivities);
        request.getRequestDispatcher("/user_menu.jsp").forward(request, response);
//        response.sendRedirect(request.getContextPath() + "/user_menu");
        LOG.debug("Finish executing doPost");
    }

    private boolean idIsInvalid(String id) {
        return !(id != null && id.matches("[+]?\\d+"));
    }
}
