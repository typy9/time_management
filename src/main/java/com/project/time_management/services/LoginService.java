package com.project.time_management.services;

import com.project.time_management.dao.DBException;
import com.project.time_management.dao.UserCredentialsDAO;
import com.project.time_management.dao.UsersActivitiesDAO;
import com.project.time_management.entity.User;
import com.project.time_management.entity.UsersActivityFull;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class LoginService {

    private static final Logger LOG = Logger.getLogger(LoginService.class);
    public User processUser(String login, String password,
                            AtomicReference<UserCredentialsDAO> dao) {
        LOG.debug("Start executing processUser");
        User user;
        try {
            user = dao.get().getUserByLoginPassword(login, password);
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
        LOG.debug("Finished executing processUser");
        return user;
    }

    public List<UsersActivityFull> processUserActivities(User user,
            AtomicReference<UsersActivitiesDAO> usersActivitiesDao) {
        LOG.debug("Start executing processUser");
        List<UsersActivityFull> userActivities;
        try {
            userActivities = usersActivitiesDao.get().findAllUserActivitiesByUserId(user.getUserId());
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
        LOG.debug("Finished executing processUser");
        return userActivities;
    }
}

