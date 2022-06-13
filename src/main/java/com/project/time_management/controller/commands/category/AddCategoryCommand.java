package com.project.time_management.controller.commands.category;

import com.project.time_management.controller.FrontCommand;
import com.project.time_management.controller.commands.activity.UpdateActivityTimeCommand;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import java.io.IOException;

public class AddCategoryCommand extends FrontCommand {
    private static final Logger LOG = Logger.getLogger(AddCategoryCommand.class);
    @Override
    public void process() throws ServletException, IOException {
        LOG.debug("Start executing Command");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/category_add");
        requestDispatcher.forward(request, response);
        LOG.debug("Finished executing Command");
    }
}


