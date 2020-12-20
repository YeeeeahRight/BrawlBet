package com.epam.web.controller;

import com.epam.web.command.Command;
import com.epam.web.command.CommandFactory;
import com.epam.web.command.CommandResult;
import com.epam.web.request.RequestContext;
import com.epam.web.request.RequestContextCreator;
import com.epam.web.request.RequestFiller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Servlet extends HttpServlet {
    private static final String ERROR_ATTRIBUTE = "errorMessage";
    private static final String COMMAND_NAME = "command";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) {
        RequestContextCreator requestContextCreator = new RequestContextCreator();
        CommandResult commandResult;
        String commandParam = req.getParameter(COMMAND_NAME);
        Command command;
        try {
            command = CommandFactory.createCommand(commandParam);
            RequestContext requestContext = requestContextCreator.create(req);
            commandResult = command.execute(requestContext);
            RequestFiller requestFiller = new RequestFiller();
            requestFiller.fillData(req, requestContext);
            dispatch(commandResult, req, resp);
        } catch (Exception e) {
            processError(req, resp, e.getMessage());
        }
    }

    private void dispatch(CommandResult commandResult, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String page = commandResult.getPage();
        if (commandResult.getIsRedirect()) {
            response.sendRedirect(page);
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher(page);
            dispatcher.forward(request, response);
        }
    }

    private void processError(HttpServletRequest req, HttpServletResponse resp, String errorMessage) {
        CommandResult commandResult = CommandResult.error();
        req.setAttribute(ERROR_ATTRIBUTE, errorMessage);
        try {
            dispatch(commandResult, req, resp);
        } catch (Exception e) {
            //???
            e.printStackTrace();
        }
    }
}
