<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<c:if test="${locale == 1}"><fmt:setLocale value="en_US" scope="session"/></c:if>
<c:if test="${locale == 2}"><fmt:setLocale value="uk_UA" scope="session"/></c:if>

<fmt:setBundle basename="pagecontent" var="content"/>


<html>
<head>
    <c:set var="currentPage" value="path.page.userComments" scope="request"/>
    <title> <fmt:message key="userCommentsPage.title" bundle="${content}"/> </title>
</head>

<body>
<header class="user-header">
    <c:import url="headers/user_header.jsp"/>
    <div class="user-links">
        <a class="references" href="userPage.jsp"><fmt:message key="reference.home" bundle="${content}"/> </a>
        <a class="references" href="createOrder.jsp"><fmt:message key="reference.createOrder" bundle="${content}"/> </a>
        <a class="references" href="userOrdersHistory.jsp"><fmt:message key="reference.ordersHistory" bundle="${content}"/> </a>
    </div>
</header>

<div class="all-services">
    <h2 class="about-services">
        <fmt:message key="userCommentsPage.comments" bundle="${content}"/>
    </h2>
            <%--<c:choose>--%>
                <%--<c:when test="${mapUsersComments != null}">--%>
                    <table class="comments">
                        <th align="center"> <fmt:message key="aboutComments.serviceName" bundle="${content}"/> </th>
                        <th align="center"> <fmt:message key="aboutComments.commentText" bundle="${content}"/> </th>

                        <c:forEach var="comment" items="${mapUsersComments}">
                        <tr>
                            <td>
                                <c:forEach var="service" items="${servicesList}">
                                    <c:if test="${comment.key == service.id}">
                                        <c:out value="${service.serviceName}"/>
                                    </c:if>
                                </c:forEach>
                            </td>
                            <td>
                                <c:out value="${comment.value.commentText}"/>
                            </td>
                        </tr>
                        </c:forEach>
                    </table>
                <%--</c:when>--%>
            <%--<c:otherwise>--%>
                    <%--<fmt:message key="userComments.dontHaveComments" bundle="${content}"/>--%>
            <%--</c:otherwise>--%>
</div>

</body>

</html>