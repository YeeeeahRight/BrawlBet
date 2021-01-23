<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sc" uri="custom-tags" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>

<html lang="<fmt:message key="html.lang"/>">
<head>
    <meta charset="UTF-8">
    <link href="${pageContext.request.contextPath}/static/styles/general-styles.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/static/styles/bookmaker-history.css" rel="stylesheet"
          type="text/css">
    <title>Brawl bet!</title>
</head>
<body>
<div class="container">
    <jsp:include page="../parts/header.jsp"/>
    <div class="body">
        <jsp:include page="../parts/menu.jsp"/>
        <div class="main-content">
            <div class="main-header">
                <div class="history-logo"></div>
                <div class="history-text">
                    <h1>
                        <fmt:message key="menu.history"/>
                    </h1>
                </div>
            </div>
            <div class="main-body">
                <c:forEach items="${matches}" var="match" varStatus="counter">
                    <div class="match">
                        <div class="commission-info">
                            <h1 class="commission-text">
                                <fmt:message key="match.commission"/>
                            </h1>
                            <h1 class="commission-value">
                                    ${match.getCommission()}%
                            </h1>
                        </div>
                        <a href="${pageContext.request.contextPath}/controller?command=match-page&id=${match.getId()}">
                            <div class="match-description">
                                <div class="match-header">
                                    <h1 class="date-text">
                                        <sc:date-formatter date="${match.getDate()}"
                                                           formatType="${sessionScope.lang}"></sc:date-formatter>
                                    </h1>
                                    <h1 class="tournament-text">
                                            ${match.getTournament()}
                                    </h1>
                                </div>
                                <div class="match-content">
                                    <div class="match-f-team ${match.getFirstTeam().equals(match.getWinner())}">
                                        <h1>
                                                ${match.getFirstTeam()}
                                        </h1>
                                    </div>
                                    <c:if test="${match.getFirstTeam().equals(match.getWinner())}">
                                        <div class="winner-image"></div>
                                    </c:if>
                                    <div class="match-detailed-info">
                                        <h1>
                                            -><fmt:message key="match.detailed.info"/><-
                                        </h1>
                                    </div>
                                    <c:if test="${match.getSecondTeam().equals(match.getWinner())}">
                                        <div class="winner-image"></div>
                                    </c:if>
                                    <div class="match-s-team ${match.getSecondTeam().equals(match.getWinner())}">
                                        <h1>
                                                ${match.getSecondTeam()}
                                        </h1>
                                    </div>
                                </div>
                            </div>
                        </a>
                        <div class="gain-info">
                            <h1 class="gain-text">
                                <fmt:message key="bet.money.received"/>
                            </h1>
                            <h1 class="gain-money">
                                <c:set var="betsAmount" value="${match.getWinner().equals(match.getFirstTeam()) ?
                                                    match.getSecondTeamBets() : match.getFirstTeamBets()}"/>
                                <fmt:formatNumber type="number" maxFractionDigits="2"
                                                  value="${betsAmount * match.getCommission() / 100}"/>
                            </h1>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
</body>
</html>