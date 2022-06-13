package com.project.time_management.services.activity;

import com.project.time_management.dao.ActivityDAO;
import com.project.time_management.dao.DBException;
import com.project.time_management.dao.UsersActivitiesDAO;
import com.project.time_management.entity.Activity;
import com.project.time_management.entity.UsersActivity;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


public class SortActivityByUsersService {

    private static final Logger LOG = Logger.getLogger(SortActivityByUsersService.class);
    private final DataSource dataSource;

    public SortActivityByUsersService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Activity> getSortedActivities() {
        LOG.debug("Start executing getSortedActivities");
        List<Activity> activities = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            ActivityDAO activityDAO = new ActivityDAO(conn);

            UsersActivitiesDAO usersActivitiesDAO = new UsersActivitiesDAO(conn);
            List<UsersActivity> usersActivities = usersActivitiesDAO.findAllUsersWithActivity();
            for (UsersActivity userActivityRecord : usersActivities) {

                int id = userActivityRecord.getActivity_id();
                Optional<Activity> activityOptional = activityDAO.findEntityById(id);

                if (activityOptional.isPresent()) {
                    Activity activity = activityOptional.get();
                    activities.add(activity);
                }
            }
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
