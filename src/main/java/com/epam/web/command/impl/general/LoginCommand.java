package com.epam.web.command.impl.general;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.command.util.ParameterExtractor;
import com.epam.web.constant.Attribute;
import com.epam.web.constant.CommandName;
import com.epam.web.constant.Page;
import com.epam.web.constant.Parameter;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.logic.service.account.AccountService;
import com.epam.web.model.entity.Account;
import com.epam.web.exception.ServiceException;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.model.enumeration.AccountRole;

public class LoginCommand implements Command {
    private static final String HOME_PAGE_COMMAND = "controller?command=" + CommandName.HOME_PAGE +
            "&" + Parameter.PAGE + "=1";
    private static final String INCORRECT_DATA_KEY = "incorrect";
    private static final String BANNED_USER_KEY = "banned";

    private final AccountService service;

    public LoginCommand(AccountService service) {
        this.service = service;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException, InvalidParametersException {
        String login = ParameterExtractor.extractString(Parameter.LOGIN, requestContext);
        String password = ParameterExtractor.extractString(Parameter.PASSWORD, requestContext);
        boolean isUserExist = service.isAccountExistByLoginPassword(login, password);
        if (isUserExist) {
            Account account = service.getAccountByLogin(login);
            if (!account.isBlocked()) {
                long id = account.getId();
                requestContext.addSession(Attribute.ACCOUNT_ID, id);
                AccountRole role = account.getRole();
                requestContext.addSession(Attribute.ROLE, role);
                return CommandResult.redirect(HOME_PAGE_COMMAND);
            }
            requestContext.addAttribute(Attribute.ERROR_MESSAGE, BANNED_USER_KEY);
        } else {
            requestContext.addAttribute(Attribute.ERROR_MESSAGE, INCORRECT_DATA_KEY);
        }

        return CommandResult.forward(Page.LOGIN);
    }
}
