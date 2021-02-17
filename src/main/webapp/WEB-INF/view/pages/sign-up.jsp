<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>

<html lang="<fmt:message key="html.lang"/>" id="html">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${pageContext.request.contextPath}/static/styles/general-styles.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/static/styles/sign-up.css" rel="stylesheet" type="text/css">
    <title>Brawl bet!</title>
</head>
<body>
<div class="container">
    <jsp:include page="../parts/header.jsp"/>
    <div class="body">
        <jsp:include page="../parts/menu.jsp"/>
        <div class="main-content">
            <div class="main-header">
                <div class="sign-up-text">
                    <h1>
                        <fmt:message key="login.sign.up.lower"/>
                    </h1>
                </div>
            </div>
            <div class="main-body">
                <div class="sign-up-form">
                    <div class="texts">
                        <div class="username-text">
                            <h1>
                                <fmt:message key="login.username"/>:
                            </h1>
                        </div>
                        <div class="password-text">
                            <h1>
                                <fmt:message key="login.password"/>:
                            </h1>
                        </div>
                        <div class="repeated-password-text">
                            <h1>
                                <fmt:message key="login.password.repeat"/>:
                            </h1>
                        </div>
                    </div>
                    <form method="POST" action="${pageContext.request.contextPath}/controller?command=sign-up">
                        <div class="input-form">
                            <input type="text"
                                   placeholder="<fmt:message key="login.username.advice"/>"
                                   name="login" value="${login}" maxlength="20" required>
                            <input id="password" type="password"
                                   placeholder="<fmt:message key="login.password.advice"/>"
                                   name="password" maxlength="25" required>
                            <input id="repeated-password" type="password"
                                   placeholder="<fmt:message key="login.password.repeat"/>"
                                   name="repeatedPassword" maxlength="25" required>
                            <button id="sign-up-submit" type="submit">
                                <fmt:message key="login.submit"/>
                            </button>
                        </div>
                    </form>
                </div>
                <h1 class="error-message">
                    <fmt:message key="login.error.${errorMessage}"/>
                </h1>
            </div>
        </div>
    </div>
</div>
</body>
</html>

<script src="<c:url value="/static/js/same-password-validator.js"/>"></script>