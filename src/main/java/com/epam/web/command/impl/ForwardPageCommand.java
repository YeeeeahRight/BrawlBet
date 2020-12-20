package com.epam.web.command.impl;

import com.epam.web.command.Command;
import com.epam.web.command.CommandFactory;
import com.epam.web.command.CommandResult;
import com.epam.web.request.RequestContext;

public class ForwardPageCommand implements Command {
    private static final String LOGIN_PAGE_PARAM = "login-page";
    private static final String SIGN_UP_PAGE_PARAM = "sign-up-page";
    private static final String ADD_MATCH_PAGE_PARAM = "add-match-page";
    private static final String LOGIN_PAGE = "WEB-INF/view/pages/login.jsp";
    private static final String SIGN_UP_PAGE = "WEB-INF/view/pages/sign-up.jsp";
    private static final String ADD_MATCH_PAGE = "WEB-INF/view/pages/add-match.jsp";
    private final String page;

    public ForwardPageCommand(String page) {
        this.page = page;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) {
        switch (page) {
            case LOGIN_PAGE_PARAM:
                return CommandResult.forward(LOGIN_PAGE);
            case SIGN_UP_PAGE_PARAM:
                return CommandResult.forward(SIGN_UP_PAGE);
            case ADD_MATCH_PAGE_PARAM:
                return CommandResult.forward(ADD_MATCH_PAGE);
            default:
                throw new IllegalArgumentException("Unknown page: " + page);
        }
    }
}
