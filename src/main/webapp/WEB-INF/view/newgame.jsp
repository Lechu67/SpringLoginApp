<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<h2>Tic tac toe</h2>
<c:url value="/tictactoe" var="tictactoeUrl"/>
<form action="${tictactoeUrl}" method="post">
    <p>
        <label for="symbol">User Symbol:</label>
        <input type="text" id="symbol" name="symbol"/>
        <button type="submit" class="btn">New game</button>
    </p>
</form>
</body>
</html>

