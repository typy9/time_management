package com.project.time_management.services.request;

import com.project.time_management.dao.DBException;
import com.project.time_management.dao.RequestDAO;
import com.project.time_management.entity.Request;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DisplayRequestsService {

    private static final Logger LOG = Logger.getLogger(DisplayRequestsService.class);
    private final DataSource dataSource;

    public DisplayRequestsService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Request> displayRequests() {
        LOG.debug("Start executing displayRequests");
        List<Request> activityRequests;

        try (Connection conn = dataSource.getConnection()) {
            RequestDAO requestDAO = new RequestDAO(conn);
            activityRequests = requestDAO.findAll();
        } catch (SQLException e) {
            LOG.error("SQLException while displayRequests");
            throw new RuntimeException(e);
        } catch (DBException e) {
            LOG.error("DBException while displayRequests");
            throw new RuntimeException(e);
        }
        LOG.debug("Finished executing displayRequests");
        return activityRequests;
    }
}
