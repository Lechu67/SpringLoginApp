
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>

  <%--  <springForm:form method="POST" modelAttribute="usercustom" action="signup">
        <table>
            <tr>
                <td>Username:</td>
                <td><springForm:input path="username"/></td>
                <td><springForm:errors path="username" cssClass="error" />
            </tr>
            <tr>
                <td>Password:</td>
                <td><springForm:password path="password"/></td>
                <td><springForm:errors path="password" cssClass="error" />
            </tr>
            <tr>
                <td colspan="3"><button type="submit">Signup</button></td>
            </tr>
        </table>
    </springForm:form>--%>
<c:url value="/signup" var="signupUrl"/>
<form action="${signupUrl}" method="post">
    <p>
        <label for="username">Enter Username</label>
        <input type="text" id="username" name="username"/>
        <form:errors path="username" cssClass="error"/>
    </p>
    <p>
        <label for="password">Enter Password</label>
        <input type="password" id="password" name="password"/>
    </p>
    <button type="submit" class="btn">Signup</button>
</form>
</body>
</html>
