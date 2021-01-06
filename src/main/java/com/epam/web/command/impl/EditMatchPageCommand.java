package com.epam.web.command.impl;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.constant.Attribute;
import com.epam.web.constant.Page;
import com.epam.web.constant.Parameter;
import com.epam.web.model.entity.Match;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.service.MatchService;

public class EditMatchPageCommand implements Command {
    private final MatchService matchService;

    public EditMatchPageCommand(MatchService matchService) {
        this.matchService = matchService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {
        String idStr = requestContext.getRequestParameter(Parameter.ID);
        Long id = Long.valueOf(idStr);
        Match match = matchService.findById(id);
        requestContext.addAttribute(Attribute.MATCH, match);

        return CommandResult.forward(Page.EDIT_MATCH);
    }
}
