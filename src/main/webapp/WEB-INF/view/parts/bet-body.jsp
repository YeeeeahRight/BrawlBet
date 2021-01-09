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
                            <sc:date-formatter date="${matchDto.getDate()}"
                                               formatType="${sessionScope.lang}"></sc:date-formatter>
                        </h1>
                    </div>
                </div>
                <div class="match-body">
                    <div class="f-team">
                        <div class="f-team-name">
                            <h1>
                                ${matchDto.getFirstTeam()}
                            </h1>
                        </div>
                        <div class="f-percent-name">
                            <h1>
                                ${matchDto.getFirstPercent()}%
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
                                ${matchDto.getSecondTeam()}
                            </h1>
                        </div>
                        <div class="s-percent-name">
                            <h1>
                                ${matchDto.getSecondPercent()}%
                            </h1>
                        </div>
                    </div>
                </div>
                <div class="match-footer">
                    <div class="match-tournament">
                        <h1>
                            ${matchDto.getTournament()}
                        </h1>
                    </div>
                </div>
                <div class="match-description">
                    <div class="commission">
                        <h1>
                            <fmt:message key="bet.commission"/>${matchDto.getCommission()}%
                        </h1>
                    </div>
                </div>
            </div>
            <c:choose>
                <c:when test="${isMatchFinished}">
                    <div class="match-finished-block">
                        <h1 class="match-finished-text">
                            <fmt:message key="match.finished"/>
                        </h1>
                    </div>
                </c:when>
                <c:otherwise>
                    <sc:access role="USER">
                        <form id="money-input-form" method="POST"
                              action="${pageContext.request.contextPath}/controller?command=bet&id=${matchDto.getId()}">
                            <input id="money" type="number" required
                                   placeholder="<fmt:message key="bet.enter.money"/>"
                                   name="money" min="${minBet}" max="${maxBet}">
                            <input id="on" type="text" name="on" value=""/>
                            <div class="bet-buttons">
                                <button type="submit" onclick="betOnFirst()" class="first-button">
                                    <div class="button">
                                        <div class="bet-on">
                                            <h1>
                                                <fmt:message key="bet.on"/>${matchDto.getFirstTeam()}
                                            </h1>
                                        </div>
                                        <div class="coefficient-value">
                                            <h1>
                                                <fmt:message key="bet.coefficient"/>
                                                <fmt:formatNumber type="number" maxFractionDigits="3"
                                                                  value="${matchDto.getFirstCoefficient()}"/>
                                            </h1>
                                        </div>
                                    </div>
                                </button>
                                <button type="submit" onclick="betOnSecond()" class="second-button">
                                    <div class="button">
                                        <div class="bet-on">
                                            <h1>
                                                <fmt:message key="bet.on"/>${matchDto.getSecondTeam()}
                                            </h1>
                                        </div>
                                        <div class="coefficient-value">
                                            <h1>
                                                <fmt:message key="bet.coefficient"/>
                                                <fmt:formatNumber type="number" maxFractionDigits="3"
                                                                  value="${matchDto.getSecondCoefficient()}"/>
                                            </h1>
                                        </div>
                                    </div>
                                </button>
                            </div>
                        </form>
                    </sc:access>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<script src="<c:url value="/static/js/bet-page.js"/>"></script>