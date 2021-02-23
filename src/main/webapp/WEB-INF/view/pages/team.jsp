<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>

<html lang="<fmt:message key="html.lang"/>">
<head>
    <meta charset="UTF-8">
    <link href="${pageContext.request.contextPath}/static/styles/general-styles.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/static/styles/team.css" rel="stylesheet" type="text/css">
    <title>Brawl bet!</title>
</head>
<body>
<div class="container">
    <jsp:include page="../parts/header.jsp"/>
    <div class="body">
        <jsp:include page="../parts/menu.jsp"/>
        <div class="main-content">
            <div class="main-header">
                <h1 class="team-text">
                    <fmt:message key="team.team"/>
                </h1>
                <h1 class="team-name-text">
                    ${team.getName()}
                </h1>
            </div>
            <div class="main-body">
                <div class="statistics-block">
                    <h1 class="statistics-text">
                        <fmt:message key="team.statistics"/>:
                    </h1>
                    <h1 class="matches-won">
                        ${team.getMatchesWon()}
                    </h1>
                    <h1 class="statistics-separator">
                        -
                    </h1>
                    <h1 class="matches-lost">
                        ${team.getMatchesLost()}
                    </h1>
                </div>
                <div class="win-rate-block">
                    <c:choose>
                        <c:when test="${winRate == -1}">
                            <h1 class="no-win-rate">
                                <fmt:message key="team.no.win.rate"/>
                            </h1>
                        </c:when>
                        <c:otherwise>
                            <h1 class="win-rate-text">
                                <fmt:message key="team.win.rate"/>:
                            </h1>
                            <h1 class="win-rate">
                                <fmt:formatNumber type="number" maxFractionDigits="2"
                                                  pattern="0.0" value="${winRate}"/>%
                            </h1>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
