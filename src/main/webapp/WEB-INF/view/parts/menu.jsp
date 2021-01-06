<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sc" uri="custom-tags" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>

<div class="menu">
    <sc:access role="ADMIN">
        <a href="${pageContext.request.contextPath}/controller?command=matches">
            <div class="matches">
                <div class="matches-image"></div>
                <h1 class="matches-text">
                    <fmt:message key="menu.matches"/>
                </h1>
            </div>
        </a>
        <a href="${pageContext.request.contextPath}/controller?command=users">
            <div class="users">
                <div class="users-image"></div>
                <h1 class="users-text">
                    <fmt:message key="menu.users"/>
                </h1>
            </div>
        </a>
    </sc:access>
</div>
