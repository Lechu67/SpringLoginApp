
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>

    <springForm:form method="POST" modelAttribute="usercustom" action="signup">
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
    </springForm:form>
</body>
</html>
