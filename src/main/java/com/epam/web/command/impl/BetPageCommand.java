package com.epam.web.command.impl;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.controller.request.RequestContext;

public class BetPageCommand implements Command {
    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {
        return null;
    }
}
