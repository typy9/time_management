package com.project.time_management.services;

import com.project.time_management.dao.DBException;
import com.project.time_management.dao.UserCredentialsDAO;
import com.project.time_management.dao.UserDAO;
import com.project.time_management.entity.User;
import com.project.time_management.entity.UserCredentials;
import com.project.time_management.helpers.UserCredentialsValidatorHelper;
import com.project.time_management.utility.Utility;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class RegistrationService {

    private static final Logger LOG = Logger.getLogger(RegistrationService.class);
    private final DataSource dataSource;
    public RegistrationService(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    public boolean checkCredentials(String name, String login, String password) {
        LOG.debug("Start executing checkCredentials");
        final User userInstance = new User(name, "user");

        if (userRegistrationPermitted(name, login, password)) {

            try (Connection conn = dataSource.getConnection()) {
                UserDAO userDAO = new UserDAO(conn);
                userDAO.create(userInstance);
                int userId = userDAO.findId(userInstance);

                UserCredentialsDAO userCredentialsDAO = new UserCredentialsDAO(conn);
                UserCredentials userCredentialsInstance = new UserCredentials(userId, login, password);
                userCredentialsDAO.create(userCredentialsInstance);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (DBException e) {
                throw new RuntimeException(e);
            }
            return true;
        }
        LOG.debug("Finished executing checkCredentials");
        return false;
    }

    /**
     * @param name - the input username on whose behalf the request is being made
     * @param login - the user's login
     * @param password - the user's password
     * @return if @param name, @param login, @param password match their regular expressions.
     *         validationInstance checks if login already exists in the database.
     */
    public boolean userRegistrationPermitted(String name, String login, String password) {

        UserCredentialsValidatorHelper validationInstance = new UserCredentialsValidatorHelper(dataSource);

        return Utility.validateLogin(login)
                && Utility.validatePassword(password)
                && Utility.validateName(name)
                && !validationInstance.loginExists(login);
    }
}
