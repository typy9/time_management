package com.project.time_management.services.activity;

import com.project.time_management.dao.ActivityDAO;
import com.project.time_management.dao.DBException;
import com.project.time_management.entity.Activity;
import com.project.time_management.services.LoginService;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class AddActivityService {

    private static final Logger LOG = Logger.getLogger(AddActivityService.class);
    private final DataSource dataSource;
    public int noOfPages;

    public AddActivityService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Activity> addActivity(String name, int category,
                                  int page, int recordsPerPage) {
        LOG.debug("Start executing addActivity");
        if (requestIsValid(name, category)) {

            final Activity activityInstance = new Activity(name, category);
            List<Activity> activities;

            try ( Connection conn = dataSource.getConnection()) {
                ActivityDAO activityDAO = new ActivityDAO(conn);
                activityDAO.create(activityInstance);
                activities = activityDAO.findAllWithLimits((page-1)*recordsPerPage, recordsPerPage);

                int noOfRecords = activityDAO.getNoOfRecords();
                noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

            } catch (SQLException e) {
                LOG.error("SQLException while getUserActivityForUpdate");
                throw new RuntimeException(e);
            } catch (DBException e) {
                LOG.error("DBException while getUserActivityForUpdate");
                throw new RuntimeException(e);
            }
            return activities;
        }
        LOG.debug("Finished executing addActivity");
        return Collections.emptyList();
    }

    private boolean requestIsValid(String name, int category) {

        return name != null && name.length() > 0 && category > 0;
    }

    public int getNoOfPages() {
        return noOfPages;
    }
}
