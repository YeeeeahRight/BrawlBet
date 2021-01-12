package com.epam.web.controller.filter;

import com.epam.web.constant.Attribute;
import com.epam.web.constant.CommandName;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AccessFilter implements Filter {
    private static final String COMMAND_NAME_PARAM = "command";
    private static final String GUEST_ROLE = "GUEST";
    private static final String ADMIN_ROLE = "ADMIN";
    private static final String USER_ROLE = "USER";
    private static final String BOOKMAKER_ROLE = "BOOKMAKER";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain next) throws IOException, ServletException {
        String commandName = request.getParameter(COMMAND_NAME_PARAM);
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        String role = (String)session.getAttribute(Attribute.ROLE);
        if (role == null) {
            role = GUEST_ROLE;
        }
        boolean isAccessAllowed = isAccessAllowed(commandName, role);
        if (isAccessAllowed) {
            next.doFilter(request, response);
        } else {
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    private boolean isAccessAllowed(String commandName, String role) {
        if (commandName == null) {
            return true;
        }
        //case CommandName.LOGOUT: was !role.equals(GUEST)
        switch (commandName) {
            case CommandName.SIGN_UP:
            case CommandName.SIGN_UP_PAGE:
            case CommandName.LOGIN:
            case CommandName.LOGIN_PAGE:
                return role.equalsIgnoreCase(GUEST_ROLE);
            case CommandName.BET:
            case CommandName.DEPOSIT:
            case CommandName.DEPOSIT_PAGE:
                return role.equalsIgnoreCase(USER_ROLE);
            case CommandName.MATCHES:
            case CommandName.ADD_MATCH:
            case CommandName.ADD_MATCH_PAGE:
            case CommandName.EDIT_MATCH_PAGE:
            case CommandName.EDIT_MATCH:
            case CommandName.BLOCK_USER:
            case CommandName.UNBLOCK_USER:
            case CommandName.CANCEL_MATCH:
            case CommandName.CLOSE_MATCHES_PAGE:
            case CommandName.CLOSE_MATCH:
            case CommandName.USERS:
                return role.equalsIgnoreCase(ADMIN_ROLE);
            case CommandName.REMOVE_MATCH:
            case CommandName.ACCEPT_MATCH:
            case CommandName.ACCEPT_MATCHES_PAGE:
                return role.equalsIgnoreCase(BOOKMAKER_ROLE);
            default:
                return true;
        }
    }

    @Override
    public void destroy() {

    }
}
