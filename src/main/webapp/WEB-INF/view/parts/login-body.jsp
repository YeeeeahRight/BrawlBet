<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>

<div class="body">
    <jsp:include page="../parts/menu.jsp"/>
    <div class="main-content">
        <div class="main-header">
            <div class = "log-in-text">
                <fmt:message key="login.log.in"/>
            </div>
        </div>
        <div class="main-body">
            <div class="form">
                <a href="${pageContext.request.contextPath}/controller?command=sign-up-page">
                    <div class="sign-up-ref">
                        <span class="sign-click-text">
                            <fmt:message key="login.sign.up.advice"/>
                        </span>
                        <span class="sign-up-text">
                             <fmt:message key="login.sign.up.upper"/>
                        </span>
                    </div>
                </a>
                <div class="login-form">
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
                    </div>
                    <form method = "POST" action="${pageContext.request.contextPath}/controller?command=login">
                        <div class="input-form">
                            <input type="text"
                                   placeholder="<fmt:message key="login.username.advice"/>"
                                   name="login" required>
                            <input type="password"
                                   placeholder="<fmt:message key="login.password.advice"/>"
                                   name="password" required>
                            <button type="submit">
                                <fmt:message key="login.submit"/>
                            </button>
                        </div>
                    </form>
                </div>
            </div>
            <h1 class="error-message">
                <fmt:message key="login.error.${errorMessage}"/>
            </h1>
        </div>
    </div>
</div>
