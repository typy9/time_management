package com.project.time_management.controller.commands.utils;

import com.project.time_management.controller.FrontCommand;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import java.io.IOException;

public class SetLocaleEnCommand extends FrontCommand {
    private static final Logger LOG = Logger.getLogger(SetLocaleEnCommand.class);
    @Override
    public void process() throws ServletException, IOException {
        LOG.debug("Start executing Command");
//        request.setAttribute("sessionLocale", "en");
//        System.out.println("Path : "+request.getRequestURL());
//        RequestDispatcher requestDispatcher = request.getRequestDispatcher();
//        requestDispatcher.forward(request, response);
    }
}