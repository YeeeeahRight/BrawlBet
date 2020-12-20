package com.epam.web.command.impl;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.entity.Match;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.request.RequestContext;
import com.epam.web.service.MatchService;

public class EditMatchPageCommand implements Command {
    private static final String ID_ATTRIBUTE = "id";
    private static final String MATCH_ATTRIBUTE = "match";
    private static final String EDIT_MATCH_PAGE = "WEB-INF/view/pages/edit-match.jsp";
    private final MatchService matchService;

    public EditMatchPageCommand(MatchService matchService) {
        this.matchService = matchService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {
        String idStr = requestContext.getRequestParameter(ID_ATTRIBUTE);
        Long id = Long.valueOf(idStr);
        Match match = matchService.findById(id);
        requestContext.addAttribute(MATCH_ATTRIBUTE, match);

        return CommandResult.forward(EDIT_MATCH_PAGE);
    }
}
