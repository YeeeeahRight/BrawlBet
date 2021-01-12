package com.epam.web.controller.filter;

import com.epam.web.constant.Attribute;
import com.epam.web.dao.helper.DaoHelperFactory;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.logic.service.UserService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class BalanceFilter implements Filter {
    private static final UserService USER_SERVICE = new UserService(new DaoHelperFactory());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest)servletRequest).getSession();
        String role = (String)session.getAttribute(Attribute.ROLE);
        if (role != null && (role.equals("USER") || role.equals("BOOKMAKER"))) {
            Long userId = (Long)session.getAttribute(Attribute.ACCOUNT_ID);
            setBalanceSession(session, userId);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void setBalanceSession(HttpSession session, Long userId) throws ServletException {
        int balance;
        try {
            balance = USER_SERVICE.getBalance(userId);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        session.setAttribute(Attribute.BALANCE, balance);
    }

    @Override
    public void destroy() {

    }
}
