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
    <link href="${pageContext.request.contextPath}/static/styles/accept-matches.css" rel="stylesheet"
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
                <div class="matches-icon-first"></div>
                <div class="matches-logo-text">
                    <h1>
                        <fmt:message key="match.accepting"/>
                    </h1>
                </div>
                <div class="matches-icon-second"></div>
            </div>
            <div class="main-body">
                <c:forEach items="${matches}" var="match" varStatus="counter">
                    <form method="POST" action="${pageContext.request.contextPath}
                                        /controller?command=accept-match&id=${match.getId()}">
                        <div class="match">
                            <div class="accept-button">
                                <button type="submit">
                                    <div class="accept-image"></div>
                                </button>
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
                                <div class="match-commission">
                                    <input class="commission-input" type="number" required step="0.01"
                                           placeholder="<fmt:message key="match.enter.commission"/>"
                                           name="commission" min="1" max="8">
                                </div>
                            </div>
                            <div class="cancel-button">
                                <a href="${pageContext.request.contextPath}
                                        /controller?command=remove-match&id=${match.getId()}">
                                    <div class="cancel-image"></div>
                                </a>
                            </div>
                        </div>
                    </form>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
</body>
</html>