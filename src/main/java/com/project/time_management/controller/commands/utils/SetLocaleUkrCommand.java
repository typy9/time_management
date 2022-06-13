package com.project.time_management.controller.commands.utils;

import com.project.time_management.controller.FrontCommand;
import com.project.time_management.controller.commands.user.AddUserCommand;
import org.apache.log4j.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import java.io.IOException;

public class SetLocaleUkrCommand extends FrontCommand {

    private static final Logger LOG = Logger.getLogger(SetLocaleUkrCommand.class);
    @Override
    public void process() throws ServletException, IOException {
        LOG.debug("Start executing Command");
//        request.setAttribute("sessionLocale", "ukr");
//        System.out.println("Path : "+request.getRequestURL());
//        RequestDispatcher requestDispatcher = request.getRequestDispatcher();
//        requestDispatcher.forward(request, response);
    }
}