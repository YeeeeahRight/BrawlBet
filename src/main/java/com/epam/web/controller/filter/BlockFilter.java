package com.epam.web.controller.filter;

import com.epam.web.constant.Attribute;
import com.epam.web.dao.helper.DaoHelperFactory;
import com.epam.web.exception.ServiceException;
import com.epam.web.logic.service.account.AccountServiceImpl;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class BlockFilter implements Filter {
    private static final AccountServiceImpl USER_SERVICE = new AccountServiceImpl(new DaoHelperFactory());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) servletRequest).getSession();
        Long accountId = (Long) session.getAttribute(Attribute.ACCOUNT_ID);
        if (accountId != null) {
            try {
                if (USER_SERVICE.isBlockedById(accountId)) {
                    session.invalidate();
                }
            } catch (ServiceException e) {
                session.invalidate();
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
