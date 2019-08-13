<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<c:if test="${locale == 1}"><fmt:setLocale value="en_US" scope="session"/></c:if>
<c:if test="${locale == 2}"><fmt:setLocale value="uk_UA" scope="session"/></c:if>

<fmt:setBundle basename="pagecontent" var="content"/>


<html>
<head>
    <c:set var="currentPage" value="path.page.userOrdersHistory" scope="request"/>
    <title> <fmt:message key="userOrdersHistoryPage.title" bundle="${content}"/> </title>
</head>

<body>
<header class="user-header">
    <c:import url="headers/user_header.jsp"/>
    <div class="user-links">
        <a class="references" href="userPage.jsp"><fmt:message key="reference.home" bundle="${content}"/> </a>
        <a class="references" href="userComments.jsp"><fmt:message key="reference.comments" bundle="${content}"/> </a>
        <a class="references" href="createOrder.jsp"><fmt:message key="reference.createOrder" bundle="${content}"/> </a>
    </div>
</header>
<div class="all-users-orders">
    <%--<c:choose>--%>
    <%--<c:when test="${mapUsersOrders != null}">--%>
    <table class="users-orders">
        <th align="center"> <fmt:message key="aboutOrder.serviceName" bundle="${content}"/> </th>
        <th align="center"> <fmt:message key="aboutOrder.status" bundle="${content}"/> </th>
        <c:forEach var="order" items="${mapUsersOrders}">
            <tr>
                <td align="center">
                    <c:out value="${order.key}"/>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${order.value.status=='NEW'}">
                            <fmt:message key="aboutOrder.statusInfoNew" bundle="${content}"/>
                        </c:when>
                        <c:when test="${order.value.status=='REFUSE'}">
                            <fmt:message key="aboutOrder.statusInfoRefuse" bundle="${content}"/>
                            <c:out value="${order.value.rejectionReason}"/>
                        </c:when>
                        <c:when test="${order.value.status=='CONFIRM'}">
                            <fmt:message key="aboutOrder.statusInfoConfirm" bundle="${content}"/>
                            <c:out value="${order.value.reviewDate}"/>
                        </c:when>
                        <c:when test="${order.value.status=='IN_WORK'}">
                            <fmt:message key="aboutOrder.statusInfoInWork" bundle="${content}"/>
                            <c:out value="${order.value.startTime}"/>
                        </c:when>
                        <c:when test="${order.value.status=='DONE'}">
                            <fmt:message key="aboutOrder.statusInfoDone" bundle="${content}"/>
                            <c:out value="${order.value.repairFinish}"/>
                        </c:when>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
    </table>
    <%--</c:when>--%>
        <%--<c:otherwise>--%>
            <%--<fmt:message key="aboutOrder.dontHaveOrders" bundle="${content}"/>--%>
        <%--</c:otherwise>--%>

    <c:if test="${mapUsersOrders != null}">
    <form name="commentForm" method="get" action="controller">
        <div class="comment_form_element">
            <p class="input_comment_text">
                <fmt:message key="userOrdersHistoryPage.leaveComment" bundle="${content}"/>
            </p>
            <select class="select-option" size="1" name="currentOrderId">
                <c:forEach var="order" items="${mapUsersOrders}">
                    <option value="${order.value.id}">
                        <c:out value="${order.key}"/>
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="comment_form_element">
            <input class="comment-text" type="text" name="commentText">
        </div>

        <div class="comment_form_element">
            <button class="button" type="submit" name="command" value="AddComment">
                <fmt:message key="userOrdersHistoryPage.button.submitComment" bundle="${content}"/>
            </button>
        </div>
        </c:if>
    </form>


</div>
</body>

</html>