<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>

<div class="body">
    <jsp:include page="menu.jsp"/>
    <div class="main-content">
        <div class="main-header">
            <div class = "users-logo-text">
                <h1>
                    <fmt:message key="user.users"/>
                </h1>
            </div>
        </div>
        <div class="main-body">
            <c:forEach items="${users}" var="user" varStatus="counter">
                <div class="user user-${counter.index + 1}" >
                    <div class="unban-button">
                        <a href="${pageContext.request.contextPath}/controller?command=unblock-user&id=${user.getId()}">
                            <div class="unban-image"></div>
                        </a>
                    </div>
                    <div class="user-description">
                        <div class="user-header">
                            <h1 class="username">${user.getName()}</h1>
                        </div>
                        <div class="user-content">
                            <h1 class="status-text">
                                <fmt:message key="user.status"/>:
                            </h1>
                            <h1 class="status-data">
                                <c:choose>
                                    <c:when test="${user.isBlocked()}">
                                        <fmt:message key="user.blocked"/>
                                    </c:when>
                                    <c:otherwise>
                                        <fmt:message key="user.active"/>
                                    </c:otherwise>
                                </c:choose>
                            </h1>
                            <h1 class="role-text">
                                <fmt:message key="user.role"/>:
                            </h1>
                            <h1 class="role-data">
                                <c:choose>
                                    <c:when test="${user.getRole().equals('BOOKMAKER')}">
                                        <fmt:message key="user.bookmaker"/>
                                    </c:when>
                                    <c:otherwise>
                                        <fmt:message key="user.user"/>
                                    </c:otherwise>
                                </c:choose>
                            </h1>
                            <h1 class="balance-text">
                                <fmt:message key="user.balance"/>:
                            </h1>
                            <h1 class="balance-data">${user.getBalance()}</h1>
                        </div>
                    </div>
                    <div class="ban-button">
                        <a href="${pageContext.request.contextPath}/controller?command=block-user&id=${user.getId()}">
                            <div class="ban-image"></div>
                        </a>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>