package com.epam.web.command.impl.general;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.command.util.ParameterExtractor;
import com.epam.web.constant.Attribute;
import com.epam.web.constant.CommandName;
import com.epam.web.constant.Page;
import com.epam.web.constant.Parameter;
import com.epam.web.exception.InvalidParametersException;
import com.epam.web.exception.ServiceException;
import com.epam.web.controller.request.RequestContext;
import com.epam.web.logic.service.account.SignUpService;

public class SignUpCommand implements Command {
    private static final String LOGIN_PAGE_COMMAND = "controller?command=" + CommandName.LOGIN_PAGE;
    private static final String LOGIN_REGEX = "^(?=.*[A-Za-z])[A-Za-z\\d]+";
    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{5,}$";
    private static final String DIFFERENT_PASSWORDS_KEY = "different.passwords";
    private static final String USERNAME_EXIST_KEY = "username.exist";
    private static final String INVALID_LOGIN_KEY = "invalid.login";
    private static final String INVALID_PASSWORD_KEY = "invalid.password";

    private final SignUpService signUpService;

    public SignUpCommand(SignUpService signUpService) {
        this.signUpService = signUpService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException, InvalidParametersException {
        String login = ParameterExtractor.extractString(Parameter.LOGIN, requestContext);
        boolean isCorrectLogin = login.matches(LOGIN_REGEX);
        if (isCorrectLogin) {
            boolean isUsernameExist = signUpService.isUsernameExist(login);
            if (!isUsernameExist) {
                requestContext.addAttribute(Attribute.SAVED_LOGIN, login);
                String password = ParameterExtractor.extractString(Parameter.PASSWORD, requestContext);
                String repeatedPassword = requestContext.getRequestParameter(Parameter.REPEATED_PASSWORD);
                boolean isPasswordCorrect = password.matches(PASSWORD_REGEX);
                if (isPasswordCorrect) {
                    boolean isPasswordRepeated = password.equals(repeatedPassword);
                    if (isPasswordRepeated) {
                        signUpService.signUp(login, repeatedPassword);
                        return CommandResult.redirect(LOGIN_PAGE_COMMAND);
                    }
                    requestContext.addAttribute(Attribute.ERROR_MESSAGE, DIFFERENT_PASSWORDS_KEY);
                } else {
                    requestContext.addAttribute(Attribute.ERROR_MESSAGE, INVALID_PASSWORD_KEY);
                }
            } else {
                requestContext.addAttribute(Attribute.ERROR_MESSAGE, USERNAME_EXIST_KEY);
            }
        } else {
            requestContext.addAttribute(Attribute.ERROR_MESSAGE, INVALID_LOGIN_KEY);
        }

        return CommandResult.forward(Page.SIGN_UP);
    }
}
