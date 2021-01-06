<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>

<div class="body">
    <jsp:include page="menu.jsp"/>
    <div class="main-content">
        <div class="main-header">
            <div class="teams-text">
                <h1>
                    <fmt:message key="match.match"/>
                </h1>
            </div>
        </div>
        <div class="main-body">
            <div class="match">
                <div class="match-header">
                    <div class="match-date">
                        <h1>
                            ${match.getMatchFormattedDate()}
                        </h1>
                    </div>
                </div>
                <div class="match-body">
                    <div class="f-team">
                        <div class="f-team-name">
                            <h1>
                                ${match.getFirstTeam()}
                            </h1>
                        </div>
                        <div class="f-percent-name">
                            <h1>
                                ${match.getFirstPercent()}%
                            </h1>
                        </div>
                    </div>
                    <div class="match-versus">
                        <h1>
                            VS
                        </h1>
                    </div>
                    <div class="s-team">
                        <div class="s-team-name">
                            <h1>
                                ${match.getSecondTeam()}
                            </h1>
                        </div>
                        <div class="s-percent-name">
                            <h1>
                                ${match.getSecondPercent()}%
                            </h1>
                        </div>
                    </div>
                </div>
                <div class="match-footer">
                    <div class="match-tournament">
                        <h1>
                            ${match.getTournament()}
                        </h1>
                    </div>
                </div>
                <div class="match-description">
                    <div class="commission">
                        <h1>
                            <fmt:message key="match.commission"/>${match.getCommission()}%
                        </h1>
                    </div>
                </div>
            </div>
            <input class="money-input" type="text"
                   placeholder="<fmt:message key="match.enter.money"/>"
                   name="money" required>
            <div class="bet-buttons">
                <a href="${pageContext.request.contextPath}/controller?command=bet&on=1">
                    <div class="first-button button">
                        <div class="bet-on">
                            <h1>
                                <fmt:message key="match.bet.on"/>${match.getFirstTeam()}
                            </h1>
                        </div>
                        <div class="coefficient-value">
                            <h1>
                                <fmt:message key="match.coefficient"/>${match.getFirstCoefficient()}
                            </h1>
                        </div>
                    </div>
                </a>
                <a href="${pageContext.request.contextPath}/controller?command=bet&on=2">
                    <div class="second-button button">
                        <div class="bet-on">
                            <h1>
                                <fmt:message key="match.bet.on"/>${match.getSecondTeam()}
                            </h1>
                        </div>
                        <div class="coefficient-value">
                            <h1>
                                <fmt:message key="match.coefficient"/>${match.getSecondCoefficient()}
                            </h1>
                        </div>
                    </div>
                </a>
            </div>
        </div>
    </div>
</div>
