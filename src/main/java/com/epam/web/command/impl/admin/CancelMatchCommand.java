package com.epam.web.command.impl.admin;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.command.util.ParameterExtractor;
import com.epam.web.constant.CommandName;
import com.epam.web.constant.Parameter;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.exception.ServiceException;
import com.epam.web.logic.service.match.MatchService;

public class CancelMatchCommand implements Command {
    private static final String MATCHES_PAGE_COMMAND = "controller?command=" + CommandName.MATCHES +
            "&" + Parameter.PAGE + "=1";
    private final MatchService matchService;

    public CancelMatchCommand(MatchService matchService) {
        this.matchService = matchService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException, InvalidParametersException {
        long id = ParameterExtractor.extractId(requestContext);
        matchService.cancelMatchById(id);

        return CommandResult.redirect(MATCHES_PAGE_COMMAND);
    }
}
