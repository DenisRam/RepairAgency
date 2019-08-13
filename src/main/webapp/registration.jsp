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
        <c:set var="currentPage" value="path.page.registration" scope="request"/>
        <title> <fmt:message key="registration.title" bundle="${content}"/> </title>
    </head>

    <body>

    <header class="header">
        <c:import url="headers/header.jsp"/>
    </header>

    <form class="registration" action="controller" method="post">
        <div>
            <table>
                <tbody>
                <tr>
                    <th align="left"><fmt:message key="registration.user.name" bundle="${content}"/></th>
                    <td><input class="inputName" type="text" name="userName">
                        <span class="error">
                            <c:if test="${incorrectName}">
                                <fmt:message key="validation.name" bundle="${message}"/>
                            </c:if>
                        </span>
                    </td>
                </tr>

                <tr>
                    <th align="left"><fmt:message key="registration.user.surname" bundle="${content}"/> </th>
                    <td><input class="inputSurname" type="text" name="userSurname">
                        <span class="error">
                            <c:if test="${incorrectSurName}">
                                <fmt:message key="validation.surname" bundle="${message}"/>
                            </c:if>
                                </span>
                    </td>
                </tr>

                <tr>
                    <th align="left"><fmt:message key="registration.user.login" bundle="${content}"/> </th>
                    <td><input class="inputLogin" type="text" name="userLogin">
                        <span class="error">
                                    <c:if test="${incorrectLogin}">
                                        <fmt:message key="message.incorrect.login" bundle="${message}"/>
                                    </c:if>
                                    <c:if test="${dublicateAccount}">
                                        <fmt:message key="message.dublicateAccount" bundle="${message}"/>
                                    </c:if>
                                </span>
                    </td>
                </tr>
                <tr>
                    <th align="left"><fmt:message key="registration.user.password" bundle="${content}"/> </th>
                    <td><input class="inputPassword" type="password" name="userPassword">
                    </td>
                </tr>
                </tbody>
            </table>
        </div>

        <div class="buttons">
            <button class="button" type="submit" name="command" value="createUser">
                <fmt:message key="registration.button.create" bundle="${content}"/>
            </button>
            <button class="button" type="submit" name="command" value="cancelCreareUser">
                <fmt:message key="registration.button.cancel" bundle="${content}"/>
            </button>
        </div>
    </form>
    </body>
</html>