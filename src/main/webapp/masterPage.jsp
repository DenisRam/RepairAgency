<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<c:if test="${locale == 1}"><fmt:setLocale value="en_US" scope="session"/></c:if>
<c:if test="${locale == 2}"><fmt:setLocale value="uk_UA" scope="session"/></c:if>

<fmt:setBundle basename="pagecontent" var="content"/>


<html>
<head>
    <c:set var="currentPage" value="path.page.masterPage" scope="request"/>
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
        <th align="center"> <fmt:message key="aboutService.manager" bundle="${content}"/> </th>
        <th align="center"> <fmt:message key="aboutOrder.status" bundle="${content}"/> </th>
        <th align="center"> <fmt:message key="aboutOrder.date" bundle="${content}"/> </th>

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
                    <c:if test="${order.managerId == user.id}">
                        <c:out value="${user.name}"/>
                        <c:out value="${user.surname}"/>
                    </c:if>
                </c:forEach>
            </td>
            <c:if test="${order.status == 'CONFIRM'}">
                <td>
                    <fmt:message key="masterPage.statusInfoConfirm" bundle="${content}"/>
                    <c:out value="${order.reviewDate}"/>
                </td>
                <td>
                        <c:out value="${order.reviewDate}"/>
                </td>
                <td>
                        <form name="repairStartService" action="controller" method="post">
                        <input type="hidden" name="command" value="startRepair">
                        <button class="table_button" name="currentOrder" value="${order.id}"
                        type="submit"><fmt:message key="startRepair" bundle="${content}"/></button>
                        </form>
                </td>
            </c:if>
            <c:if test="${order.status == 'IN_WORK'}">
            <td>
                    <fmt:message key="masterPage.statusInfoInWork" bundle="${content}"/>
                    <c:out value="${order.repairStartTime}"/>
            </td>
            <td>
                    <c:out value="${order.repairStartTime}"/>
            </td>
            <td>
                <form name="repairStartService" action="controller" method="post">
                        <input type="hidden" name="command" value="finishRepair">
                        <button class="table_button" name="currentOrder" value="${order.id}"
                        type="submit"><fmt:message key="finishRepair" bundle="${content}"/></button>
                </form>
            </td>
            </c:if>
        </c:forEach>
    </table>
</div>
</body>
</html>
