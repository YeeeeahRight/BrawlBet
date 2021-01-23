<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sc" uri="custom-tags" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>

<div class="menu">
    <sc:access role="ADMIN">
        <a href="${pageContext.request.contextPath}/controller?command=matches">
            <div class="first-item">
                <div class="matches-image"></div>
                <h1 class="item-text">
                    <fmt:message key="menu.matches"/>
                </h1>
            </div>
        </a>
        <a href="${pageContext.request.contextPath}/controller?command=users">
            <div class="second-item">
                <div class="users-image"></div>
                <h1 class="item-text">
                    <fmt:message key="menu.users"/>
                </h1>
            </div>
        </a>
        <a href="${pageContext.request.contextPath}/controller?command=close-matches-page">
            <div class="third-item">
                <div class="menu-close-matches"></div>
                <h1 class="item-text">
                    <fmt:message key="menu.close.matches"/>
                </h1>
            </div>
        </a>
    </sc:access>
    <sc:access role="BOOKMAKER">
        <a href="${pageContext.request.contextPath}/controller?command=accept-matches-page">
            <div class="first-item">
                <div class="matches-image"></div>
                <h1 class="item-text">
                    <fmt:message key="menu.accept.matches"/>
                </h1>
            </div>
        </a>
        <a href="${pageContext.request.contextPath}/controller?command=bookmaker-history">
            <div class="second-item">
                <div class="history-image"></div>
                <h1 class="item-text">
                    <fmt:message key="menu.history"/>
                </h1>
            </div>
        </a>
    </sc:access>
    <sc:access role="USER">
        <a href="${pageContext.request.contextPath}/controller?command=deposit-page">
            <div class="first-item">
                <div class="deposit-image"></div>
                <h1 class="item-text">
                    <fmt:message key="menu.deposit"/>
                </h1>
            </div>
        </a>
        <a href="${pageContext.request.contextPath}/controller?command=my-bets">
            <div class="second-item">
                <div class="my-bets-image"></div>
                <h1 class="item-text">
                    <fmt:message key="menu.my.bets"/>
                </h1>
            </div>
        </a>
    </sc:access>
</div>
