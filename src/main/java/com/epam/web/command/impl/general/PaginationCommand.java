package com.epam.web.command.impl.general;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.command.util.ParameterExtractor;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.exception.ServiceException;

public class PaginationCommand implements Command {
    private static final String PAGE_PARAM = "page=";

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException, InvalidParametersException {
        int page = ParameterExtractor.extractPageNumber(requestContext);
        String initialPage = requestContext.getHeader();
        int indexOfPageParam = initialPage.indexOf(PAGE_PARAM);
        String newPage = initialPage.substring(0, indexOfPageParam) + PAGE_PARAM + page;

        return CommandResult.redirect(newPage);
    }
}
