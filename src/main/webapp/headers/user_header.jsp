<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setBundle basename="pagecontent" var="content"/>

<html>
<head>

    <style>
        <%@include file='./../css/header.css'%>
    </style>

</head>
<body>
<div class="header-panel">
    <h1 class="logo"> <fmt:message key="head.logo.user.message" bundle="${content}"/>
        <c:out value="${currentUser.name}"/>
        <c:out value="${currentUser.surname}"/>
    </h1>
    <form name="setLocaleEN" method="post" action="controller">
        <input type="hidden" name="command" value="setLocale">
        <c:set var="currentPage" value="${currentPage}" scope="request"/>
        <input type="hidden" name="currentPage" value="${currentPage}">
        <button class="change_locale" type="submit" name="locale" value="1">English</button>
        <button class="change_locale" type="submit" name="locale" value="2">Українська</button>
    </form>

    <form name="logout" method="post" action="logout">
        <input type="hidden" name="command" value="logout">
        <button class="logout" type="submit">
            <fmt:message key="header.button.logout" bundle="${content}"/>
        </button>
    </form>


</div>


</body>
</html>
