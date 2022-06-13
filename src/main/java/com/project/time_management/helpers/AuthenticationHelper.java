package com.project.time_management.helpers;

import com.project.time_management.servlets.filters.AuthenticationFilter;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * Class that provides method to check if user with a certain role can access required page.
 */
public class AuthenticationHelper {

    private static final Logger LOG = Logger.getLogger(AuthenticationFilter.class);

    public boolean accessAllowed(String command, String userRole,
                                 List<String> outOfControl,
                                 List<String> commons,
                                 Map<String, List<String>> accessMap) {

        LOG.debug("accessAllowed method starts");
        LOG.trace("userRole  --> " + userRole);

        if (outOfControl.contains(command)) {
            return true;
        }

        if (userRole == null) {
            LOG.trace("user role is null");
            return commons.contains(command) || command == null;
        }

        if (command == null || command.isEmpty()) {
            LOG.trace("command is null or empty");
            return true;
        }

//        if (session == null) {
////            System.out.println("session is null");
//            return false;
//        }

        LOG.trace("Method return --> " + (accessMap.get(userRole).contains(command) || commons.contains(command)));
        return accessMap.get(userRole).contains(command) || commons.contains(command);
    }
}
