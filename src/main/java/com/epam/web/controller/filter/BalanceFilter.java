package com.epam.web.controller.filter;

import com.epam.web.constant.Attribute;
import com.epam.web.dao.helper.DaoHelperFactory;
import com.epam.web.exception.ServiceException;
import com.epam.web.logic.service.account.AccountServiceImpl;
import com.epam.web.model.enumeration.AccountRole;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class BalanceFilter implements Filter {
    private static final AccountServiceImpl USER_SERVICE = new AccountServiceImpl(new DaoHelperFactory());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest)servletRequest).getSession();
        AccountRole role = (AccountRole)session.getAttribute(Attribute.ROLE);
        if (role != null && role != AccountRole.ADMIN) {
            Long userId = (Long)session.getAttribute(Attribute.ACCOUNT_ID);
            setBalanceSession(session, userId);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void setBalanceSession(HttpSession session, Long userId) throws ServletException {
        float balance;
        try {
            balance = USER_SERVICE.getBalance(userId).floatValue();
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        session.setAttribute(Attribute.BALANCE, balance);
    }

    @Override
    public void destroy() {

    }
}
