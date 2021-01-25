package com.epam.web.command.impl.general;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.constant.CommandName;
import com.epam.web.constant.Page;
import com.epam.web.controller.request.RequestContext;

public class ForwardPageCommand implements Command {
    private final String page;

    public ForwardPageCommand(String page) {
        this.page = page;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) {
        if (page == null) {
            throw new IllegalArgumentException("Invalid page.");
        }
        switch (page) {
            case CommandName.LOGIN_PAGE:
                return CommandResult.forward(Page.LOGIN);
            case CommandName.SIGN_UP_PAGE:
                return CommandResult.forward(Page.SIGN_UP);
            case CommandName.ADD_MATCH_PAGE:
                return CommandResult.forward(Page.ADD_MATCH);
            case CommandName.DEPOSIT_PAGE:
                return CommandResult.forward(Page.DEPOSIT);
            default:
                throw new IllegalArgumentException("Unknown page: " + page);
        }
    }
}
