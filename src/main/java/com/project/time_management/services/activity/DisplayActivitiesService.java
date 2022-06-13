package com.project.time_management.services.activity;

import com.project.time_management.dao.ActivityDAO;
import com.project.time_management.dao.DBException;
import com.project.time_management.entity.Activity;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DisplayActivitiesService {

    private static final Logger LOG = Logger.getLogger(DisplayActivitiesService.class);
    private final DataSource dataSource;
    private int noOfPages;

    public DisplayActivitiesService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Activity> getActivities(int page, int recordsPerPage) {

        LOG.debug("Start executing getActivities");

        List<Activity> activities;
        try ( Connection conn = dataSource.getConnection() ) {

            ActivityDAO activityDAO = new ActivityDAO(conn);
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
        LOG.debug("Finished executing getActivities");
        return activities;
    }

    public int getNoOfPages() {
        return noOfPages;
    }
}
