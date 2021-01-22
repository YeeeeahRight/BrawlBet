package com.epam.web.command.impl.user;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.constant.Attribute;
import com.epam.web.constant.Parameter;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.exception.ServiceException;
import com.epam.web.logic.service.account.AccountService;

public class DepositCommand implements Command {
    private static final float MAX_DEPOSIT = 100.0f;
    private static final float MIN_DEPOSIT = 0.1f;
    private final AccountService accountService;

    public DepositCommand(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException, InvalidParametersException {
        String moneyStr = requestContext.getRequestParameter(Parameter.MONEY);
        float money;
        try {
            money = Float.parseFloat(moneyStr);
        } catch (NumberFormatException e) {
            throw new InvalidParametersException("Invalid money parameter in request.");
        }
        if (money > MAX_DEPOSIT || money < MIN_DEPOSIT) {
            throw new InvalidParametersException(
                    String.format("Deposit value is not in range[%f-%f]", MIN_DEPOSIT, MAX_DEPOSIT));
        }
        long accountId = (Long) requestContext.getSessionAttribute(Attribute.ACCOUNT_ID);
        accountService.addMoneyById(money, accountId);

        String prevPage = requestContext.getHeader();
        return CommandResult.redirect(prevPage);
    }
}
