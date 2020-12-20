package com.epam.web.command;

import com.epam.web.command.impl.*;
import com.epam.web.dao.helper.DaoHelperFactory;
import com.epam.web.service.LoginService;
import com.epam.web.service.MatchService;
import com.epam.web.service.SignUpService;
import com.epam.web.service.UserService;

public class CommandFactory {
    private static final String START_PARAM = "start-page";
    private static final String ADMIN_PARAM = "admin-page";
    private static final String LOGIN_PAGE_PARAM = "login-page";
    private static final String SIGN_UP_PAGE_PARAM = "sign-up-page";
    private static final String ADD_MATCH_PAGE_PARAM = "add-match-page";
    private static final String EDIT_MATCH_PAGE_PARAM = "edit-match-page";
    private static final String USERS_PARAM = "users";
    private static final String MATCHES_PARAM = "matches";
    private static final String LOGIN_PARAM = "login";
    private static final String SIGN_UP_PARAM = "sign-up";
    private static final String EDIT_MATCH_PARAM = "edit-match";
    private static final String REMOVE_MATCH = "remove-match";
    private static final String ADD_MATCH = "add-match";
    private static final String BLOCK_USER = "block-user";
    private static final String UNBLOCK_USER = "unblock-user";

    public static Command createCommand(String commandParam) {
        switch (commandParam) {
            case LOGIN_PARAM:
                return new LoginCommand(new LoginService(new DaoHelperFactory()));
            case SIGN_UP_PARAM:
                return new SignUpCommand(new SignUpService(new DaoHelperFactory()));
            case ADMIN_PARAM:
                return new HomePageCommand(new MatchService(new DaoHelperFactory()), ADMIN_PARAM);
            case START_PARAM:
                return new HomePageCommand(new MatchService(new DaoHelperFactory()), START_PARAM);
            case MATCHES_PARAM:
                return new MatchesCommand(new MatchService(new DaoHelperFactory()));
            case USERS_PARAM:
                return new UsersCommand(new UserService(new DaoHelperFactory()));
            case REMOVE_MATCH:
                return new RemoveMatchCommand(new MatchService(new DaoHelperFactory()));
            case ADD_MATCH:
                return new AddMatchCommand(new MatchService(new DaoHelperFactory()));
            case EDIT_MATCH_PARAM:
                return new EditMatchCommand(new MatchService(new DaoHelperFactory()));
            case EDIT_MATCH_PAGE_PARAM:
                return new EditMatchPageCommand(new MatchService(new DaoHelperFactory()));
            case BLOCK_USER:
                return new BlockCommand(new UserService(new DaoHelperFactory()));
            case UNBLOCK_USER:
                return new UnblockCommand(new UserService(new DaoHelperFactory()));
            case ADD_MATCH_PAGE_PARAM:
            case LOGIN_PAGE_PARAM:
            case SIGN_UP_PAGE_PARAM:
                return new ForwardPageCommand(commandParam);
            default:
                throw new IllegalArgumentException();
        }
    }
}
