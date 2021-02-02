<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>

<html lang="<fmt:message key="html.lang"/>">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${pageContext.request.contextPath}/static/styles/general-styles.css" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/static/styles/add-team.css" rel="stylesheet" type="text/css">
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
                        <fmt:message key="team.add"/>
                    </h1>
                </div>
            </div>
            <div class="main-body">
                <div class="form">
                    <div class="deposit-form">
                        <div class="deposit-text">
                            <h1>
                                <fmt:message key="team.name"/>:
                            </h1>
                        </div>
                        <form method="POST" action="${pageContext.request.contextPath}/controller?command=add-team">
                            <input type="text" maxlength="15"
                                   placeholder="<fmt:message key="team.add.input"/>"
                                   name="name" required>
                            <button type="submit">
                                <fmt:message key="match.add.button"/>
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


