<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>

<div class="body">
    <jsp:include page="../parts/menu.jsp"/>
    <div class="main-content">
        <div class="main-header">
            <div class = "deposit-text-head">
                <fmt:message key="menu.deposit"/>
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
                    <form method = "POST" action="${pageContext.request.contextPath}/controller?command=deposit">
                        <input type="number"
                               placeholder="<fmt:message key="user.deposit.input"/>"
                               name="money" min="1" max="100" required>
                        <button type="submit">
                            <fmt:message key="user.get"/>
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
