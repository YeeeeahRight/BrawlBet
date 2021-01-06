<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
            <img class="shape-draw"
                 src="https://anima-uploads.s3.amazonaws.com/projects/5fb8769c7ea495239fa1fd21/releases/5fbdbd2ac1b21e32784fb837/img/combined-shape@2x.svg"
            />
        </div>
    </div>
    <div class="main-body">
        <c:forEach items="${matches}" var="match" varStatus="counter">
            <a href="${pageContext.request.contextPath}/controller?command=bet&id=${match.getId()}">
                <div class="match match-${counter.index + 1}" >
                    <div class="match-body">
                        <div class="match-f-team">
                            <h1>${match.getFirstTeam()}</h1>
                        </div>
                        <div class="match-f-percent">
                            <h1>${match.getFirstPercent()}
                                <%="%"%>
                            </h1>
                        </div>
                        <div class="match-versus-icon">
                            <h1>-</h1>
                        </div>
                        <div class="match-s-percent">
                            <h1>${match.getSecondPercent()}
                                <%="%"%>
                            </h1>
                        </div>
                        <div class="match-s-team">
                            <h1>${match.getSecondTeam()}</h1>
                        </div>
                    </div>
                    <div class="match-header">
                        <div class="match-date">
                            <h1>${match.getMatchFormattedDate()}</h1>
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