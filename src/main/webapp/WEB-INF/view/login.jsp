<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<c:url value="/login" var="loginUrl"/>
<form action="${loginUrl}" method="post">
    <c:if test="${param.error != null}">
        <p>
            Invalid username and password.
        </p>
    </c:if>
    <c:if test="${param.logout != null}">
        <p>
            You have been successfully logout
        </p>
    </c:if>
    <p>
        <label for="username">Username</label>
        <input type="text" id="username" name="username"/>
    </p>
    <p>
        <label for="password">Password</label>
        <input type="password" id="password" name="password"/>
    </p>
    <%--        <input type="hidden"
                   name="${_csrf.parameterName}"
                   value="${_csrf.token}"/>--%>
    <button type="submit" class="btn">Log in</button>
</form>
<c:url value="/signup" var="signupUrl"/>
<form action=${signupUrl} method="get">
    <button type="submit" class="btn">Sign in</button>
</form>
</body>
</html>
