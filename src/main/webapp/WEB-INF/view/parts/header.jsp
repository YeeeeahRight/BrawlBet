<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sc" uri="custom-tags" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>

<header>
    <div class="logo">
        <h1 class="brawl-bet-text">
            <a href="${pageContext.request.contextPath}/controller?command=home-page&page=1">
                <span class="brawl-text">BRAWL </span>
                <span class="bet-text">BET</span>
            </a>
        </h1>
    </div>
    <div class="header-content">
        <sc:access role="ADMIN">
            <div class="admin-mode">
                <h1 class="admin-mode-text">
                    <fmt:message key="header.admin.mode"/>
                </h1>
            </div>
        </sc:access>
        <sc:access role="USER">
            <div class="account-balance-block">
                <h1 class="account-balance-text">
                    <fmt:message key="header.account.balance"/>
                </h1>
                <h1 class="account-balance">
                    <fmt:formatNumber type="number" maxFractionDigits="2"
                                      pattern="0.0" value="${sessionScope.balance}"/>
                </h1>
            </div>
        </sc:access>
        <sc:access role="BOOKMAKER">
            <div class="bookmaker-mode">
                <h1 class="bookmaker-mode-text">
                    <fmt:message key="header.bookmaker.mode"/>
                </h1>
            </div>
            <div class="account-balance-block">
                <h1 class="account-balance-text">
                    <fmt:message key="header.account.balance"/>
                </h1>
                <h1 class="account-balance">
                    <fmt:formatNumber type="number" maxFractionDigits="2"
                                      pattern="0.0" value="${sessionScope.balance}"/>
                </h1>
            </div>
        </sc:access>
        <div class="lang-menu">
            <div class="language-text">
                <fmt:message key="header.language"/>
            </div>
            <ul>
                <li>
                    <a href="${pageContext.request.contextPath}/controller?command=localization&lang=en">
                        English
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/controller?command=localization&lang=ru">
                        Русский
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/controller?command=localization&lang=be">
                        Беларуская
                    </a>
                </li>
            </ul>
        </div>
        <sc:access role="GUEST">
            <a href="${pageContext.request.contextPath}/controller?command=login-page">
                <div class="login">
                    <h1 class="login-text">
                        <fmt:message key="header.login"/>
                    </h1>
                </div>
            </a>
        </sc:access>
        <sc:access role="NOT_GUEST">
            <a href="${pageContext.request.contextPath}/controller?command=logout">
                <div class="login">
                    <h1 class="login-text">
                        <fmt:message key="header.logout"/>
                    </h1>
                </div>
            </a>
        </sc:access>
    </div>
</header>