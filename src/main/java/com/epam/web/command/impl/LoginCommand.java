package com.epam.web.command.impl;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.constant.Attribute;
import com.epam.web.constant.CommandName;
import com.epam.web.constant.Page;
import com.epam.web.constant.Parameter;
import com.epam.web.exceptions.InvalidParametersException;
import com.epam.web.model.entity.Account;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.logic.service.LoginService;
import com.epam.web.model.enumeration.AccountRole;

public class LoginCommand implements Command {
    private static final String HOME_PAGE_COMMAND = "controller?command=" + CommandName.HOME_PAGE;
    private static final String INCORRECT_DATA_KEY = "incorrect";
    private static final String BANNED_USER_KEY = "banned";

    private final LoginService service;

    public LoginCommand(LoginService service) {
        this.service = service;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException, InvalidParametersException {
        String login = requestContext.getRequestParameter(Parameter.LOGIN);
        if (login == null) {
            throw new InvalidParametersException("No login parameter in request.");
        }
        String password = requestContext.getRequestParameter(Parameter.PASSWORD);
        if (password == null) {
            throw new InvalidParametersException("No password parameter in request.");
        }
        boolean isUserExist = service.isUserExistByLoginPassword(login, password);
        if (isUserExist) {
            Account account = service.getAccountByLogin(login);
            if (!account.isBlocked()) {
                long id = account.getId();
                requestContext.addSession(Attribute.ACCOUNT_ID, id);
                AccountRole role = account.getRole();
                requestContext.addSession(Attribute.ROLE, role);
                if (role != AccountRole.ADMIN) {
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
