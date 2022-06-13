package com.project.time_management.services.user;

import com.project.time_management.dao.DBException;
import com.project.time_management.dao.UserCredentialsDAO;
import com.project.time_management.dao.UserDAO;
import com.project.time_management.entity.User;
import com.project.time_management.entity.UserCredentials;
import com.project.time_management.services.request.DisplayRequestsService;
import com.project.time_management.utility.Utility;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class AddUserService {

    private static final Logger LOG = Logger.getLogger(AddUserService.class);
    private final DataSource dataSource;
    public int noOfPages;

    public AddUserService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<User> addUser(String name, String role, String login, String password,
                                     int page, int recordsPerPage) {
        LOG.debug("Start executing addUser");
        if (requestIsValid(name, role, login, password)) {

            final User userInstance = new User(name, role);

            List<User> users;
            try (Connection conn = dataSource.getConnection()) {
                UserDAO userDAO = new UserDAO(conn);
                userDAO.create(userInstance);
                int userId = userDAO.findId(userInstance);

                UserCredentialsDAO userCredentialsDAO = new UserCredentialsDAO(conn);
                UserCredentials userCredentialsInstance = new UserCredentials(userId, login, password);
                userCredentialsDAO.create(userCredentialsInstance);

                users = userDAO.findAllWithLimits((page - 1) * recordsPerPage, recordsPerPage);
                int noOfRecords = userDAO.getNoOfRecords();
                noOfPages = (int) Math.ceil(noOfRecords * (1.0 / recordsPerPage));

            } catch (SQLException e) {
                LOG.error("SQLException while addUser");
                throw new RuntimeException(e);
            } catch (DBException e) {
                LOG.error("DBException while addUser");
                throw new RuntimeException(e);
            }
            return users;
        }
        LOG.debug("Finished executing addUser");
        return Collections.emptyList();
    }

    private boolean requestIsValid(String name, String role, String login, String password) {
        return name != null && role != null && role.length() > 0
                && Utility.validateLogin(login)
                && Utility.validatePassword(password)
                && Utility.validateName(name);
    }

    public int getNoOfPages() {
        return noOfPages;
    }
}