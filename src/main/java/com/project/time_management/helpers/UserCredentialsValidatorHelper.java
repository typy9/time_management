package com.project.time_management.helpers;

import com.project.time_management.dao.DBException;
import com.project.time_management.dao.UserCredentialsDAO;
import com.project.time_management.dao.UserDAO;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * A utility class
 * Methods:
 *          {@code @loginExists}  - returns true if login already exists in the DB.
 *          {@code @nameExists}  - returns true if name already exists in the DB.
 * Throws: SQLException – if a database access error occurs.
 *         DBException - if error occurs in the DAO class.
 */
public class UserCredentialsValidatorHelper {

    private static final Logger LOG = Logger.getLogger(UserCredentialsValidatorHelper.class);
    private final DataSource dataSource;
    public UserCredentialsValidatorHelper(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean loginExists(String login) {
        LOG.debug("loginExists method starts");
        boolean loginExists;

        try (Connection conn = dataSource.getConnection()) {
            UserCredentialsDAO userCredentialsDAO = new UserCredentialsDAO(conn);
            loginExists = userCredentialsDAO.findEntityByName(login);
            LOG.trace("loginExists : " + loginExists);
        } catch (SQLException e) {
            LOG.error("SQLException : " + e);
            throw new RuntimeException(e);
        } catch (DBException e) {
            LOG.error("DBException : " + e);
            throw new RuntimeException(e);
        }
        LOG.trace("loginExists return : " + loginExists);
        return loginExists;
    }

    public boolean nameExists(String name) {
        LOG.debug("nameExists method starts");
        boolean nameExists;

        try (Connection conn = dataSource.getConnection()) {
            UserDAO userDAO = new UserDAO(conn);
            nameExists = userDAO.findEntityByName(name);
            LOG.trace("nameExists : " + nameExists);
        } catch (SQLException e) {
            LOG.error("SQLException : " + e);
            throw new RuntimeException(e);
        } catch (DBException e) {
            LOG.error("DBException : " + e);
            throw new RuntimeException(e);
        }
        LOG.trace("nameExists return : " + nameExists);
        return nameExists;
    }
}
