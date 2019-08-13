<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<c:if test="${locale == 1}"><fmt:setLocale value="en_US" scope="session"/></c:if>
<c:if test="${locale == 2}"><fmt:setLocale value="uk_UA" scope="session"/></c:if>

<fmt:setBundle basename="pagecontent" var="content"/>


<html>
<head>
    <c:set var="currentPage" value="path.page.createOrder" scope="request"/>
    <title> <fmt:message key="createOrder.title" bundle="${content}"/> </title>
</head>

<body>
<header class="user-header">
    <c:import url="headers/user_header.jsp"/>
    <div class="user-links">
        <a class="references" href="userPage.jsp"><fmt:message key="reference.home" bundle="${content}"/> </a>
        <a class="references" href="userComments.jsp"><fmt:message key="reference.comments" bundle="${content}"/> </a>
        <a class="references" href="userOrdersHistory.jsp"><fmt:message key="reference.ordersHistory" bundle="${content}"/> </a>
    </div>
</header>
<div class="create-order">
    <form name="create-order" method="get" action="controller">
        <div class="order_form_element">
            <p class="input_comment_text">
                <fmt:message key="userOrder.chooseService" bundle="${content}"/>
            </p>
            <select class="select-option" size="1" name="currentServiceId">
                <c:forEach var="service" items="${servicesList}">
                    <option value="${service.id}">
                        <c:out value="${service.serviceName}"/>
                    </option>
                </c:forEach>
            </select>
        </div>
        <div class="order_form_element">
            <button class="button" type="submit" name="command" value="createOrder">
                <fmt:message key="createoOrderCreate" bundle="${content}"/>
            </button>
        </div>

    </form>
</div>



</body>

</html>
