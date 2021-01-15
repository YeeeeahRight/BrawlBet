<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sc" uri="custom-tags" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>

<div class="main-content">
    <div class="main-header">
        <div class ="logo-content">
            <div class = "bets-text">
                <h1>
                    <fmt:message key="bets.bets"/>
                </h1>
            </div>
            <div class="bets-icon"></div>
        </div>
    </div>
    <div class="main-body">
        <c:forEach items="${matches}" var="match" varStatus="counter">
            <a href="${pageContext.request.contextPath}/controller?command=bet-page&id=${match.getId()}">
                <div class="match match-${counter.index + 1}" >
                    <div class="match-body">
                        <div class="match-f-team">
                            <h1>${match.getFirstTeam()}</h1>
                        </div>
                        <div class="match-f-percent">
                            <h1>
                                ${firstPercent}%
                            </h1>
                        </div>
                        <div class="match-versus-icon">
                            <h1>-</h1>
                        </div>
                        <div class="match-s-percent">
                            <h1>
                                ${secondPercent}%
                            </h1>
                        </div>
                        <div class="match-s-team">
                            <h1>${match.getSecondTeam()}</h1>
                        </div>
                    </div>
                    <div class="match-header">
                        <div class="match-date">
                            <h1>
                                <sc:date-formatter date="${match.getDate()}"
                                                   formatType="${sessionScope.lang}"></sc:date-formatter>
                            </h1>
                        </div>
                        <div class="match-tournament">
                            <h1>${match.getTournament()}</h1>
                        </div>
                    </div>
                </div>
            </a>
        </c:forEach>
    </div>
</div>