<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>

<html lang="<fmt:message key="html.lang"/>">
<head>
    <meta charset="UTF-8">
    <link href="${pageContext.request.contextPath}/static/styles/general-styles.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/static/styles/header.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/static/styles/deposit-body.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/static/styles/menu.css" rel="stylesheet" type="text/css">
    <title>Brawl bet!</title>
</head>
<body>
<div class="container">
    <jsp:include page="../parts/header.jsp"/>
    <div class="body">
        <jsp:include page="../parts/menu.jsp"/>
        <div class="main-content">
            <div class="main-header">
                <div class="deposit-text-head">
                    <h1>
                        <fmt:message key="menu.deposit"/>
                    </h1>
                </div>
            </div>
            <div class="main-body">
                <div class="form">
                    <div class="deposit-form">
                        <div class="deposit-text">
                            <h1>
                                <fmt:message key="user.amount.text"/>:
                            </h1>
                        </div>
                        <form method="POST" action="${pageContext.request.contextPath}/controller?command=deposit">
                            <input type="number" step="0.01"
                                   placeholder="<fmt:message key="user.deposit.input"/>"
                                   name="money" min="0.1" max="100.00" required>
                            <button type="submit">
                                <fmt:message key="user.get"/>
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>


