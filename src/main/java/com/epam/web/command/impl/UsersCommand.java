package com.epam.web.command.impl;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.constant.Attribute;
import com.epam.web.constant.Page;
import com.epam.web.model.entity.Account;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.logic.service.UserService;
import com.epam.web.model.enumeration.AccountRole;

import java.util.ArrayList;
import java.util.List;

public class UsersCommand implements Command {
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
            AccountRole role = account.getRole();
            if (role == AccountRole.USER) {
                showedAccounts.add(account);
            } else if (role == AccountRole.BOOKMAKER) {
                showedAccounts.add(0, account);
            }
        }
        return showedAccounts;
    }
}
