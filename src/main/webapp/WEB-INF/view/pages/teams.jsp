<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sc" uri="custom-tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>

<html lang="<fmt:message key="html.lang"/>">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${pageContext.request.contextPath}/static/styles/general-styles.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/static/styles/teams.css" rel="stylesheet" type="text/css">
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
                <div class="teams-logo-text">
                    <h1>
                        <fmt:message key="menu.teams"/>
                    </h1>
                </div>
            </div>
            <div class="main-body">
                <div class="button-add-team">
                    <a href="${pageContext.request.contextPath}/controller?command=add-team-page">
                        <h1 class="add-team-text">
                            <fmt:message key="team.add.upper"/>
                        </h1>
                    </a>
                </div>
                <c:forEach items="${teams}" var="team" varStatus="counter">
                    <c:if test="${counter.index % 2 == 0}">
                        <div class="team-row">
                    </c:if>
                    <a class="team team-${counter.index % 2}"
                       href="${pageContext.request.contextPath}/controller?command=team&id=${team.getId()}">
                        <div>
                            <div class="team-header">
                                <h1 class="team-name">
                                        ${team.getName()}
                                </h1>
                            </div>
                            <div class="team-content">
                                <h1 class="matches-won">
                                        ${team.getMatchesWon()}
                                </h1>
                                <h1 class="versus">
                                    -
                                </h1>
                                <h1 class="matches-lost">
                                        ${team.getMatchesLost()}
                                </h1>
                            </div>
                        </div>
                    </a>
                    <c:if test="${counter.index % 2 != 0}">
                        </div>
                    </c:if>
                    <c:set var="cycleCounter" value="${counter}"/>
                </c:forEach>
                <c:if test="${cycleCounter.index % 2 == 0}">
            </div>
            </c:if>
            <c:if test="${teams.size() != 0}">
                <div class="pagination">
                    <a class="first-arrow" id="${currentPage > 1}"
                       href="${pageContext.request.contextPath}/controller?command=teams&page=${currentPage - 1}"></a>
                    <form method="POST" action="${pageContext.request.contextPath}/controller?command=pagination">
                        <input type="number" id="page" value="${currentPage}"
                               name="page" min="1" max="${maxPage}" required>
                    </form>
                    <h1>
                        <fmt:message key="pagination.of"/> ${maxPage}
                    </h1>
                    <a class="second-arrow" id="${currentPage < maxPage}"
                       href="${pageContext.request.contextPath}/controller?command=teams&page=${currentPage + 1}"></a>
                </div>
            </c:if>
        </div>
    </div>
</div>
</div>
</body>
</html>
