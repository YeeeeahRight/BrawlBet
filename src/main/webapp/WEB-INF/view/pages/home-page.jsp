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
    <link href="${pageContext.request.contextPath}/static/styles/header.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/static/styles/home.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/static/styles/menu.css" rel="stylesheet" type="text/css">
    <title>Brawl bet!</title>
</head>
<body>
<div class="container">
    <jsp:include page="../parts/header.jsp"/>
    <div class="body">
        <jsp:include page="../parts/menu.jsp"/>
        <div class="main-content">
            <div class="main-header">
                <div class="bets-icon"></div>
                <div class="bets-text">
                    <h1>
                        <fmt:message key="bets.bets"/>
                    </h1>
                </div>
            </div>
            <div class="main-body">
                <c:forEach items="${matchBetsDtoList}" var="matchBetsDto" varStatus="counter">
                    <a href="${pageContext.request.contextPath}/controller?command=match-page&id=${matchBetsDto.getId()}">
                        <div class="match match-${counter.index + 1}" id="${matchBetsDto.isClosed()}">
                            <div class="match-header">
                                <div class="match-date">
                                    <h1>
                                        <sc:date-formatter date="${matchBetsDto.getDate()}"
                                                           formatType="${sessionScope.lang}"></sc:date-formatter>
                                    </h1>
                                </div>
                                <div class="match-tournament">
                                    <h1>${matchBetsDto.getTournament()}</h1>
                                </div>
                            </div>
                            <div class="match-body">
                                <div class="match-f-team ${matchBetsDto.getFirstTeam().equals(matchBetsDto.getWinner())}">
                                    <h1>
                                            ${matchBetsDto.getFirstTeam()}
                                    </h1>
                                </div>
                                <div class="match-body-center">
                                    <div class="match-f-percent">
                                        <h1>
                                                ${matchBetsDto.getFirstPercent()}%
                                        </h1>
                                    </div>
                                    <c:if test="${matchBetsDto.getFirstTeam().equals(matchBetsDto.getWinner())}">
                                        <div class="winner-image"></div>
                                    </c:if>
                                    <div class="match-versus-icon">
                                        <h1>
                                            -
                                        </h1>
                                    </div>
                                    <c:if test="${matchBetsDto.getSecondTeam().equals(matchBetsDto.getWinner())}">
                                        <div class="winner-image"></div>
                                    </c:if>
                                    <div class="match-s-percent">
                                        <h1>
                                                ${matchBetsDto.getSecondPercent()}%
                                        </h1>
                                    </div>
                                </div>
                                <div class="match-s-team ${matchBetsDto.getSecondTeam().equals(matchBetsDto.getWinner())}">
                                    <h1>
                                            ${matchBetsDto.getSecondTeam()}
                                    </h1>
                                </div>
                            </div>
                        </div>
                    </a>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
</body>
</html>