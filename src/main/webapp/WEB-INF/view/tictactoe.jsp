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

        $.ajax({
            type: "GET",
            url: "/tictactoe",
            success: function (currentBoard) {
                $.each(currentBoard, function (i, boardResponse) {
                    $('td[x='+boardResponse.x+'][y='+boardResponse.y+']').append(boardResponse.symbol);
                });
            }
        });
        alert("It's your move");
        $("td").click(function(element){
            console.log($(element.target).attr("x"));
            console.log($(element.target).attr("y"));
            //blokada planszy(flags, attrybut html enabled)
            var moveRequest   = {
                x: $(element.target).attr("x"),
                y: $(element.target).attr("y")
            };
            $.ajax({
                url : "/playerMove",
                type : "POST",
                contentType: "application/json; ; charset=UTF-8", //to co wysylam
                data : JSON.stringify(moveRequest), //to co dostaje
                success : function(data){

                    switch(data.status){
                        case 'TAKEN':
                            alert("Field already taken!");
                            break;
                        case 'WIN':
                            $(element.target).text(data.symbol);
                            alert(data.symbol+" wins");
                            window.location.replace("/")
                            break;
                        case 'DRAW':
                            $(element.target).text(data.symbol);
                            alert("It's a draw !");
                            window.location.replace("/")
                            break;
                        case 'CONTINUE':
                            $(element.target).text(data.symbol);
                            computerMove();
                            break;
                    }
                },
                error : function(){
                    console.log("Response: error");
                    alert("An error occured");
                },
            });
            console.log(moveRequest);
        });
        function computerMove() {

            $.ajax({
                url : "/computerMove",
                type : "POST",
                contentType: "application/json; ; charset=UTF-8",
                success : function(MoveComputerResponse){

                    switch(MoveComputerResponse.status){
                        case 'WIN':
                            $('td[x='+MoveComputerResponse.x+'][y='+MoveComputerResponse.y+']').text(MoveComputerResponse.symbol);
                            alert(MoveComputerResponse.symbol+" wins");
                            window.location.replace("/")
                            break;
                        case 'DRAW':
                            $('td[x='+MoveComputerResponse.x+'][y='+MoveComputerResponse.y+']').text(MoveComputerResponse.symbol);
                            alert("It's a draw !");
                            window.location.replace("/")
                            break;
                        case 'CONTINUE':
                            $('td[x='+MoveComputerResponse.x+'][y='+MoveComputerResponse.y+']').text(MoveComputerResponse.symbol);
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

