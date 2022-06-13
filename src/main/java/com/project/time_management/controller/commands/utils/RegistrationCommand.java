package com.project.time_management.controller.commands.utils;

import com.project.time_management.controller.FrontCommand;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import java.io.IOException;

public class RegistrationCommand extends FrontCommand {

    private static final Logger LOG = Logger.getLogger(RegistrationCommand.class);
    @Override
    public void process() throws ServletException, IOException {
        LOG.debug("Start executing Command");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/registration");
        requestDispatcher.forward(request, response);
        LOG.debug("Finished executing Command");
    }
}