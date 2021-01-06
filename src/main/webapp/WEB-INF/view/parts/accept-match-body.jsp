<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>

<div class="body">
    <jsp:include page="menu.jsp"/>
    <div class="main-content">
        <div class="main-header">
            <div class="matches-icon-first"></div>
            <div class = "matches-logo-text">
                <h1>
                    <fmt:message key="match.accepting"/>
                </h1>
            </div>
            <div class="matches-icon-second"></div>
        </div>
        <div class="main-body">
            <c:forEach items="${matches}" var="match" varStatus="counter">
                <form method = "POST"
                      action="${pageContext.request.contextPath}/controller?command=accept-match&id=${match.getId()}">
                    <div class="match match-${counter.index + 1}">
                        <div class="accept-button">
                            <button type="submit">
                                <div class="accept-image"></div>
                            </button>
                        </div>
                        <div class="match-description">
                            <div class="match-header">
                                <h1 class="date">${match.getMatchFormattedDate()}</h1>
                                <h1 class="tournament">${match.getTournament()}</h1>
                            </div>
                            <div class="match-content">
                                <h1 class="f-team-name">${match.getFirstTeam()}</h1>
                                <h1 class="versus"><-></h1>
                                <h1 class="s-team-name">${match.getSecondTeam()}</h1>
                            </div>
                            <div class="match-commission">
                                <input class="commission-input" type="text"
                                       placeholder="<fmt:message key="match.enter.commission"/>"
                                       name="commission" required>
                            </div>
                        </div>
                        <div class="cancel-button">
                            <a href="${pageContext.request.contextPath}/controller?command=remove-match&id=1">
                                <div class="cancel-image"></div>
                            </a>
                        </div>
                    </div>
                </form>
            </c:forEach>
        </div>
    </div>
</div>