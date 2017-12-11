<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<h2>Hello World!</h2>
<br>
Hello ${user}
<c:url value="/logout" var="logoutUrl"/>
<form action=${logoutUrl} method="post">
    <button type="submit" class="btn">Log out</button>
</form>
    <a href="/admin">Admin Page</a>
</body>
</html>
