package com.epam.web.command.impl;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.constant.Parameter;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.exceptions.InvalidParametersException;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.logic.service.MatchService;

public class AcceptMatchCommand implements Command {
    private final MatchService matchService;

    public AcceptMatchCommand(MatchService matchService) {
        this.matchService = matchService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException, InvalidParametersException {
        String idStr = requestContext.getRequestParameter(Parameter.ID);
        String commissionStr = requestContext.getRequestParameter(Parameter.COMMISSION);
        long id;
        float commission;
        try {
            id = Long.parseLong(idStr);
            commission = Float.parseFloat(commissionStr);
        } catch (NumberFormatException e) {
            throw new InvalidParametersException("Invalid parameters in request.");
        }
        matchService.addCommissionById(commission, id);

        String prevPage = requestContext.getHeader();
        return CommandResult.redirect(prevPage);
    }
}
