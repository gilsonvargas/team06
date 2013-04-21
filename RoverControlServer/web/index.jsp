<!DOCTYPE html>
<html>
    <head>
        <title></title>
        <script src="js/jquery-1.9.1.min.js"></script>
        <script>
            var data = '{"commands":"iniciar; avanzar; girar90der; avanzar; detener", "roverName":"devPool"}';

            $.ajax({
                type: "POST",
                url: "http://localhost:8080/RoverControlServer/service/control/command/run",
                data: data,
                contentType: "application/json",
                success: function(response) {
                    alert("Data Loaded: " + response.errorTrace);
                },
                dataType: "json"
            });




        </script>
    </head>
    <body>
        <div>Send Command</div>
    </body>
</html>
