<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<c:if test="${locale == 1}"><fmt:setLocale value="en_US" scope="session"/></c:if>
<c:if test="${locale == 2}"><fmt:setLocale value="uk_UA" scope="session"/></c:if>

<fmt:setBundle basename="pagecontent" var="content"/>

<html>
    <head>
        <c:set var="currentPage" value="path.page.index" scope="request"/>
        <title> <fmt:message key="index.title" bundle="${content}"/> </title>
    </head>
<body>
    <header class="header">
            <c:import url="headers/header.jsp"/>
    </header>
<div class="index-hello">
    <fmt:message key="index.hello.massage" bundle="${content}"/>
    <a class="references" href="login.jsp"><fmt:message key="index.references.login" bundle="${content}"/></a>
    <a class="references" href="registration.jsp"><fmt:message key="index.references.registration" bundle="${content}"/></a>
</div>





</body>
</html>
