package com.project.time_management.servlets;

import com.project.time_management.dao.*;
import com.project.time_management.entity.*;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;

@WebListener
public class ContextListener implements ServletContextListener {

    private static final Logger LOG = Logger.getLogger(ContextListener.class);
    private AtomicReference<UserCredentialsDAO> dao;
    private AtomicReference<UsersActivitiesDAO> usersActivitiesDao;
    private List<User> users;
    private List<Activity> activities;
    private List<Category> categories;
    private List<UsersActivity> usersActivities;
    private List<UsersActivityFull> usersActivitiesFull;

    private List<Request> activityRequests;
    @Resource(name="jdbc/project")
    DataSource dataSource;

    public ContextListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        LOG.trace("Start ContextListener initializer");
        final ServletContext servletContext = servletContextEvent.getServletContext();

        users = new CopyOnWriteArrayList<>();
        servletContext.setAttribute("users", users);

        activities = new CopyOnWriteArrayList<>();
        servletContext.setAttribute("activities", activities);

        categories = new CopyOnWriteArrayList<>();
        servletContext.setAttribute("categories", categories);

        usersActivities = new CopyOnWriteArrayList<>();
        servletContext.setAttribute("usersActivities", usersActivities);

        usersActivitiesFull = new CopyOnWriteArrayList<>();
        servletContext.setAttribute("usersActivitiesFull", usersActivitiesFull);

        activityRequests = new CopyOnWriteArrayList<>();
        servletContext.setAttribute("activityRequests", activityRequests);

        // ------------------------------------------------------

        try ( Connection conn = dataSource.getConnection() ) {
            UserDAO userDAO = new UserDAO(conn);
            ActivityDAO activityDAO = new ActivityDAO(conn);
            CategoryDAO categoryDAO = new CategoryDAO(conn);
            users = userDAO.findAll();
            activities = activityDAO.findAll();
            categories = categoryDAO.findAll();
        } catch (SQLException e) {
            LOG.error("SQLException while getting initial data from the DB");
            throw new RuntimeException(e);
        } catch (DBException e) {
            LOG.error("DBException while getRequests");
            throw new RuntimeException(e);
        }

        Connection conn;
        try {
            conn = dataSource.getConnection();
            dao = new AtomicReference<>(new UserCredentialsDAO(conn));
            usersActivitiesDao = new AtomicReference<>(new UsersActivitiesDAO(conn));
        } catch (SQLException e) {
            LOG.error("SQLException while getting UserCredentialsDAO");
            throw new RuntimeException(e);
        }
        servletContext.setAttribute("dao", dao);
        servletContext.setAttribute("usersActivitiesDao", usersActivitiesDao);
        LOG.trace("Finished ContextListener initializer");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

}
