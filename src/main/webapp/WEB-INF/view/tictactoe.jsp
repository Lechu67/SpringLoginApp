<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script>

$(document).ready(function(){
        $("td").click(function(element){
            console.log($(element.target).attr("x"));
            console.log($(element.target).attr("y"));

            var moveRequest   = {
              x: $(element.target).attr("x"),
              y: $(element.target).attr("y"),
            };


            $.ajax({
                url : "/tictactoe",
                type : "POST",
                contentType: "application/json; ; charset=UTF-8",
                success : function(data){
                    console.log("Response: success, data = " + data);
                    $(element.target).text(data.symbol)

                    if(data.status == 1) {
                        //alert win
                    } else if(data.status == 2) {
                        //
                    } else if (data.status == 3){

                    }

                },
                error : function(){
                    console.log("Response: error");
                    alert("Field already taken !");
                },
                data : JSON.stringify(moveRequest)
            });
            console.log(moveRequest);
        });



   });
</script>

<style>
table, td, th {
    border: 1px solid black;
        height: 100px;
}


table {
    border-collapse: collapse;
    width: 20%;
    height: 100%;
}
</style>
</head>
<body>

<h2>Tic tac toe</h2>

<table>
<c:forEach var = "row" begin = "1" end = "3">
   <tr>
       <c:forEach var = "col" begin = "1" end = "3">
          <td id="test" x = "${col}" y = "${row}"></td>
        </c:forEach>
    </tr>
</c:forEach>
</table>



</body>
</html>

