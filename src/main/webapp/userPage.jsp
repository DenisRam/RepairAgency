<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<c:if test="${locale == 1}"><fmt:setLocale value="en_US" scope="session"/></c:if>
<c:if test="${locale == 2}"><fmt:setLocale value="uk_UA" scope="session"/></c:if>

<fmt:setBundle basename="pagecontent" var="content"/>


<html>
    <head>
        <c:set var="currentPage" value="path.page.userPage" scope="request"/>
        <title> <fmt:message key="userPage.title" bundle="${content}"/> </title>
    </head>

    <body>
    <header class="user-header">
        <c:import url="headers/user_header.jsp"/>
        <div class="user-links">
            <a class="references" href="userComments.jsp"><fmt:message key="reference.comments" bundle="${content}"/> </a>
            <a class="references" href="createOrder.jsp"><fmt:message key="reference.createOrder" bundle="${content}"/> </a>
            <a class="references" href="userOrdersHistory.jsp"><fmt:message key="reference.ordersHistory" bundle="${content}"/> </a>
        </div>
    </header>

    <div class="all-services">
        <h2 class="about-services">
            <fmt:message key="userPage.aboutServices" bundle="${content}"/>
        </h2>
        <table class="services">

            <th align="center"> <fmt:message key="aboutService.name" bundle="${content}"/> </th>
            <th align="center"> <fmt:message key="aboutService.price" bundle="${content}"/> </th>

            <c:forEach var="service" items="${servicesList}">
                <tr>
                    <td align="center">
                        <c:out value="${service.serviceName}"/>
                    </td>
                    <td>
                        <c:out value="${service.price}"/>
                    </td>
                </tr>
            </c:forEach>
        </table>

    </div>






    </body>


</html>
