package com.project.time_management.controller.commands.user;

import com.project.time_management.controller.FrontCommand;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import java.io.IOException;

import org.apache.log4j.Logger;

public class AddUserCommand extends FrontCommand {

    private static final Logger LOG = Logger.getLogger(AddUserCommand.class);

    @Override
    public void process() throws ServletException, IOException {
        LOG.debug("Start executing Command");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/add_user");
        requestDispatcher.forward(request, response);
        LOG.debug("Finished executing Command");
    }
}
