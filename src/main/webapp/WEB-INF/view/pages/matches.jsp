<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sc" uri="custom-tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>

<html lang="<fmt:message key="html.lang"/>">
<head>
    <meta charset="UTF-8">
    <link href="${pageContext.request.contextPath}/static/styles/general-styles.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/static/styles/matches.css" rel="stylesheet" type="text/css">
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
                        <fmt:message key="menu.matches"/>
                    </h1>
                </div>
                <div class="matches-icon-second"></div>
            </div>
            <div class="main-body">
                <div class="button-add-match">
                    <a href="${pageContext.request.contextPath}/controller?command=add-match-page">
                        <h1 class="add-match-text">
                            <fmt:message key="match.add.upper"/>
                        </h1>
                    </a>
                </div>
                <c:forEach items="${matches}" var="match" varStatus="counter">
                    <div class="match">
                        <div class="edit-button">
                            <a href="${pageContext.request.contextPath}/controller?command=edit-match-page&id=${match.getId()}">
                                <div class="edit-image"></div>
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
                        <div class="remove-button">
                            <a href="${pageContext.request.contextPath}/controller?command=cancel-match&id=${match.getId()}">
                                <div class="remove-image"></div>
                            </a>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
</body>
</html>
