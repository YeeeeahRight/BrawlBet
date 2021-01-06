package com.epam.web.command.impl;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.constant.Attribute;
import com.epam.web.constant.Page;
import com.epam.web.model.entity.Account;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class UsersCommand implements Command {
    private static final String USER_ROLE = "USER";
    private static final String BOOKMAKER_ROLE = "BOOKMAKER";

    private final UserService userService;

    public UsersCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {
        List<Account> allAccounts = userService.getAll();
        List<Account> accountList = getUserList(allAccounts);
        requestContext.addAttribute(Attribute.USERS, accountList);
        return CommandResult.forward(Page.USERS);
    }

    private List<Account> getUserList(List<Account> accounts) {
        List<Account> showedAccounts = new ArrayList<>();
        for (Account account : accounts) {
            String role = account.getRole();
            if (role.equals(USER_ROLE)) {
                showedAccounts.add(account);
            } else if (role.equals(BOOKMAKER_ROLE)) {
                showedAccounts.add(0, account);
            }
        }
        return showedAccounts;
    }
}
