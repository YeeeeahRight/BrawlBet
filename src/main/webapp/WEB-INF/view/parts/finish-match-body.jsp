<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sc" uri="custom-tags" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>

<div class="body">
    <jsp:include page="menu.jsp"/>
    <div class="main-content">
        <div class="main-header">
            <div class="matches-icon-first"></div>
            <div class="matches-logo-text">
                <h1>
                    <fmt:message key="match.finishing"/>
                </h1>
            </div>
            <div class="matches-icon-second"></div>
        </div>
        <div class="main-body">
            <c:forEach items="${matches}" var="match" varStatus="counter">
                <div class="match match-${counter.index + 1}">
                    <div class="finish-button">
                        <a href="/WebProject/controller?command=close-match&id=${match.getId()}">
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
        </div>
    </div>
</div>