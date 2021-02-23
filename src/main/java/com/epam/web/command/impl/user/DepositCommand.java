package com.epam.web.command.impl.user;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.command.util.ParameterExtractor;
import com.epam.web.constant.Attribute;
import com.epam.web.constant.Parameter;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.exception.ServiceException;
import com.epam.web.logic.service.account.AccountService;

import java.math.BigDecimal;

public class DepositCommand implements Command {
    private static final BigDecimal MAX_DEPOSIT = new BigDecimal("100.0");
    private static final BigDecimal MIN_DEPOSIT = new BigDecimal("0.1");
    private final AccountService accountService;

    public DepositCommand(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException, InvalidParametersException {
        BigDecimal money = ParameterExtractor.extractNumber(Parameter.MONEY, requestContext);
        if (money.compareTo(MAX_DEPOSIT) > 0 || money.compareTo(MIN_DEPOSIT) < 0) {
            throw new InvalidParametersException(
                    String.format("Deposit value is not in range[%f-%f]", MIN_DEPOSIT, MAX_DEPOSIT));
        }
        long accountId = (Long) requestContext.getSessionAttribute(Attribute.ACCOUNT_ID);
        accountService.addMoneyById(money, accountId);

        String prevPage = requestContext.getHeader();
        return CommandResult.redirect(prevPage);
    }
}
