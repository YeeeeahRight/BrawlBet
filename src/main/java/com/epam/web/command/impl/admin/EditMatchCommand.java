package com.epam.web.command.impl.admin;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.command.util.MatchRequestCreator;
import com.epam.web.constant.CommandName;
import com.epam.web.constant.Parameter;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.logic.service.match.MatchService;
import com.epam.web.logic.service.team.TeamService;
import com.epam.web.model.entity.Match;
import com.epam.web.exception.ServiceException;
import com.epam.web.controller.request.RequestContext;

public class EditMatchCommand implements Command {
    private static final String MATCHES_COMMAND = "controller?command=" + CommandName.MATCHES +
            "&" + Parameter.PAGE + "=1";

    private final MatchService matchService;
    private final TeamService teamService;
    private final MatchRequestCreator matchRequestCreator;

    public EditMatchCommand(MatchService matchService, TeamService teamService,
                            MatchRequestCreator matchRequestCreator) {
        this.matchService = matchService;
        this.teamService = teamService;
        this.matchRequestCreator = matchRequestCreator;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException, InvalidParametersException {
        Match match = matchRequestCreator.createMatch(requestContext, teamService, true);
        matchService.saveMatch(match);

        return CommandResult.redirect(MATCHES_COMMAND);
    }
}


