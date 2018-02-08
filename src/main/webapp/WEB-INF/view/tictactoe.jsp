<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>

<style>
table, td, th {
    border: 1px solid black;
    height: 100px;

}
table {
    border-collapse: collapse;
    width: 20%;
    height: 100%;
    table-layout: fixed;
    text-align: center;
    font-size: xx-large;
}
</style>
</head>
<body>

<h2>Tic tac toe</h2>

<table>
<c:forEach var = "row" begin = "0" end = "2">
   <tr>
       <c:forEach var = "col" begin = "0" end = "2">
          <td x = "${col}" y = "${row}"></td>
        </c:forEach>
    </tr>
</c:forEach>
</table>



</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script>

    $(document).ready(function(){

        getBoard();
        function getBoard() {
            $.ajax({
                type: "GET",
                url: "/tictactoe",
                success: function (boardSymbolsResponse) {
                    $.each(boardSymbolsResponse.symbolsLocations, function (i, boardResponse) {
                        $('td[x=' + boardResponse.x + '][y=' + boardResponse.y + ']').text(boardResponse.symbol);
                    });
                    if (boardSymbolsResponse.symbol == 'X') {
                        alert("User move")
                    } else {
                        alert("Computer move")
                        computerMove()
                    }
                }
            });
        }
            $("td").click(function (element) {
                console.log($(element.target).attr("x"));
                console.log($(element.target).attr("y"));
                var moveRequest = {
                    x: $(element.target).attr("x"),
                    y: $(element.target).attr("y")
                };
                $.ajax({
                    url: "/playerMove",
                    type: "POST",
                    contentType: "application/json; ; charset=UTF-8", //to co wysylam
                    data: JSON.stringify(moveRequest), //to co dostaje
                    success: function (data) {

                        switch (data.status) {
                            case 'TAKEN':
                                alert("Field already taken!");
                                break;
                            case 'WIN':
                                $(element.target).text(data.symbol);
                                alert(data.symbol + " wins");
                                window.location.replace("/")
                                break;
                            case 'DRAW':
                                $(element.target).text(data.symbol);
                                alert("It's a draw !");
                                window.location.replace("/")
                                break;
                            case 'CONTINUE':
                                $(element.target).text(data.symbol);
                                getBoard();
                                break;
                            case 'NOT_YOUR_TURN':
                                alert("Not your turn")
                                console.log("not your turn player");
                                break;
                        }
                    },
                    error: function () {
                        console.log("Response: error");
                        alert("An error occured");
                    },
                });
            });
        function computerMove() {

            $.ajax({
                url : "/computerMove",
                type : "POST",
                contentType: "application/json; ; charset=UTF-8",
                success : function(MoveComputerResponse){

                    var writeSymbolOnBoard = $('td[x='+MoveComputerResponse.x+'][y='+MoveComputerResponse.y+']').text(MoveComputerResponse.symbol);
                    switch(MoveComputerResponse.status){
                        case 'WIN':
                            writeSymbolOnBoard;
                            alert(MoveComputerResponse.symbol+" wins");
                            window.location.replace("/");
                            break;
                        case 'DRAW':
                            writeSymbolOnBoard;
                            alert("It's a draw !");
                            window.location.replace("/");
                            break;
                        case 'CONTINUE':
                            writeSymbolOnBoard;
                            getBoard();
                            break;
                        case 'NOT_YOUR_TURN':
                            alert("Not your turn")
                            console.log("not your turn computer");
                            break;
                    }
                },
                error : function(){
                    console.log("Response: error");
                    alert("An error occured");
                },
            });
        }
    });
</script>

</html>

