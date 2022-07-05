package com.project.time_management.services.request;

import com.project.time_management.dao.DBException;
import com.project.time_management.dao.RequestDAO;
import com.project.time_management.dao.UsersActivitiesDAO;
import com.project.time_management.entity.Request;
import com.project.time_management.entity.UsersActivity;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class ApproveRequestsService {

    private static final Logger LOG = Logger.getLogger(ApproveRequestsService.class);
    private final DataSource dataSource;

    public ApproveRequestsService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Request> getRequests(int id) {
        LOG.debug("Start executing getRequests");
        List<Request> activityRequests = null;

        if ( idIsValid(id) ) {

            try (Connection conn = dataSource.getConnection()) {
                RequestDAO requestDAO = new RequestDAO(conn);
                activityRequests = requestDAO.findAll();
            } catch (SQLException e) {
                LOG.error("SQLException while getRequests");
                throw new RuntimeException(e);
            } catch (DBException e) {
                LOG.error("DBException while getRequests");
                throw new RuntimeException(e);
            }
        }
        LOG.debug("Finished executing getRequests");
        return activityRequests;
    }

    public List<UsersActivity> getUserActivities(int id, int userId, int activityId) {
        LOG.debug("Start executing getUserActivities");
        List<UsersActivity> usersActivities = null;

        if ( idIsValid(id) ) {

            try (Connection conn = dataSource.getConnection()) {
                RequestDAO requestDAO = new RequestDAO(conn);
                UsersActivitiesDAO userActivitiesDAO = new UsersActivitiesDAO(conn);
                String requestStatus = requestDAO.getStatusById(id);

                if (!Objects.equals(requestStatus, "approved")) {
                    requestDAO.updateStatusById(id, "approved");
                    UsersActivity activityRecord = UsersActivity.createUserActivity(userId, activityId);
                    userActivitiesDAO.create(activityRecord);
                }
                usersActivities = userActivitiesDAO.findAll();
            } catch (SQLException e) {
                LOG.error("SQLException while getUserActivities");
                throw new RuntimeException(e);
            } catch (DBException e) {
                LOG.error("DBException while getUserActivities");
                throw new RuntimeException(e);
            }
        }
        LOG.debug("Finished executing getUserActivities");
        return usersActivities;
    }

    private boolean idIsValid(int id) {
        return id > 0;
    }
}
