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

        <p><fmt:message key="header.logo.message" bundle="${content}"/></p>
        <form name="setLocaleEN" method="post" action="controller">
            <input type="hidden" name="command" value="setLocale">
            <c:set var="currentPage" value="${currentPage}" scope="request"/>
            <input type="hidden" name="currentPage" value="${currentPage}">
            <button class="change_locale" type="submit" name="locale" value="1">English</button>
            <button class="change_locale" type="submit" name="locale" value="2">Українська</button>
        </form>


    </div>

</body>
</html>
