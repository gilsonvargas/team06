/**
 * @Author cquezada
 */

/**
* Funcion para enviar comando al rover
*/  
function enviarFlujo(comando,rover){
    var data = '{"commands":"'+comando+'","roverName":"devPool"}';
    alert(data);
    $.ajax({
        type: "POST",
        url:"http://localhost:8080/RoverControlServer/service/control/command/run",
        data : data,
        contentType:"application/json",
        success: function(response){
            if(!response.errorTrace){
                //alert(response.errorTrace);
            }
                        
        },
        dataType: "json"
    });
}
/**
* Funcion para traer los rovers conectados
*/
function getRovers(){
    $("#robots").html('<li class="nav-header">R O B O T S</li>');
    $.ajax({
        type: "POST",
        url:"http://localhost:8080/RoverControlServer/service/control/command/rovers",
        contentType:"application/json",
        success: function(response){
            $.each(response.rovers, function(index, value) {
                $("#robots").append('<li><a href="#">'+value+'</a></li>');
            });
        },
        dataType: "json"
    });
}

