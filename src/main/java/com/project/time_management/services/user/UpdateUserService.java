package com.project.time_management.services.user;

import com.project.time_management.dao.DBException;
import com.project.time_management.dao.UserDAO;
import com.project.time_management.entity.User;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UpdateUserService {

    private static final Logger LOG = Logger.getLogger(UpdateUserService.class);
    private final DataSource dataSource;
    public int noOfPages;
    public UpdateUserService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public User getUserForUpdate(String id) {
        LOG.debug("Start executing getUserForUpdate");
        User user = null;

        if (idIsNumber(id)) {
            try (Connection conn = dataSource.getConnection()) {
                UserDAO userDAO = new UserDAO(conn);
                Optional<User> userToUpdate = userDAO.findEntityById(Integer.parseInt(id));
                if (userToUpdate.isPresent()) {
                    user = userToUpdate.get();
                }
            } catch (SQLException e) {
                LOG.error("SQLException while getUserForUpdate");
                throw new RuntimeException(e);
            } catch (DBException e) {
                LOG.error("DBException while getUserForUpdate");
                throw new RuntimeException(e);
            }
        }
        LOG.debug("Finished executing getUserForUpdate");
        return user;
    }

    public List<User> updateUser(String id, String name, String role) {
        LOG.debug("Start executing updateUser");

        List<User> users = null;

        if (idIsNumber(id)) {
            try (Connection conn = dataSource.getConnection()) {
                UserDAO userDAO = new UserDAO(conn);
                Optional<User> userToUpdate = userDAO.findEntityById(Integer.parseInt(id));
                if (userToUpdate.isPresent()) {
                    User user = userToUpdate.get();
                    user.setName(name);
                    user.setRole(role);
                    userDAO.update(user);
                }
                users = userDAO.findAll();

            } catch (SQLException e) {
                LOG.error("SQLException while updateUser");
                throw new RuntimeException(e);
            } catch (DBException e) {
                LOG.error("DBException while updateUser");
                throw new RuntimeException(e);
            }
        }
        LOG.debug("Finished executing updateUser");
        return users;
    }

    private boolean idIsNumber(String id) {
        return id != null && (id.length() > 0) && id.matches("[+]?\\d+");
    }
}
