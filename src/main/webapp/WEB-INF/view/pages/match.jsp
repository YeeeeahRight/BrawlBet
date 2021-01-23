<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sc" uri="custom-tags" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>

<html lang="<fmt:message key="html.lang"/>" id="html">
<head>
    <meta charset="UTF-8">
    <link href="${pageContext.request.contextPath}/static/styles/general-styles.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/static/styles/header.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/static/styles/match-body.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/static/styles/menu.css" rel="stylesheet" type="text/css">
    <script src="<c:url value="/static/js/bet-page.js"/>"></script>
    <title>Brawl bet!</title>
</head>
<body>
<div class="container">
    <jsp:include page="../parts/header.jsp"/>
    <div class="body">
        <jsp:include page="../parts/menu.jsp"/>
        <div class="main-content">
            <div class="main-header">
                <div class="teams-text">
                    <h1>
                        <fmt:message key="match.match"/>
                    </h1>
                </div>
            </div>
            <div class="main-body">
                <div class="match">
                    <div class="match-header">
                        <div class="match-date">
                            <h1>
                                <sc:date-formatter date="${match.getDate()}"
                                                   formatType="${sessionScope.lang}"></sc:date-formatter>
                            </h1>
                        </div>
                    </div>
                    <div class="match-body">
                        <div class="f-team ${isMatchClosed && match.getFirstTeam().equals(match.getWinner())}">
                            <div class="f-team-name">
                                <h1>
                                    ${match.getFirstTeam()}
                                </h1>
                            </div>
                            <div class="f-percent">
                                <h1>
                                    ${firstPercent}%
                                </h1>
                            </div>
                        </div>
                        <div class="match-versus">
                            <h1>
                                VS
                            </h1>
                        </div>
                        <div class="s-team ${isMatchClosed && match.getSecondTeam().equals(match.getWinner())}">
                            <div class="s-team-name">
                                <h1>
                                    ${match.getSecondTeam()}
                                </h1>
                            </div>
                            <div class="s-percent">
                                <h1>
                                    ${secondPercent}%
                                </h1>
                            </div>
                        </div>
                    </div>
                    <div class="match-footer">
                        <div class="match-tournament">
                            <h1>
                                ${match.getTournament()}
                            </h1>
                        </div>
                    </div>
                    <div class="match-description">
                        <div class="commission-text">
                            <h1>
                                <fmt:message key="bet.commission"/>
                            </h1>
                            <h1 id="commission">
                                ${match.getCommission()}%
                            </h1>
                        </div>
                    </div>
                    <div class="bets-amounts">
                        <div class="first-bet-amount">
                            <h1 class="bet-amount-text">
                                <fmt:message key="bet.left.amount"/>
                            </h1>
                            <h1 id="first-bets-amount">
                                <fmt:formatNumber type="number" maxFractionDigits="2"
                                                  value="${firstBetsAmount}"/>
                            </h1>
                        </div>
                        <div class="second-bet-amount">
                            <h1 class="bet-amount-text">
                                <fmt:message key="bet.right.amount"/>
                            </h1>
                            <h1 id="second-bets-amount">
                                <fmt:formatNumber type="number" maxFractionDigits="2"
                                                  value="${secondBetsAmount}"/>
                            </h1>
                        </div>
                    </div>
                </div>
                <c:choose>
                    <c:when test="${isMatchClosed}">
                        <div class="match-info-block">
                            <div class="match-info-text">
                                <h1>
                                    <fmt:message key="match.closed"/>
                                </h1>
                                <h1>
                                    <fmt:message key="match.winner"/>${match.getWinner()}
                                </h1>
                            </div>
                        </div>
                    </c:when>
                    <c:when test="${isMatchUnaccepted}">
                        <div class="match-info-block">
                            <h1 class="match-info-text">
                                <fmt:message key="match.unaccepted"/>
                            </h1>
                        </div>
                    </c:when>
                    <c:when test="${isMatchFinished}">
                        <div class="match-info-block">
                            <h1 class="match-info-text">
                                <fmt:message key="match.finished"/>
                            </h1>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <sc:access role="USER">
                            <form id="money-input-form" method="POST"
                                  action="${pageContext.request.contextPath}/controller?command=bet&id=${match.getId()}">
                                <input id="money" type="number" step="0.01" required
                                       placeholder="<fmt:message key="bet.enter.money"/>"
                                       name="money" min="${minBet}" max="${maxBet}" oninput="calculatePotentialGains()">
                                <input id="on" type="text" name="on" value=""/>
                                <div class="bet-buttons">
                                    <button type="submit" onclick="betOnFirst()" class="first-button">
                                        <div class="button">
                                            <div class="bet-on">
                                                <h1>
                                                    <fmt:message key="bet.on.upper"/>${match.getFirstTeam()}
                                                </h1>
                                            </div>
                                            <div class="coefficient-value">
                                                <h1 class="coefficient-text">
                                                    <fmt:message key="bet.coefficient"/>
                                                </h1>
                                                <h1 id="first-coefficient">
                                                    <fmt:formatNumber type="number" maxFractionDigits="3"
                                                                      value="${firstCoefficient}"/>
                                                </h1>
                                            </div>
                                            <div class="potential-gain-value">
                                                <h1 class="potential-gain-text">
                                                    <fmt:message key="bet.potential.gain"/>
                                                </h1>
                                                <h1 id="first-potential-gain">
                                                    <fmt:formatNumber type="number" maxFractionDigits="3"
                                                                      value=""/>
                                                </h1>
                                            </div>
                                        </div>
                                    </button>
                                    <button type="submit" onclick="betOnSecond()" class="second-button">
                                        <div class="button">
                                            <div class="bet-on">
                                                <h1>
                                                    <fmt:message key="bet.on.upper"/>${match.getSecondTeam()}
                                                </h1>
                                            </div>
                                            <div class="coefficient-value">
                                                <h1 class="coefficient-text">
                                                    <fmt:message key="bet.coefficient"/>
                                                </h1>
                                                <h1 id="second-coefficient">
                                                    <fmt:formatNumber type="number" maxFractionDigits="3"
                                                                      value="${secondCoefficient}"/>
                                                </h1>
                                            </div>
                                            <div class="potential-gain-value">
                                                <h1 class="potential-gain-text">
                                                    <fmt:message key="bet.potential.gain"/>
                                                </h1>
                                                <h1 id="second-potential-gain">
                                                    <fmt:formatNumber type="number" maxFractionDigits="3"
                                                                      value=""/>
                                                </h1>
                                            </div>
                                        </div>
                                    </button>
                                </div>
                            </form>
                        </sc:access>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>
</body>
</html>