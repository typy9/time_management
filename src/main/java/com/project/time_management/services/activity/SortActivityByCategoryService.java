package com.project.time_management.services.activity;

import com.project.time_management.dao.ActivityDAO;
import com.project.time_management.dao.DBException;
import com.project.time_management.entity.Activity;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortActivityByCategoryService {

    private static final Logger LOG = Logger.getLogger(SortActivityByCategoryService.class);
    private final DataSource dataSource;

    public SortActivityByCategoryService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Activity> getSortedActivities() {
        LOG.debug("Start executing getSortedActivities");
        List<Activity> activities;
        try (Connection conn = dataSource.getConnection()) {
            ActivityDAO activityDAO = new ActivityDAO(conn);
            activities = activityDAO.findAll();

            activities = activities.stream()
                    .sorted(Comparator.comparing(Activity::getCategory))
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            LOG.error("SQLException while getUserActivityForUpdate");
            throw new RuntimeException(e);
        } catch (DBException e) {
            LOG.error("DBException while getUserActivityForUpdate");
            throw new RuntimeException(e);
        }
        LOG.debug("Finished executing getSortedActivities");
        return activities;
    }
}