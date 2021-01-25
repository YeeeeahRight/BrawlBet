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
    <link href="${pageContext.request.contextPath}/static/styles/close-matches.css" rel="stylesheet"
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
                <div class="matches-icon-first"></div>
                <div class="matches-logo-text">
                    <h1>
                        <fmt:message key="match.closing"/>
                    </h1>
                </div>
                <div class="matches-icon-second"></div>
            </div>
            <div class="main-body">
                <c:forEach items="${matches}" var="match" varStatus="counter">
                    <div class="match match-${counter.index + 1}">
                        <div class="finish-button">
                            <a href="${pageContext.request.contextPath}/controller?command=close-match&id=${match.getId()}">
                                <div class="finish-image"></div>
                            </a>
                        </div>
                        <div class="match-description">
                            <div class="match-header">
                                <h1 class="date">
                                    <sc:date-formatter date="${match.getDate()}"
                                                       formatType="${sessionScope.lang}"></sc:date-formatter>
                                </h1>
                                <h1 class="tournament">${match.getTournament()}</h1>
                            </div>
                            <div class="match-content">
                                <h1 class="f-team-name">${match.getFirstTeam()}</h1>
                                <h1 class="versus"><-></h1>
                                <h1 class="s-team-name">${match.getSecondTeam()}</h1>
                            </div>
                        </div>
                    </div>
                </c:forEach>
                <c:if test="${matches.size() != 0}">
                    <div class="pagination">
                        <a class="first-arrow" id="${currentPage > 1}"
                           href="${pageContext.request.contextPath}/controller?command=close-matches-page&page=${currentPage - 1}"></a>
                        <form method="POST" action="${pageContext.request.contextPath}/controller?command=pagination">
                            <input type="number" id="page" value="${currentPage}"
                                   name="page" min="1" max="${maxPage}" required>
                        </form>
                        <h1>
                            <fmt:message key="pagination.of"/> ${maxPage}
                        </h1>
                        <a class="second-arrow" id="${currentPage < maxPage}"
                           href="${pageContext.request.contextPath}/controller?command=close-matches-page&page=${currentPage + 1}"></a>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</div>
</body>
</html>