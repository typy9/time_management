package com.project.time_management.services.user;

import com.project.time_management.dao.DBException;
import com.project.time_management.dao.UserDAO;
import com.project.time_management.entity.User;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DisplayUserService {

    private static final Logger LOG = Logger.getLogger(DisplayUserService.class);
    private final DataSource dataSource;
    private int noOfPages;

    public DisplayUserService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<User> getUsers(int page, int recordsPerPage) {
        LOG.debug("Start executing getUsers");
        List<User> users;

        try (Connection conn = dataSource.getConnection()) {
            UserDAO userDAO = new UserDAO(conn);
            users = userDAO.findAllWithLimits((page - 1) * recordsPerPage, recordsPerPage);

            int noOfRecords = userDAO.getNoOfRecords();
            noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

        } catch (SQLException e) {
            LOG.error("SQLException while getUsers");
            throw new RuntimeException(e);
        } catch (DBException e) {
            LOG.error("DBException while getUsers");
            throw new RuntimeException(e);
        }
        LOG.debug("Finished executing getUsers");
        return users;
    }

    public int getNoOfPages() {
        return noOfPages;
    }
}
