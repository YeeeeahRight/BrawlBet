<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>

<html lang="<fmt:message key="html.lang"/>" id="html">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${pageContext.request.contextPath}/static/styles/general-styles.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/static/styles/add-match.css" rel="stylesheet" type="text/css">
    <title>Brawl bet!</title>
</head>
<body>
<div class="container">
    <jsp:include page="../parts/header.jsp"/>
    <div class="body">
        <jsp:include page="../parts/menu.jsp"/>
        <div class="main-content">
            <div class="main-header">
                <div class="add-text">
                    <h1>
                        <fmt:message key="match.add"/>
                    </h1>
                </div>
            </div>
            <div class="main-body">
                <div class="add-form">
                    <div class="texts">
                        <div class="date-text">
                            <h1>
                                <fmt:message key="match.date"/>:
                            </h1>
                        </div>
                        <div class="tournament-text">
                            <h1>
                                <fmt:message key="match.tournament"/>:
                            </h1>
                        </div>
                        <div class="first-team-text">
                            <h1>
                                <fmt:message key="match.team.first"/>:
                            </h1>
                        </div>
                        <div class="second-team-text">
                            <h1>
                                <fmt:message key="match.team.second"/>:
                            </h1>
                        </div>
                    </div>
                    <form method="POST" action="${pageContext.request.contextPath}/controller?command=add-match">
                        <div class="input-form">
                            <input class="date"
                                   type="datetime-local"
                                   min="2021-01-05T00:00" max="2021-12-31T23:59"
                                   placeholder="<fmt:message key="match.date.advice"/>"
                                   name="date" required>
                            <input class="tournament" type="text"
                                   placeholder="<fmt:message key="match.tournament.advice"/>"
                                   name="tournament" maxlength="15" required>
                            <input class="first-team" type="text" id="firstTeam"
                                   placeholder="<fmt:message key="match.team.first.advice"/>"
                                   name="firstTeam" maxlength="15" required>
                            <input class="second-team" type="text" id="secondTeam"
                                   placeholder="<fmt:message key="match.team.second.advice"/>"
                                   name="secondTeam" maxlength="15" required>
                            <button type="submit">
                                <fmt:message key="match.add.button"/>
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

<script src="<c:url value="/static/js/same-teams-validator.js"/>"></script>

