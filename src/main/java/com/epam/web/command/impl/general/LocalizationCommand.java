package com.epam.web.command.impl.general;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.constant.Attribute;
import com.epam.web.constant.CommandName;
import com.epam.web.constant.Page;
import com.epam.web.constant.Parameter;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.exception.InvalidParametersException;

public class LocalizationCommand implements Command {
    private static final String RU = "ru";
    private static final String BE = "be";
    private static final String EN_LOCALE = "en_US";
    private static final String RU_LOCALE = "ru_RU";
    private static final String BE_LOCALE = "be_BY";

    private static final String PARAMETER_SPLITERATOR = "&";

    private static final String LOGIN_PAGE = "controller?command=" + CommandName.LOGIN_PAGE;
    private static final String SIGN_UP_PAGE = "controller?command=" + CommandName.SIGN_UP_PAGE;

    @Override
    public CommandResult execute(RequestContext requestContext) throws InvalidParametersException {
        String language = requestContext.getRequestParameter(Parameter.LANGUAGE);
        if (language == null) {
            throw new InvalidParametersException("No language parameter in request.");
        }
        String locale = getLocaleByLanguage(language);
        requestContext.addSession(Attribute.LANGUAGE, locale);
        String page = requestContext.getHeader();
        if (page != null) {
            String prevCommand = extractCommand(page);
            if (CommandName.LOGIN.equals(prevCommand) || CommandName.SIGN_UP.equals(prevCommand)) {
                page = changeCommandToCommandPage(prevCommand);
            }
        }
        return CommandResult.redirect(page);
    }

    private String getLocaleByLanguage(String language) {
        switch (language) {
            case RU:
                return RU_LOCALE;
            case BE:
                return BE_LOCALE;
        }
        return EN_LOCALE;
    }

    private String changeCommandToCommandPage(String prevCommand) {
        switch (prevCommand) {
            case CommandName.LOGIN:
                return LOGIN_PAGE;
            case CommandName.SIGN_UP:
                return SIGN_UP_PAGE;
            default:
                return Page.HOME;
        }
    }

    private String extractCommand(String url) {
        int commandIndex = url.indexOf(Parameter.COMMAND) + Parameter.COMMAND.length() + 1;
        int lastCommandIndex = url.indexOf(PARAMETER_SPLITERATOR);
        if (lastCommandIndex == -1) {
            return url.substring(commandIndex);
        } else {
            return url.substring(commandIndex, lastCommandIndex);
        }
    }
}
