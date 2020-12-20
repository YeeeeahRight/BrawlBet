package com.epam.web.command.impl;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.request.RequestContext;
import com.epam.web.service.LoginService;

public class LoginCommand implements Command {
    private static final String LOGIN_PARAM = "login";
    private static final String PASSWORD_PARAM = "password";
    private static final String ERROR_MESSAGE_ATTRIBUTE = "errorMessage";
    private static final String INCORRECT_DATA_MESSAGE = "Incorrect data! Please, sign up if you have not already.";
    private static final String BANNED_USER_MESSAGE = "This account was banned! Please, create a new one.";
    private static final String ADMIN_COMMAND = "controller?command=admin-page";
    private static final String LOGIN_PAGE = "WEB-INF/view/pages/login.jsp";

    private final LoginService service;

    public LoginCommand(LoginService service) {
        this.service = service;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {
        String login = requestContext.getRequestParameter(LOGIN_PARAM);
        String password = requestContext.getRequestParameter(PASSWORD_PARAM);
        boolean isUserExist = service.isUserExist(login, password);
        if (isUserExist) {
            boolean isUserBlocked = service.isBlocked(login);
            if (!isUserBlocked) {
                requestContext.addAttribute(LOGIN_PAGE, login);
                return CommandResult.redirect(ADMIN_COMMAND);
            }
            requestContext.addAttribute(ERROR_MESSAGE_ATTRIBUTE, BANNED_USER_MESSAGE);
        } else {
            requestContext.addAttribute(ERROR_MESSAGE_ATTRIBUTE, INCORRECT_DATA_MESSAGE);
        }

        return CommandResult.forward(LOGIN_PAGE);
    }
}
