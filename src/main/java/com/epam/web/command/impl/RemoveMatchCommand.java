package com.epam.web.command.impl;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.request.RequestContext;
import com.epam.web.service.MatchService;

public class RemoveMatchCommand implements Command {
    private static final String MATCHES_COMMAND = "controller?command=matches";
    private static final String ID_PARAMETER = "id";
    private final MatchService matchService;

    public RemoveMatchCommand(MatchService matchService) {
        this.matchService = matchService;
    }


    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {
        String idStr = requestContext.getRequestParameter(ID_PARAMETER);
        Long id = Long.valueOf(idStr);
        matchService.removeById(id);
        return CommandResult.redirect(MATCHES_COMMAND);
    }
}
