package com.epam.web.command;

import com.epam.web.exceptions.ServiceException;
import com.epam.web.request.RequestContext;

public interface Command {
    CommandResult execute(RequestContext requestContext) throws ServiceException;
}
