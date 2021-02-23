<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sc" uri="custom-tags" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>

<html lang="<fmt:message key="html.lang"/>">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${pageContext.request.contextPath}/static/styles/general-styles.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/static/styles/bookmaker-history.css" rel="stylesheet"
          type="text/css">
    <link href="${pageContext.request.contextPath}/static/styles/pagination.css" rel="stylesheet" type="text/css">
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
                <c:forEach items="${matchDtoList}" var="matchDto" varStatus="counter">
                    <div class="match">
                        <div class="commission-info">
                            <h1 class="commission-text">
                                <fmt:message key="match.commission"/>
                            </h1>
                            <h1 class="commission-value">
                                    ${matchDto.getCommission()}%
                            </h1>
                        </div>
                        <a href="${pageContext.request.contextPath}/controller?command=match-page&id=${matchDto.getId()}">
                            <div class="match-description">
                                <div class="match-header">
                                    <h1 class="date-text">
                                        <sc:date-formatter date="${matchDto.getDate()}"
                                                           formatType="${sessionScope.lang}"></sc:date-formatter>
                                    </h1>
                                    <h1 class="tournament-text">
                                            ${matchDto.getTournament()}
                                    </h1>
                                </div>
                                <div class="match-content">
                                    <div class="match-f-team ${matchDto.getFirstTeam().equals(matchDto.getWinner())}">
                                        <h1>
                                                ${matchDto.getFirstTeam()}
                                        </h1>
                                    </div>
                                    <c:if test="${matchDto.getFirstTeam().equals(matchDto.getWinner())}">
                                        <div class="winner-image"></div>
                                    </c:if>
                                    <div class="match-detailed-info">
                                        <h1>
                                            -><fmt:message key="match.detailed.info"/><-
                                        </h1>
                                    </div>
                                    <c:if test="${matchDto.getSecondTeam().equals(matchDto.getWinner())}">
                                        <div class="winner-image"></div>
                                    </c:if>
                                    <div class="match-s-team ${matchDto.getSecondTeam().equals(matchDto.getWinner())}">
                                        <h1>
                                                ${matchDto.getSecondTeam()}
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
                                <c:set var="betsAmount" value="${matchDto.getWinner().equals(matchDto.getFirstTeam()) ?
                                                    matchDto.getSecondTeamBets() : matchDto.getFirstTeamBets()}"/>
                                <fmt:formatNumber type="number" maxFractionDigits="2" pattern="0.0"
                                                  value="${betsAmount * matchDto.getCommission() / 100}"/>
                            </h1>
                        </div>
                    </div>
                </c:forEach>
                <c:if test="${matchDtoList.size() != 0}">
                    <div class="pagination">
                        <a class="first-arrow" id="${currentPage > 1}"
                           href="${pageContext.request.contextPath}/controller?command=bookmaker-history&page=${currentPage - 1}"></a>
                        <form method="POST" action="${pageContext.request.contextPath}/controller?command=pagination">
                            <input type="number" id="page" value="${currentPage}"
                                   name="page" min="1" max="${maxPage}" required>
                        </form>
                        <h1>
                            <fmt:message key="pagination.of"/> ${maxPage}
                        </h1>
                        <a class="second-arrow" id="${currentPage < maxPage}"
                           href="${pageContext.request.contextPath}/controller?command=bookmaker-history&page=${currentPage + 1}"></a>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</div>
</body>
</html>