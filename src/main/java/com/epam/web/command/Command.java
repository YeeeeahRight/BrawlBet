package com.epam.web.command;

import com.epam.web.exception.InvalidParametersException;
import com.epam.web.exception.ServiceException;
import com.epam.web.controller.request.RequestContext;

/**
 * Interface for processing requests from web browser.
 */
public interface Command {
    /**
     * Executes request using parameters of RequestContext object and returns
     * CommandResult object with necessary transition instructions
     *
     * @param  requestContext  an object which contains all request parameters
     *                         and attributes and session attributes.
     *
     * @return  a command result with transition instructions.
     *
     * @throws  ServiceException  if logical errors occurs and
     *                            also it's a wrapper for lower errors.
     * @throws  InvalidParametersException if there are errors in parameters of request.
     */
    CommandResult execute(RequestContext requestContext) throws ServiceException, InvalidParametersException;
}
