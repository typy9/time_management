package com.project.time_management.services.request;

import com.project.time_management.dao.DBException;
import com.project.time_management.dao.RequestDAO;
import com.project.time_management.entity.Request;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DeclineRequestsService {

    private static final Logger LOG = Logger.getLogger(DeclineRequestsService.class);
    private final DataSource dataSource;

    public DeclineRequestsService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Request> declineRequest(String id) {
        LOG.debug("Start executing declineRequest");
        List<Request> activityRequests = null;
        if ( idIsNumber(id) ) {

            try (Connection conn = dataSource.getConnection()) {
                RequestDAO requestDAO = new RequestDAO(conn);
                requestDAO.updateStatusById(Integer.parseInt(id), "declined");
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

    private boolean idIsNumber(String id) {
        return id != null && (id.length() > 0) && id.matches("[+]?\\d+");
    }
}
