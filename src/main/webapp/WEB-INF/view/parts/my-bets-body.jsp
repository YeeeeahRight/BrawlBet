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
            <div class="my-bets-image-logo"></div>
            <div class="my-bets-logo-text">
                <h1>
                    <fmt:message key="menu.my.bets"/>
                </h1>
            </div>
        </div>
        <div class="main-body">
            <c:forEach items="${betMatchDtoList}" var="betMatchDto" varStatus="counter">
                <div class="bet bet-${counter.index + 1}">
                    <div class="bet-info">
                        <h1 class="bet-on-text">
                            <fmt:message key="bet.on"/>
                        </h1>
                        <h1 class="bet-on-team ${betMatchDto.getTeamOnBet().equals(betMatchDto.getWinner())}">
                                ${betMatchDto.getTeamOnBet()}:
                        </h1>
                        <h1 class="bet-on-money">
                            <fmt:formatNumber type="number" maxFractionDigits="2"
                                              value="${betMatchDto.getMoneyBet()}"/>
                        </h1>
                    </div>
                    <a href="${pageContext.request.contextPath}/controller?command=match-page&id=${betMatchDto.getId()}">
                        <div class="bet-description">
                            <div class="bet-header">
                                <h1 class="date-text">
                                    <sc:date-formatter date="${betMatchDto.getDate()}"
                                                       formatType="${sessionScope.lang}"></sc:date-formatter>
                                </h1>
                                <h1 class="tournament-text">
                                        ${betMatchDto.getTournament()}
                                </h1>
                            </div>
                            <div class="bet-content">
                                <div class="match-f-team ${betMatchDto.getFirstTeam().equals(betMatchDto.getWinner())}">
                                    <h1>
                                            ${betMatchDto.getFirstTeam()}
                                    </h1>
                                </div>
                                <div class="match-f-percent">
                                    <h1>
                                            ${betMatchDto.getFirstPercent()}%
                                    </h1>
                                </div>
                                <div class="match-versus-icon">
                                    <h1>
                                        -
                                    </h1>
                                </div>
                                <div class="match-s-percent">
                                    <h1>
                                            ${betMatchDto.getSecondPercent()}%
                                    </h1>
                                </div>
                                <div class="match-s-team ${betMatchDto.getSecondTeam().equals(betMatchDto.getWinner())}">
                                    <h1>
                                            ${betMatchDto.getSecondTeam()}
                                    </h1>
                                </div>
                            </div>
                        </div>
                    </a>
                    <div class="gain-info">
                        <h1 class="gain-text">
                            <fmt:message key="bet.money.received"/>
                        </h1>
                        <h1 class="gain-money">
                            <fmt:formatNumber type="number" maxFractionDigits="2"
                                              value="${betMatchDto.getMoneyReceived()}"/>
                        </h1>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>