package com.project.time_management.services.activity;

import com.project.time_management.dao.DBException;
import com.project.time_management.dao.UsersActivitiesDAO;
import com.project.time_management.entity.UsersActivityFull;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ActivityTimeUpdateService {

    private static final Logger LOG = Logger.getLogger(ActivityTimeUpdateService.class);
    private final DataSource dataSource;

    public ActivityTimeUpdateService(DataSource dataSource) {

        this.dataSource = dataSource;
    }

    public UsersActivityFull getUserActivityForUpdate(String id, int userId) {
        LOG.debug("Start executing getUserActivityForUpdate");
        UsersActivityFull userActivity;

        try ( Connection conn = dataSource.getConnection() ){
            UsersActivitiesDAO userActivitiesDAO = new UsersActivitiesDAO(conn);
            Optional<UsersActivityFull> userActivityToUpdate =
                    userActivitiesDAO.findEntityByActivityId(Integer.parseInt(id), userId);

            userActivity = userActivityToUpdate.get();

        } catch (SQLException e) {
            LOG.error("SQLException while getUserActivityForUpdate");
            throw new RuntimeException(e);
        } catch (DBException e) {
            LOG.error("DBException while getUserActivityForUpdate");
            throw new RuntimeException(e);
        }
        LOG.debug("Finished executing getUserActivityForUpdate");
        return userActivity;
    }

    public List<UsersActivityFull> updateActivityTime(String id, int userId, int time) {
        LOG.debug("Start executing updateActivityTime");
        List<UsersActivityFull> userActivities;

        try (Connection conn = dataSource.getConnection()) {

            UsersActivitiesDAO userActivitiesDAO = new UsersActivitiesDAO(conn);
            Optional<UsersActivityFull> userActivityToUpdate =
                    userActivitiesDAO.findEntityByActivityId(Integer.parseInt(id), userId);
            if (userActivityToUpdate.isPresent()) {
                UsersActivityFull userActivity = userActivityToUpdate.get();
                userActivity.setTime(time);
                userActivitiesDAO.updateFullRecord(userActivity);
            }
            userActivities = userActivitiesDAO.findAllUserActivitiesByUserId(userId);
        } catch (SQLException e) {
            LOG.error("SQLException while getUserActivityForUpdate");
            throw new RuntimeException(e);
        } catch (DBException e) {
            LOG.error("DBException while getUserActivityForUpdate");
            throw new RuntimeException(e);
        }
        LOG.debug("Finished executing updateActivityTime");
        return userActivities;
    }
}
