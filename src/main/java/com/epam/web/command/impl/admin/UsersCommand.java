package com.epam.web.command.impl.admin;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.constant.Attribute;
import com.epam.web.constant.Page;
import com.epam.web.logic.service.account.AccountService;
import com.epam.web.model.entity.Account;
import com.epam.web.exception.ServiceException;
import com.epam.web.controller.request.RequestContext;

import java.util.List;
import java.util.Optional;

public class UsersCommand implements Command {
    private final AccountService accountService;

    public UsersCommand(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {
        List<Account> users = accountService.getUsers();
        Optional<Account> bookmakerOptional = accountService.findBookmaker();
        if (bookmakerOptional.isPresent()) {
            users.add(0, bookmakerOptional.get());
        }
        requestContext.addAttribute(Attribute.USERS, users);

        return CommandResult.forward(Page.USERS);
    }
}
