package com.epam.web.command.impl;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.constant.Attribute;
import com.epam.web.constant.CommandName;
import com.epam.web.constant.Page;
import com.epam.web.constant.Parameter;
import com.epam.web.model.entity.Account;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.service.LoginService;

public class LoginCommand implements Command {
    private static final String HOME_PAGE_COMMAND = "controller?command=" + CommandName.HOME_PAGE;
    private static final String INCORRECT_DATA_KEY = "incorrect";
    private static final String BANNED_USER_KEY = "banned";
    private static final String ADMIN_ROLE = "ADMIN";

    private final LoginService service;

    public LoginCommand(LoginService service) {
        this.service = service;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {
        String login = requestContext.getRequestParameter(Parameter.LOGIN);
        String password = requestContext.getRequestParameter(Parameter.PASSWORD);
        boolean isUserExist = service.isUserExist(login, password);
        if (isUserExist) {
            Account account = service.getAccountByLogin(login);
            if (!account.isBlocked()) {
                long id = account.getId();
                requestContext.addSession(Attribute.ACCOUNT_ID, id);
                String role = account.getRole();
                requestContext.addSession(Attribute.ROLE, account.getRole());
                if (!role.equalsIgnoreCase(ADMIN_ROLE)) {
                    int balance = account.getBalance();
                    requestContext.addSession(Attribute.BALANCE, balance);
                }
                return CommandResult.redirect(HOME_PAGE_COMMAND);
            }
            requestContext.addAttribute(Attribute.ERROR_MESSAGE, BANNED_USER_KEY);
        } else {
            requestContext.addAttribute(Attribute.ERROR_MESSAGE, INCORRECT_DATA_KEY);
        }

        return CommandResult.forward(Page.LOGIN);
    }
}
