package com.project.time_management.controller.commands.category;

import com.project.time_management.controller.FrontCommand;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import java.io.IOException;

public class DeleteCategoryCommand extends FrontCommand {
    private static final Logger LOG = Logger.getLogger(DeleteCategoryCommand.class);
    @Override
    public void process() throws ServletException, IOException {
        LOG.debug("Start executing Command");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/category_delete");
        requestDispatcher.forward(request, response);
        LOG.debug("Finished executing Command");
    }
}