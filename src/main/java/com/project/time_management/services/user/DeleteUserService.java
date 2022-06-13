package com.project.time_management.services.user;

import com.project.time_management.dao.DBException;
import com.project.time_management.dao.UserDAO;
import com.project.time_management.entity.User;
import com.project.time_management.services.request.DisplayRequestsService;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DeleteUserService {

    private static final Logger LOG = Logger.getLogger(DeleteUserService.class);
    private final DataSource dataSource;
    public int noOfPages;
    public DeleteUserService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<User> deleteUser (String id, int page, int recordsPerPage) {
        LOG.debug("Start executing deleteUser");
        List<User> users = null;

        if (idIsNumber(id)) {

            try (Connection conn = dataSource.getConnection()) {

                UserDAO userDAO = new UserDAO(conn);
                userDAO.delete(Integer.valueOf(id));

                users = userDAO.findAllWithLimits((page-1)*recordsPerPage, recordsPerPage);
                int noOfRecords = userDAO.getNoOfRecords();
                noOfPages = (int) Math.ceil(noOfRecords * (1.0 / recordsPerPage));
            } catch (SQLException e) {
                LOG.error("SQLException while deleteUser");
                throw new RuntimeException(e);
            } catch (DBException e) {
                LOG.error("DBException while deleteUser");
                throw new RuntimeException(e);
            }
        }
        LOG.debug("Finished executing displayRequests");
        return users;
    }

    private boolean idIsNumber(String id) {
        return id != null && (id.length() > 0) && id.matches("[+]?\\d+");
    }

    public int getNoOfPages() {
        return noOfPages;
    }
}
