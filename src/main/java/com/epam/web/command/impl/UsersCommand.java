package com.epam.web.command.impl;

import com.epam.web.command.Command;
import com.epam.web.command.CommandResult;
import com.epam.web.entity.User;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.request.RequestContext;
import com.epam.web.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class UsersCommand implements Command {
    private static final String USER_ROLE = "user";
    private static final String BOOKMAKER_ROLE = "bookmaker";
    private static final String USERS_PAGE = "WEB-INF/view/pages/users.jsp";
    private static final String USERS_ATTRIBUTE = "users";
    private final UserService userService;

    public UsersCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {
        List<User> allUsers = userService.getAll();
        List<User> userList = getUserList(allUsers);
        requestContext.addAttribute(USERS_ATTRIBUTE, userList);
        return CommandResult.forward(USERS_PAGE);
    }

    private List<User> getUserList(List<User> users) {
        List<User> showedUsers = new ArrayList<>();
        for (User user : users) {
            String role = user.getRole();
            if (role.equals(USER_ROLE)) {
                showedUsers.add(user);
            } else if (role.equals(BOOKMAKER_ROLE)) {
                showedUsers.add(0, user);
            }
        }
        return showedUsers;
    }
}
