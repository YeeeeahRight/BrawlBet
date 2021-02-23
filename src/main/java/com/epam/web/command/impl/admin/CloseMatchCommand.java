package com.epam.web.command.impl.admin;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.command.util.ParameterExtractor;
import com.epam.web.constant.CommandName;
import com.epam.web.constant.Parameter;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.exception.ServiceException;
import com.epam.web.logic.service.match.CloseMatchService;

public class CloseMatchCommand implements Command {
    private static final String MATCHES_PAGE_COMMAND = "controller?command=" + CommandName.CLOSE_MATCHES_PAGE +
            "&" + Parameter.PAGE + "=1";
    private final CloseMatchService closeMatchService;

    public CloseMatchCommand(CloseMatchService closeMatchService) {
        this.closeMatchService = closeMatchService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException, InvalidParametersException {
        long id = ParameterExtractor.extractId(requestContext);
        closeMatchService.closeMatchById(id);

        return CommandResult.redirect(MATCHES_PAGE_COMMAND);
    }
}
