<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<c:if test="${locale == 1}"><fmt:setLocale value="en_US" scope="session"/></c:if>
<c:if test="${locale == 2}"><fmt:setLocale value="uk_UA" scope="session"/></c:if>

<fmt:setBundle basename="pagecontent" var="content"/>
<fmt:setBundle basename="message" var="message"/>

<html>
    <head>
        <c:set var="currentPage" value="path.page.login" scope="request"/>
        <title><fmt:message key="login.title" bundle="${content}"/> </title>

    </head>

    <body class="login-body">
        <header class="header">
            <c:import url="headers/header.jsp"/>
        </header>
        <form class="login-form" action="controller" method="post">

            <div class="login-form-element">
                <p class="input-element-text">
                    <fmt:message key="login.login" bundle="${content}"/>
                </p>
                <input class="input" type="text" name="login" placeholder="login">
            </div>

            <div class="login-form-element">
                <p class="input-element-text">
                    <fmt:message key="login.password" bundle="${content}"/>
                </p>
                <input class="input" type="password" name="password" placeholder="password">
            </div>

             <div class="login-form-element">
                 <button class="button" type="submit" name="command" value="singIn">
                     <fmt:message key="login.button.singIn" bundle="${content}"/>
                 </button>
                 <button class="button"  type="submit" name="command" value="registration">
                     <fmt:message key="login.button.registration" bundle="${content}"/>
                 </button>
            </div>

            <div class="login_errors">
                <c:if test="${errorLoginMessage}">
                    <p><fmt:message key="message.errorLogin" bundle="${message}"/></p>
                </c:if>

            </div>

        </form>

    </body>
</html>