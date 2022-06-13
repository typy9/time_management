package com.project.time_management.services.report;

import com.project.time_management.dao.DBException;
import com.project.time_management.dao.UsersActivitiesDAO;
import com.project.time_management.entity.UsersActivityFull;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UsersReportService {

    private static final Logger LOG = Logger.getLogger(UsersReportService.class);
    private final DataSource dataSource;
    public int noOfPages;
    public UsersReportService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<UsersActivityFull> displayUserReport(int page, int recordsPerPage) {
        LOG.debug("Start executing displayUserReport");
        List<UsersActivityFull> usersActivitiesFull;

        try (Connection conn = dataSource.getConnection()){
            UsersActivitiesDAO userActivityDAO = new UsersActivitiesDAO(conn);
            usersActivitiesFull = userActivityDAO.findAllWithLimits((page-1)*recordsPerPage, recordsPerPage);

            int noOfRecords = userActivityDAO.getNoOfRecords();
            noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

        } catch (SQLException e) {
            LOG.error("SQLException while displayUserReport");
            throw new RuntimeException(e);
        } catch (DBException e) {
            LOG.error("DBException while displayUserReport");
            throw new RuntimeException(e);
        }
        LOG.debug("Finished executing displayUserReport");
        return usersActivitiesFull;
    }

    public int getNoOfPages() {
        return noOfPages;
    }
}
