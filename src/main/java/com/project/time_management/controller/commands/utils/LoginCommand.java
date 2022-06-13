package com.project.time_management.controller.commands.utils;

import com.project.time_management.controller.FrontCommand;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import java.io.IOException;

public class LoginCommand extends FrontCommand {

    private static final Logger LOG = Logger.getLogger(LoginCommand.class);
    @Override
    public void process() throws ServletException, IOException {
        LOG.debug("Start executing Command");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/login");
        requestDispatcher.forward(request, response);
        LOG.debug("Finished executing Command");
    }
}