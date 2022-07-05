package com.project.time_management.services.activity;

import com.project.time_management.dao.ActivityDAO;
import com.project.time_management.dao.DBException;
import com.project.time_management.entity.Activity;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UpdateActivityService {

    private static final Logger LOG = Logger.getLogger(UpdateActivityService.class);
    private final DataSource dataSource;

    public UpdateActivityService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Activity getActivityForUpdate(String id) {
        LOG.debug("Start executing getActivityForUpdate");

        Activity activity = null;

        if (idIsNumber(id)) {
            try (Connection conn = dataSource.getConnection()) {

                ActivityDAO activityDAO = new ActivityDAO(conn);
                Optional<Activity> activityToUpdate = activityDAO.findEntityById(Integer.parseInt(id));
                if (activityToUpdate.isPresent()) {
                    activity = activityToUpdate.get();
                }
            } catch (SQLException e) {
                LOG.error("SQLException while getUserActivityForUpdate");
                throw new RuntimeException(e);
            } catch (DBException e) {
                LOG.error("DBException while getUserActivityForUpdate");
                throw new RuntimeException(e);
            }
        }
        LOG.debug("Finished executing getActivityForUpdate");
        return activity;
    }

    public List<Activity> updateActivity(String id, String name, int category) {
        LOG.debug("Start executing updateActivity");

        List<Activity> activities = null;
        if (idIsNumber(id) && idIsNumber(String.valueOf(category))) {
            try (Connection conn = dataSource.getConnection()) {
                ActivityDAO activityDAO = new ActivityDAO(conn);
                Optional<Activity> activityToUpdate = activityDAO.findEntityById(Integer.parseInt(id));
                if (activityToUpdate.isPresent()) {
                    Activity activity = activityToUpdate.get();
                    activity.setName(name);
                    activity.setCategory(category);
                    activityDAO.update(activity);
                }
                activities = activityDAO.findAll();
            } catch (SQLException e) {
                LOG.error("SQLException while updateActivity");
                throw new RuntimeException(e);
            } catch (DBException e) {
                LOG.error("DBException while updateActivity");
                throw new RuntimeException(e);
            }
        }
        LOG.debug("Finished executing updateActivity");
        return activities;
    }
    private boolean idIsNumber(String id) {
        return id != null && (id.length() > 0) && id.matches("[+]?\\d+");
    }
}
