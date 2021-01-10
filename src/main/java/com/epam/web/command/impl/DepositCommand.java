package com.epam.web.command.impl;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.constant.Attribute;
import com.epam.web.constant.Parameter;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.exceptions.InvalidInputException;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.UserService;

public class DepositCommand implements Command {
    private final UserService userService;

    public DepositCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {
        long accountId = (Long) requestContext.getSessionAttribute(Attribute.ACCOUNT_ID);
        String moneyStr = requestContext.getRequestParameter(Parameter.MONEY);
        if (moneyStr == null || moneyStr.isEmpty()) {
            throw new InvalidInputException("Deposit value is invalid.");
        }
        int money;
        try {
            money = Integer.parseInt(moneyStr);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Deposit value is not a number.");
        }
        userService.addMoney(money, accountId);

        String prevPage = requestContext.getHeader();
        return CommandResult.redirect(prevPage);
    }
}
