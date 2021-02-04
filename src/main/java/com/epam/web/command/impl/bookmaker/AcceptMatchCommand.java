package com.epam.web.command.impl.bookmaker;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.command.util.ParameterExtractor;
import com.epam.web.constant.Parameter;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.exception.ServiceException;
import com.epam.web.logic.service.match.MatchService;

public class AcceptMatchCommand implements Command {
    private static final float MIN_COMMISSION = 1.0f;
    private static final float MAX_COMMISSION = 8.0f;
    private final MatchService matchService;

    public AcceptMatchCommand(MatchService matchService) {
        this.matchService = matchService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext)
            throws ServiceException, InvalidParametersException {
        long id = ParameterExtractor.extractId(requestContext);
        float commission = ParameterExtractor.extractNumber(Parameter.COMMISSION, requestContext)
                .floatValue();
        if (commission < MIN_COMMISSION || commission > MAX_COMMISSION) {
            throw new InvalidParametersException(
                    String.format("Commission parameter is not in range[%f-%f]", MIN_COMMISSION, MAX_COMMISSION));
        }
        matchService.addCommissionById(commission, id);

        String prevPage = requestContext.getHeader();
        return CommandResult.redirect(prevPage);
    }
}
