package com.epam.web.command;

import com.epam.web.command.impl.admin.*;
import com.epam.web.command.impl.bookmaker.AcceptMatchCommand;
import com.epam.web.command.impl.bookmaker.AcceptMatchesPageCommand;
import com.epam.web.command.impl.bookmaker.BookmakerHistory;
import com.epam.web.command.impl.bookmaker.RemoveMatchCommand;
import com.epam.web.command.impl.general.*;
import com.epam.web.command.impl.user.BetCommand;
import com.epam.web.command.impl.user.DepositCommand;
import com.epam.web.command.impl.user.MyBetsCommand;
import com.epam.web.constant.CommandName;
import com.epam.web.dao.helper.DaoHelperFactory;
import com.epam.web.logic.calculator.BetCalculatorImpl;
import com.epam.web.logic.service.account.SignUpServiceImpl;
import com.epam.web.logic.service.account.AccountServiceImpl;
import com.epam.web.logic.service.bet.BetServiceImpl;
import com.epam.web.logic.service.match.CloseMatchServiceImpl;
import com.epam.web.logic.service.match.MatchServiceImpl;
import com.epam.web.logic.service.team.TeamServiceImpl;
import com.epam.web.logic.validator.impl.AccountValidator;
import com.epam.web.logic.validator.impl.BetValidator;
import com.epam.web.logic.validator.impl.MatchValidator;
import com.epam.web.logic.validator.impl.TeamValidator;

public class CommandFactory {

    public static Command createCommand(String commandParam) {
        if (commandParam == null) {
            throw new IllegalArgumentException("There is no command to do.");
        }
        switch (commandParam) {
            case CommandName.LOGIN:
                return new LoginCommand(new AccountServiceImpl(new DaoHelperFactory()));
            case CommandName.LOGOUT:
                return new LogoutCommand();
            case CommandName.SIGN_UP:
                return new SignUpCommand(new SignUpServiceImpl(new DaoHelperFactory(),
                        new AccountValidator()));
            case CommandName.LOCALIZATION:
                return new LocalizationCommand();
            case CommandName.HOME_PAGE:
                return new HomePageCommand(new MatchServiceImpl(new DaoHelperFactory(),
                        new MatchValidator()), new TeamServiceImpl(new DaoHelperFactory(),
                        new TeamValidator()), new BetCalculatorImpl());
            case CommandName.CLOSE_MATCHES_PAGE:
                return new CloseMatchesPageCommand(new MatchServiceImpl(new DaoHelperFactory(),
                        new MatchValidator()), new TeamServiceImpl(new DaoHelperFactory(),
                        new TeamValidator()));
            case CommandName.CLOSE_MATCH:
                return new CloseMatchCommand(new CloseMatchServiceImpl(new DaoHelperFactory(),
                        new BetCalculatorImpl()));
            case CommandName.MATCH_PAGE:
                return new MatchPageCommand(new MatchServiceImpl(new DaoHelperFactory(), new MatchValidator()),
                        new AccountServiceImpl(new DaoHelperFactory()), new TeamServiceImpl(new DaoHelperFactory(),
                        new TeamValidator()), new BetCalculatorImpl());
            case CommandName.BET:
                return new BetCommand(new BetServiceImpl(new DaoHelperFactory(),
                        new BetValidator()), new AccountServiceImpl(new DaoHelperFactory()),
                        new MatchServiceImpl(new DaoHelperFactory(), new MatchValidator()));
            case CommandName.MATCHES:
                return new MatchesCommand(new MatchServiceImpl(new DaoHelperFactory(),
                        new MatchValidator()), new TeamServiceImpl(new DaoHelperFactory(),
                        new TeamValidator()));
            case CommandName.USERS:
                return new UsersCommand(new AccountServiceImpl(new DaoHelperFactory()));
            case CommandName.DEPOSIT:
                return new DepositCommand(new AccountServiceImpl(new DaoHelperFactory()));
            case CommandName.MY_BETS:
                return new MyBetsCommand(new BetServiceImpl(new DaoHelperFactory(), new BetValidator()),
                        new MatchServiceImpl(new DaoHelperFactory(), new MatchValidator()),
                        new TeamServiceImpl(new DaoHelperFactory(), new TeamValidator()), new BetCalculatorImpl());
            case CommandName.BOOKMAKER_HISTORY:
                return new BookmakerHistory(new MatchServiceImpl(new DaoHelperFactory(),
                        new MatchValidator()), new TeamServiceImpl(new DaoHelperFactory(),
                        new TeamValidator()));
            case CommandName.REMOVE_MATCH:
                return new RemoveMatchCommand(new MatchServiceImpl(new DaoHelperFactory(),
                        new MatchValidator()));
            case CommandName.CANCEL_MATCH:
                return new CancelMatchCommand(new MatchServiceImpl(new DaoHelperFactory(),
                        new MatchValidator()));
            case CommandName.ADD_MATCH:
                return new AddMatchCommand(new MatchServiceImpl(new DaoHelperFactory(),
                        new MatchValidator()), new TeamServiceImpl(new DaoHelperFactory(),
                        new TeamValidator()));
            case CommandName.EDIT_MATCH:
                return new EditMatchCommand(new MatchServiceImpl(new DaoHelperFactory(),
                        new MatchValidator()), new TeamServiceImpl(new DaoHelperFactory(),
                        new TeamValidator()));
            case CommandName.EDIT_MATCH_PAGE:
                return new EditMatchPageCommand(new MatchServiceImpl(new DaoHelperFactory(),
                        new MatchValidator()), new TeamServiceImpl(new DaoHelperFactory(),
                        new TeamValidator()));
            case CommandName.BLOCK_USER:
                return new BlockCommand(new AccountServiceImpl(new DaoHelperFactory()));
            case CommandName.UNBLOCK_USER:
                return new UnblockCommand(new AccountServiceImpl(new DaoHelperFactory()));
            case CommandName.ACCEPT_MATCHES_PAGE:
                return new AcceptMatchesPageCommand(new MatchServiceImpl(new DaoHelperFactory(),
                        new MatchValidator()), new TeamServiceImpl(new DaoHelperFactory(),
                        new TeamValidator()));
            case CommandName.PAGINATION:
                return new PaginationCommand();
            case CommandName.ACCEPT_MATCH:
                return new AcceptMatchCommand(new MatchServiceImpl(new DaoHelperFactory(),
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
