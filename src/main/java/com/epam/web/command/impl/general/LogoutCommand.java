package com.epam.web.command.impl.general;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.constant.Attribute;
import com.epam.web.constant.CommandName;
import com.epam.web.controller.request.RequestContext;

public class LogoutCommand implements Command {
    private static final String HOME_PAGE_COMMAND = "controller?command=" + CommandName.HOME_PAGE;

    @Override
    public CommandResult execute(RequestContext requestContext) {
        requestContext.removeSessionAttribute(Attribute.ACCOUNT_ID);
        requestContext.removeSessionAttribute(Attribute.ROLE);

        return CommandResult.redirect(HOME_PAGE_COMMAND);
    }
}
