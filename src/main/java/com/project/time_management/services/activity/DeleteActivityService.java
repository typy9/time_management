package com.project.time_management.services.activity;

import com.project.time_management.dao.ActivityDAO;
import com.project.time_management.dao.DBException;
import com.project.time_management.entity.Activity;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DeleteActivityService {
    private static final Logger LOG = Logger.getLogger(DeleteActivityService.class);
    private final DataSource dataSource;
    public int noOfPages;
    public DeleteActivityService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Activity> deleteActivity(String id, int page, int recordsPerPage) {
        LOG.debug("Start executing deleteActivity");
        List<Activity> activities = null;

        if (idIsNumber(id)) {

            try (Connection conn = dataSource.getConnection()) {

                ActivityDAO activityDAO = new ActivityDAO(conn);
                activityDAO.delete(Integer.valueOf(id));

                activities = activityDAO.findAllWithLimits((page-1)*recordsPerPage, recordsPerPage);
                int noOfRecords = activityDAO.getNoOfRecords();
                noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
            } catch (SQLException e) {
                LOG.error("SQLException while getUserActivityForUpdate");
                throw new RuntimeException(e);
            } catch (DBException e) {
                LOG.error("DBException while getUserActivityForUpdate");
                throw new RuntimeException(e);
            }
        }
        LOG.debug("Finished executing deleteActivity");
        return activities;
    }

    private boolean idIsNumber(String id) {
        return id != null && (id.length() > 0) && id.matches("[+]?\\d+");
    }

    public int getNoOfPages() {
        return noOfPages;
    }
}
