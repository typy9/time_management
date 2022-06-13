package com.project.time_management.servlets;

import com.project.time_management.controller.FrontCommand;
import com.project.time_management.controller.commands.UnknownCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.apache.log4j.Logger;

public class FrontControllerServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(FrontControllerServlet.class);
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        FrontCommand command = getCommand(request);
        command.init(getServletContext(), request, response);
        command.process();

    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        FrontCommand command = getCommand(request);
        command.init(getServletContext(), request, response);
        command.process();
    }


    /**
     * The command name is extracted from a request, then a new instance of a command class
     * is created dynamically. Finally, it is executed.
     * This allows to add new commands without changing a code base of our Front Controller.
     * @param request is used to extract command name in order to create an implementation
     *                of the abstract FrontCommand class.
     * @return an implementation of the abstract FrontCommand class defined by @param request.
     */
    private FrontCommand getCommand(HttpServletRequest request) {
        LOG.debug("Start getCommand in FrontController");
        try {
            Class type = Class.forName(String.format(
                    "com.project.time_management.controller.commands.%sCommand",
                    request.getParameter("command")));
            LOG.trace("Request parameter: 'command' = " + request.getParameter("command"));
            return (FrontCommand) type.asSubclass(FrontCommand.class).newInstance();

        } catch (Exception e) {
            LOG.debug("Unknown Command in FrontController");
            return new UnknownCommand();
        }
    }
}
