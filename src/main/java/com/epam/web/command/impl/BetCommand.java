package com.epam.web.command.impl;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.constant.CommandName;
import com.epam.web.constant.Parameter;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.exceptions.ServiceException;

public class BetCommand implements Command {
    private static final String BET_COMMAND = "controller?command="
            + CommandName.BET_PAGE + "&" + Parameter.ID + "=";

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {
        String idStr = requestContext.getRequestParameter(Parameter.ID);
        long id = Long.parseLong(idStr);

        return CommandResult.redirect(BET_COMMAND + id);
    }
}
