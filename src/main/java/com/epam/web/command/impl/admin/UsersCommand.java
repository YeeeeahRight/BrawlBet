package com.epam.web.command.impl.admin;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.command.util.ParameterExtractor;
import com.epam.web.constant.Attribute;
import com.epam.web.constant.Page;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.logic.service.account.AccountService;
import com.epam.web.model.entity.Account;
import com.epam.web.exception.ServiceException;
import com.epam.web.controller.request.RequestContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersCommand implements Command {
    private static final int MAX_USERS_PAGE = 6;
    private final AccountService accountService;

    public UsersCommand(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException,
            InvalidParametersException {
        int page = ParameterExtractor.extractPageNumber(requestContext);
        List<Account> users;
        Optional<Account> bookmakerOptional = accountService.findBookmaker();
        boolean isBookmakerExist = bookmakerOptional.isPresent();
        if (isBookmakerExist && page == 1) {
            users = new ArrayList<>();
            users.add(bookmakerOptional.get());
            users.addAll(accountService.getUsersRange(0, MAX_USERS_PAGE - 1));
        } else {
            int firstMatchIndex = MAX_USERS_PAGE * (page - 1) - (isBookmakerExist ? 1 : 0);
            users = accountService.getUsersRange(firstMatchIndex, MAX_USERS_PAGE);
        }
        if (users.size() == 0 && page > 1) {
            throw new InvalidParametersException("No users on this page");
        }
        requestContext.addAttribute(Attribute.USERS, users);
        requestContext.addAttribute(Attribute.CURRENT_PAGE, page);
        int usersAmount = accountService.getUsersAmount() + (isBookmakerExist ? 1 : 0);
        int maxPage = ((usersAmount - 1) / MAX_USERS_PAGE) + 1;
        requestContext.addAttribute(Attribute.MAX_PAGE, maxPage);

        return CommandResult.forward(Page.USERS);
    }
}
