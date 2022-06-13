package com.project.time_management.controller.commands.report;

import com.project.time_management.controller.FrontCommand;
import com.project.time_management.controller.commands.category.UpdateCategoryCommand;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import java.io.IOException;

public class DisplayReportCommand extends FrontCommand {
    private static final Logger LOG = Logger.getLogger(DisplayReportCommand.class);
    @Override
    public void process() throws ServletException, IOException {
        LOG.debug("Start executing Command");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/users_report");
        requestDispatcher.forward(request, response);
        LOG.debug("Finished executing Command");
    }
}