package com.project.time_management.services.request;

import com.project.time_management.dao.DBException;
import com.project.time_management.dao.RequestDAO;
import com.project.time_management.dao.UsersActivitiesDAO;
import com.project.time_management.entity.Request;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeleteRequestsService {

    private static final Logger LOG = Logger.getLogger(DeleteRequestsService.class);
    private final DataSource dataSource;

    public DeleteRequestsService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Request> deleteRequestUser(String userId, String activityId) {

        LOG.debug("Start executing deleteRequestUser");

        List<Request> activityRequests = null;

        if ( idIsNumber(userId) && idIsNumber(activityId) ) {

            int requestUserId = Integer.parseInt(userId);
            int userActivityId = Integer.parseInt(activityId);



            try (Connection conn = dataSource.getConnection()) {
                RequestDAO requestDAO = new RequestDAO(conn);

                UsersActivitiesDAO userActivityDAO = new UsersActivitiesDAO(conn);
                int requestActivityId = userActivityDAO.findEntityById(userActivityId).get().getActivity_id();

                if ( !requestDAO.findByUserIdActivityIdStatus(
                        requestUserId, requestActivityId, "tobedeleted")) {

                    requestDAO.updateStatusByUserIdActivityId(
                            requestUserId, requestActivityId, "tobedeleted");
                }
                activityRequests = requestDAO.findAll();

            } catch (SQLException e) {
                LOG.error("SQLException while declineRequest");
                throw new RuntimeException(e);
            } catch (DBException e) {
                LOG.error("DBException while declineRequest");
                throw new RuntimeException(e);
            }
        }
        LOG.debug("Finished executing declineRequest");
        return activityRequests;
    }

    public void deleteRequest(int requestId) throws SQLException{
        LOG.debug("Start executing deleteRequest");

        if (idIsNumber(String.valueOf(requestId))) {
            try (Connection conn = dataSource.getConnection()) {
                RequestDAO requestDAO = new RequestDAO(conn);
                requestDAO.delete(requestId);
            } catch (DBException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<Request> findAll() throws SQLException{
        LOG.debug("Start executing deleteRequest");

        List<Request> result;
        try (Connection conn = dataSource.getConnection()) {
            RequestDAO requestDAO = new RequestDAO(conn);
            result = requestDAO.findAll();
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private boolean idIsNumber(String id) {
        return id != null && (id.length() > 0) && id.matches("[+]?\\d+");
    }


}
