<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
                <c:set var="status" value="<%=response.getStatus()%>"/>
                <h1>
                    <fmt:message key="error.${status}.message"/>
                </h1>
                <h1>------------>${status} <fmt:message key="error.error"/><------------</h1>
            </div>
        </div>
    </div>
</div>