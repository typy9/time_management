package com.project.time_management.controller.commands.request;

import com.project.time_management.controller.FrontCommand;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import java.io.IOException;

public class DeleteRequestAdminCommand extends FrontCommand {

    private static final Logger LOG = Logger.getLogger(DeleteRequestAdminCommand.class);

    @Override
    public void process() throws ServletException, IOException {
        LOG.debug("Start executing Command");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/delete_request_admin");
        requestDispatcher.forward(request, response);
        LOG.debug("Finished executing Command");
    }
}