<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<c:if test="${locale == 1}"><fmt:setLocale value="en_US" scope="session"/></c:if>
<c:if test="${locale == 2}"><fmt:setLocale value="uk_UA" scope="session"/></c:if>

<fmt:setBundle basename="pagecontent" var="content"/>


<html>
<head>
    <c:set var="currentPage" value="path.page.managerPage" scope="request"/>
    <title> <fmt:message key="managerPage.title" bundle="${content}"/> </title>
</head>

<body>
<header class="user-header">
    <c:import url="headers/user_header.jsp"/>
</header>

<div class="all-services">
    <h2 class="about-services">
        <fmt:message key="managerPage.orders" bundle="${content}"/>
    </h2>

    <table class="orders">
        <th align="center"> <fmt:message key="aboutService.name" bundle="${content}"/> </th>
        <th align="center"> <fmt:message key="aboutService.user" bundle="${content}"/> </th>
        <c:forEach var="order" items="${orderList}">
            <tr>
                <td align="center">
                    <c:forEach var="service" items="${servicesList}">
                        <c:if test="${order.serviceId == service.id}">
                            <c:out value="${service.serviceName}"/>
                        </c:if>
                    </c:forEach>
                </td>
                <td align="center">
                    <c:forEach var="user" items="${userList}">
                        <c:if test="${order.userId == user.id}">
                            <c:out value="${user.name}"/>
                            <c:out value="${user.surname}"/>
                        </c:if>
                    </c:forEach>
                </td>
                <td align="center">
                    <form name="confirmService" action="controller" method="post">
                        <input type="hidden" name="command" value="confirmOrder">
                        <button class="table_button" name="currentOrder" value="${order.id}"
                                type="submit"><fmt:message key="confirmOrder" bundle="${content}"/></button>
                    </form>
                </td>
                <td align="center">

                        <div class="refuse_order_element">
                            <p class="refuse_order_text">
                                <fmt:message key="managerPage.RefuseReason" bundle="${content}"/>
                            </p>
                        </div>
                    <form name="refuse-order-message" method="post" action="controller">
                        <div class="refuse_order_element">
                            <input class="refuse-text" type="text" name="refuseReason">
                        </div>


                        <div class="refuse_order_element">
                            <input type="hidden" name="command" value="refuseOrder">
                            <button class="table_button" name="currentOrder" value="${order.id}"
                                    type="submit"><fmt:message key="managerPage.Refuse" bundle="${content}"/></button>
                        </div>
                    </form>
                </td>


        </c:forEach>
    </table>
</div>
</body>
</html>
