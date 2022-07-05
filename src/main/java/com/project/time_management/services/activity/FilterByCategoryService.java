package com.project.time_management.services.activity;

import com.project.time_management.dao.ActivityDAO;
import com.project.time_management.dao.DBException;
import com.project.time_management.entity.Activity;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class FilterByCategoryService {

    private static final Logger LOG = Logger.getLogger(FilterByCategoryService.class);
    private final DataSource dataSource;

    public FilterByCategoryService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Activity> getActivities(int id) {

        LOG.debug("Start executing getActivities");

        List<Activity> activities = null;

        if (id > 0) {
            try (Connection conn = dataSource.getConnection()) {

                ActivityDAO activityDAO = new ActivityDAO(conn);
                activities = activityDAO.findActivitiesByCategory(id);

            } catch (SQLException e) {
                LOG.error("SQLException while getUserActivityForUpdate");
                throw new RuntimeException(e);
            } catch (DBException e) {
                LOG.error("DBException while getUserActivityForUpdate");
                throw new RuntimeException(e);
            }
        }

        LOG.debug("Finished executing getActivities");

        return activities;
    }

}
