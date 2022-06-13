package com.project.time_management.controller.commands;

import com.project.time_management.controller.FrontCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UnknownCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
//        System.out.println("~~~");
//        System.out.println("UnknownCommand worked");

        HttpSession session = request.getSession(false);

//        System.out.println("view : " + session.getAttribute("view"));
//        System.out.println("~~~");

        forward(String.valueOf(session.getAttribute("view")));
    }
}