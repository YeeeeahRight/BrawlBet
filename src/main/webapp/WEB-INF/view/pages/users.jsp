<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>

<html lang="<fmt:message key="html.lang"/>">
<head>
    <meta charset="UTF-8">
    <link href="${pageContext.request.contextPath}/static/styles/general-styles.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/static/styles/users.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/static/styles/pagination.css" rel="stylesheet" type="text/css">
    <title>Brawl bet!</title>
</head>
<body>
<div class="container">
    <jsp:include page="../parts/header.jsp"/>
    <div class="body">
        <jsp:include page="../parts/menu.jsp"/>
        <div class="main-content">
            <div class="main-header">
                <div class="users-logo-text">
                    <h1>
                        <fmt:message key="user.users"/>
                    </h1>
                </div>
            </div>
            <div class="main-body">
                <c:forEach items="${users}" var="betMatchDto" varStatus="counter">
                    <div class="user">
                        <div class="unban-button">
                            <a href="${pageContext.request.contextPath}/controller?command=unblock-user&id=${betMatchDto.getId()}">
                                <div class="unban-image"></div>
                            </a>
                        </div>
                        <div class="user-description">
                            <div class="user-header">
                                <h1 class="username">${betMatchDto.getName()}</h1>
                            </div>
                            <div class="user-content">
                                <h1 class="status-text">
                                    <fmt:message key="user.status"/>:
                                </h1>
                                <h1 class="status-data">
                                    <c:choose>
                                        <c:when test="${betMatchDto.isBlocked()}">
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
                                        <c:when test="${betMatchDto.getRole().toString().equals('BOOKMAKER')}">
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
                                <h1 class="balance-data">
                                    <fmt:formatNumber type="number" maxFractionDigits="2"
                                                      pattern="0.0" value="${betMatchDto.getBalance()}"/>
                                </h1>
                            </div>
                        </div>
                        <div class="ban-button">
                            <a href="${pageContext.request.contextPath}/controller?command=block-user&id=${betMatchDto.getId()}">
                                <div class="ban-image"></div>
                            </a>
                        </div>
                    </div>
                </c:forEach>
                <div class="pagination">
                    <a class="first-arrow" id="${currentPage > 1}"
                       href="${pageContext.request.contextPath}/controller?command=users&page=${currentPage - 1}"></a>
                    <form method="POST" action="${pageContext.request.contextPath}/controller?command=pagination">
                        <input type="number" id="page" value="${currentPage}"
                               name="page" min="1" max="${maxPage}" required>
                    </form>
                    <h1>
                        <fmt:message key="pagination.of"/> ${maxPage}
                    </h1>
                    <a class="second-arrow" id="${currentPage < maxPage}"
                       href="${pageContext.request.contextPath}/controller?command=users&page=${currentPage + 1}"></a>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>