<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>

<div class="body">
    <jsp:include page="../parts/menu.jsp"/>
    <div class="main-content">
        <div class="main-header">
            <div class = "error-text">
                <h1>
                    <fmt:message key="error.header"/>
                </h1>
            </div>
        </div>
        <div class="main-body">
            <div class = "error-message-text">
                <h1>
                    ${errorMessage}
                </h1>
            </div>
        </div>
    </div>
</div>