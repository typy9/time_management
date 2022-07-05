package com.project.time_management.services.report;

import com.project.time_management.dao.DBException;
import com.project.time_management.dao.RequestDAO;
import com.project.time_management.dao.UsersActivitiesDAO;
import com.project.time_management.entity.UsersActivity;
import com.project.time_management.entity.UsersActivityFull;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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


    public void deleteRecord(int requestId, int userId, int activityId) {

        LOG.debug("Start executing deleteRecord");

        if ( idIsValid(requestId) ) {

            try (Connection conn = dataSource.getConnection()) {
                RequestDAO requestDAO = new RequestDAO(conn);
                UsersActivitiesDAO userActivitiesDAO = new UsersActivitiesDAO(conn);
//                String requestStatus = requestDAO.getStatusById(requestId);


                UsersActivity activityRecord =
                        userActivitiesDAO.findRecord(userId, activityId);


                userActivitiesDAO.delete(activityRecord.getId());


            } catch (SQLException e) {
                LOG.error("SQLException while getUserActivities");
                throw new RuntimeException(e);
            } catch (DBException e) {
                LOG.error("DBException while getUserActivities");
                throw new RuntimeException(e);
            }
        }
        LOG.debug("Finished executing getUserActivities");
    }

    public boolean findByUserIdActivity(int userId, int activityId) {

        LOG.debug("Start executing deleteRecord");

        UsersActivity activityRecord;

        try (Connection conn = dataSource.getConnection()) {

            UsersActivitiesDAO userActivitiesDAO = new UsersActivitiesDAO(conn);

            activityRecord = userActivitiesDAO.findRecord(userId, activityId);

        } catch (SQLException e) {
            LOG.error("SQLException while getUserActivities");
            throw new RuntimeException(e);
        } catch (DBException e) {
            LOG.error("DBException while getUserActivities");
            throw new RuntimeException(e);
        }

        return activityRecord != null;
    }

    public List<UsersActivity> findAll() {

        List<UsersActivity> result;

        try (Connection conn = dataSource.getConnection()) {
            UsersActivitiesDAO dao = new UsersActivitiesDAO(conn);
            result = dao.findAll();

        } catch (SQLException e) {
            LOG.error("SQLException while getUserActivities");
            throw new RuntimeException(e);
        } catch (DBException e) {
            LOG.error("DBException while getUserActivities");
            throw new RuntimeException(e);
        }
        return result;
    }

    private boolean idIsValid(int id) {
        return id > 0;
    }


    public int getNoOfPages() {
        return noOfPages;
    }


    public List<UsersActivityFull> getUserReport(int userId, int page, int recordsPerPage) {

        LOG.debug("Start executing getRecords");

        List<UsersActivityFull> usersActivities;

        try (Connection conn = dataSource.getConnection()){
            UsersActivitiesDAO userActivityDAO = new UsersActivitiesDAO(conn);
            usersActivities = userActivityDAO.findAllUserRecordsWithLimits(userId,(page-1)*recordsPerPage, recordsPerPage);

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
        return usersActivities;
    }
}
