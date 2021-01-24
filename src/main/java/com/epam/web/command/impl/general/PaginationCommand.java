package com.epam.web.command.impl.general;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.constant.Parameter;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.exception.ServiceException;

public class PaginationCommand implements Command {
    private static final String PAGE_PARAM = "page=";

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException, InvalidParametersException {
        String pageStr = requestContext.getRequestParameter(Parameter.PAGE);
        int page;
        try {
            page = Integer.parseInt(pageStr);
        } catch (NumberFormatException e) {
            throw new InvalidParametersException("Invalid page number in request.");
        }
        if (page < 1) {
            throw new InvalidParametersException("Not positive page number in request.");
        }
        String initialPage = requestContext.getHeader();
        int indexOfPageParam = initialPage.indexOf(PAGE_PARAM);
        String newPage = initialPage.substring(0, indexOfPageParam) + PAGE_PARAM + page;
        return CommandResult.redirect(newPage);
    }
}
