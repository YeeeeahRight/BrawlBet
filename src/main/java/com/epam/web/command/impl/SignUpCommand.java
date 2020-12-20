package com.epam.web.command.impl;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.request.RequestContext;
import com.epam.web.service.SignUpService;

public class SignUpCommand implements Command {
    private static final String LOGIN_PARAM = "login";
    private static final String PASSWORD_PARAM = "password";

    private static final String LOGIN_REGEX = "^(?=.*[A-Za-z])[A-Za-z\\d]+$";
    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{5,}$";
    private static final String REPEATED_PASSWORD_PARAM = "repeatedPassword";
    private static final String ERROR_MESSAGE_ATTRIBUTE = "errorMessage";
    private static final String SAVED_LOGIN_ATTRIBUTE = "login";
    private static final String DIFFERENT_PASSWORDS_MESSAGE = "Entered passwords dont match.";
    private static final String USERNAME_EXIST_MESSAGE = "User with this username already exist.";
    private static final String INVALID_LOGIN_MESSAGE = "Login is not valid! Login must contain at least one char.";
    private static final String INVALID_PASSWORD_MESSAGE = "Password is not valid! Password must contain " +
            "at least one char, one digit and more than 4 ENG symbols.";

    private static final String SIGN_UP_PAGE = "WEB-INF/view/pages/sign-up.jsp";
    private static final String LOGIN_COMMAND = "controller?command=login-page";

    private final SignUpService signUpService;

    public SignUpCommand(SignUpService signUpService) {
        this.signUpService = signUpService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {
        String login = requestContext.getRequestParameter(LOGIN_PARAM);
        boolean isCorrectLogin = login.matches(LOGIN_REGEX);
        if (isCorrectLogin) {
            boolean isUsernameExist = signUpService.isUsernameExist(login);
            if (isUsernameExist) {
                requestContext.addAttribute(SAVED_LOGIN_ATTRIBUTE, login);
                String password = requestContext.getRequestParameter(PASSWORD_PARAM);
                String repeatedPassword = requestContext.getRequestParameter(REPEATED_PASSWORD_PARAM);
                boolean isPasswordCorrect = password.matches(PASSWORD_REGEX);
                if (isPasswordCorrect) {
                    boolean isPasswordRepeated = password.equals(repeatedPassword);
                    if (isPasswordRepeated) {
                        signUpService.sign(login, repeatedPassword);
                        return CommandResult.redirect(LOGIN_COMMAND);
                    }
                    requestContext.addAttribute(ERROR_MESSAGE_ATTRIBUTE, DIFFERENT_PASSWORDS_MESSAGE);
                } else {
                    requestContext.addAttribute(ERROR_MESSAGE_ATTRIBUTE, INVALID_PASSWORD_MESSAGE);
                }
            } else {
                requestContext.addAttribute(ERROR_MESSAGE_ATTRIBUTE, USERNAME_EXIST_MESSAGE);
            }
        } else {
            requestContext.addAttribute(ERROR_MESSAGE_ATTRIBUTE, INVALID_LOGIN_MESSAGE);
        }

        return CommandResult.forward(SIGN_UP_PAGE);
    }
}
