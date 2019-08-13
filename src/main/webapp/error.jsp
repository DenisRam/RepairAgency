<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<c:if test="${locale == 0}"><fmt:setLocale value="en_US" scope="session"/></c:if>
<c:if test="${locale == 1}"><fmt:setLocale value="uk_UA" scope="session"/></c:if>

<fmt:setBundle basename="pagecontent" var="content"/>
<fmt:setBundle basename="message" var="message"/>



<html>
    <head>
        <c:set var="currentPage" value="path.page.error" scope="request"/>
        <title><fmt:message key="errorPage.title" bundle="${content}"/> </title>
    </head>

<body>
<div class="errorBody">
    <p class="errorMessage"><fmt:message key="${errorMessage}" bundle="${message}"/> </p>
    <a class="back" href="controller?command=back"><fmt:message key="erroPage.button.back" bundle="${content}"/></a>
</div>
</body>
</html>
