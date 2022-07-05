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
    private int noOfPages;

    public DisplayRequestsService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Request> getRequests(int page, int recordsPerPage) {

        LOG.debug("Start executing displayRequests");

        List<Request> activityRequests;

        try (Connection conn = dataSource.getConnection()) {

            RequestDAO requestDAO = new RequestDAO(conn);
            activityRequests = requestDAO.findAllWithLimits((page-1)*recordsPerPage, recordsPerPage);
            int noOfRecords = requestDAO.getNoOfRecords();
            noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

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

    public int getNoOfPages() {
        return noOfPages;
    }
}
