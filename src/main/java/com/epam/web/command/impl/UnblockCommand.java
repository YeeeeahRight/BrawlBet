package com.epam.web.command.impl;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.constant.CommandName;
import com.epam.web.constant.Parameter;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.service.UserService;

public class UnblockCommand implements Command {
    private static final String USERS_COMMAND = "controller?command=" + CommandName.USERS;
    private final UserService userService;

    public UnblockCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {
        String idStr = requestContext.getRequestParameter(Parameter.ID);
        long id = Long.parseLong(idStr);
        userService.unblock(id);
        return CommandResult.redirect(USERS_COMMAND);
    }
}
