package com.epam.web.controller;

import com.epam.web.command.Command;
import com.epam.web.command.CommandFactory;
import com.epam.web.command.CommandResult;
import com.epam.web.connection.ConnectionPool;
import com.epam.web.constant.Attribute;
import com.epam.web.constant.Page;
import com.epam.web.constant.Parameter;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.controller.request.RequestContextCreator;
import com.epam.web.controller.request.RequestFiller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class Controller extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        RequestContextCreator requestContextCreator = new RequestContextCreator();
        CommandResult commandResult;
        String commandParam = req.getParameter(Parameter.COMMAND);
        Command command;
        try {
            command = CommandFactory.createCommand(commandParam);
            RequestContext requestContext = requestContextCreator.create(req);
            commandResult = command.execute(requestContext);
            RequestFiller requestFiller = new RequestFiller();
            requestFiller.fillData(req, requestContext);
            dispatch(commandResult, req, resp);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            handleException(req, resp, e.getMessage());
        }
    }

    private void dispatch(CommandResult commandResult, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String page = commandResult.getPage();
        if (page == null) {
            response.sendRedirect(Page.HOME);
        }
        if (commandResult.isRedirect()) {
            response.sendRedirect(page);
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher(page);
            dispatcher.forward(request, response);
        }
    }

    private void handleException(HttpServletRequest req, HttpServletResponse resp, String errorMessage)
            throws IOException {
        req.setAttribute(Attribute.ERROR_MESSAGE, errorMessage);
        RequestDispatcher dispatcher = req.getRequestDispatcher(Page.ERROR);
        try {
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            LOGGER.fatal(e.getMessage(), e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void destroy() {
        try {
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            connectionPool.closeAll();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        super.destroy();
    }
}
