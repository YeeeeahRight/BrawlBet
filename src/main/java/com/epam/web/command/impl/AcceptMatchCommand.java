package com.epam.web.command.impl;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.constant.CommandName;
import com.epam.web.constant.Parameter;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.MatchService;

public class AcceptMatchCommand implements Command {
    private static final String ACCEPT_MATCHES_COMMAND = "controller?command="
            + CommandName.ACCEPT_MATCHES_PAGE;
    private final MatchService matchService;

    public AcceptMatchCommand(MatchService matchService) {
        this.matchService = matchService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {
        String idStr = requestContext.getRequestParameter(Parameter.ID);
        long id = Long.parseLong(idStr);
        String commissionStr = requestContext.getRequestParameter(Parameter.COMMISSION);
        float commission = Float.parseFloat(commissionStr);
        matchService.addCommission(commission, id);

        return CommandResult.redirect(ACCEPT_MATCHES_COMMAND);
    }
}
