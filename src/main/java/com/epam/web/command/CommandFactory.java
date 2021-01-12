package com.epam.web.command;

import com.epam.web.command.impl.*;
import com.epam.web.constant.CommandName;
import com.epam.web.dao.helper.DaoHelperFactory;
import com.epam.web.logic.service.*;
import com.epam.web.logic.validator.impl.AccountValidator;
import com.epam.web.logic.validator.impl.BetValidator;
import com.epam.web.logic.validator.impl.MatchValidator;

public class CommandFactory {

    public static Command createCommand(String commandParam) {
        if (commandParam == null) {
            throw new IllegalArgumentException("There is no command to do.");
        }
        switch (commandParam) {
            case CommandName.LOGIN:
                return new LoginCommand(new LoginService(new DaoHelperFactory()));
            case CommandName.LOGOUT:
                return new LogoutCommand();
            case CommandName.SIGN_UP:
                return new SignUpCommand(new SignUpService(new DaoHelperFactory(),
                        new AccountValidator()));
            case CommandName.LOCALIZATION:
                return new LocalizationCommand();
            case CommandName.HOME_PAGE:
                return new HomePageCommand(new MatchService(new DaoHelperFactory(),
                        new MatchValidator()), new BetService(new DaoHelperFactory(),
                        new BetValidator()));
            case CommandName.CLOSE_MATCHES_PAGE:
                return new CloseMatchesPageCommand(new MatchService(new DaoHelperFactory(),
                        new MatchValidator()));
            case CommandName.CLOSE_MATCH:
                return new CloseMatchCommand(new CloseMatchService(new DaoHelperFactory()));
            case CommandName.BET_PAGE:
                return new BetPageCommand(new MatchService(new DaoHelperFactory(),
                        new MatchValidator()), new BetService(new DaoHelperFactory(),
                        new BetValidator()), new UserService(new DaoHelperFactory()));
            case CommandName.BET:
                return new BetCommand(new MatchService(new DaoHelperFactory(),
                        new MatchValidator()), new BetService(new DaoHelperFactory(),
                        new BetValidator()), new UserService(new DaoHelperFactory()));
            case CommandName.MATCHES:
                return new MatchesCommand(new MatchService(new DaoHelperFactory(),
                        new MatchValidator()));
            case CommandName.USERS:
                return new UsersCommand(new UserService(new DaoHelperFactory()));
            case CommandName.DEPOSIT:
                return new DepositCommand(new UserService(new DaoHelperFactory()));
            case CommandName.REMOVE_MATCH:
                return new RemoveMatchCommand(new MatchService(new DaoHelperFactory(),
                        new MatchValidator()));
            case CommandName.CANCEL_MATCH:
                return new CancelMatchCommand(new MatchService(new DaoHelperFactory(),
                        new MatchValidator()));
            case CommandName.ADD_MATCH:
                return new AddMatchCommand(new MatchService(new DaoHelperFactory(),
                        new MatchValidator()));
            case CommandName.EDIT_MATCH:
                return new EditMatchCommand(new MatchService(new DaoHelperFactory(),
                        new MatchValidator()));
            case CommandName.EDIT_MATCH_PAGE:
                return new EditMatchPageCommand(new MatchService(new DaoHelperFactory(),
                        new MatchValidator()));
            case CommandName.BLOCK_USER:
                return new BlockCommand(new UserService(new DaoHelperFactory()));
            case CommandName.UNBLOCK_USER:
                return new UnblockCommand(new UserService(new DaoHelperFactory()));
            case CommandName.ACCEPT_MATCHES_PAGE:
                return new AcceptMatchesPageCommand(new MatchService(new DaoHelperFactory(),
                        new MatchValidator()));
            case CommandName.ACCEPT_MATCH:
                return new AcceptMatchCommand(new MatchService(new DaoHelperFactory(),
                        new MatchValidator()));
            case CommandName.ADD_MATCH_PAGE:
            case CommandName.LOGIN_PAGE:
            case CommandName.DEPOSIT_PAGE:
            case CommandName.SIGN_UP_PAGE:
                return new ForwardPageCommand(commandParam);
            default:
                throw new IllegalArgumentException("Unknown command: " + commandParam);
        }
    }
}
