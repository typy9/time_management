package com.project.time_management.controller.commands.request;

import com.project.time_management.controller.FrontCommand;
import com.project.time_management.controller.commands.report.DisplayReportCommand;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import java.io.IOException;

public class AddRequestCommand extends FrontCommand {
    private static final Logger LOG = Logger.getLogger(AddRequestCommand.class);
    @Override
    public void process() throws ServletException, IOException {
        LOG.debug("Start executing Command");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/add_activity_request");
        requestDispatcher.forward(request, response);
        LOG.debug("Finished executing Command");
    }
}