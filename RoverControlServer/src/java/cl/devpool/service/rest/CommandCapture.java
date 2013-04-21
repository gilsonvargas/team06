package cl.devpool.service.rest;

import cl.devpool.pojo.RoverControlData;
import cl.devpool.service.GenericResponse;
import cl.devpool.service.ResponseRovers;
import cl.devpool.service.logic.RoverCommandProcessor;
import cl.devpool.service.websocket.RoverController;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author CyberCastle
 */
@Path("/command")
public class CommandCapture {

    public Map<String, RoverController.RoverConnection> roverConnections;

    public CommandCapture() {
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/run")
    public GenericResponse execute(RoverControlData controlData) {
        RoverControlData roversResp;
        roversResp = controlData;
        GenericResponse response = new GenericResponse();
        String[] splits = controlData.getCommands().split(";");
        for (String command : splits) {
            try {
                roversResp.setCommands(command);
                
                RoverCommandProcessor cmdProcessor = new RoverCommandProcessor();
                cmdProcessor.runCommand(roversResp);
                System.out.println(roversResp.getCommands());
                Thread.sleep(1000);
            } catch (Exception e) {
                
                response.setError(e);
                response.setErrorCode("01");
                response.setErrorDescription("????");
            }
            System.out.println(command);
        }

        return response;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/rovers")
    public ResponseRovers getRovers() {
        List<String> rovers = new ArrayList<String>();
        ResponseRovers response = new ResponseRovers();
        try {
            this.roverConnections = RoverController.getRoverConnections();
            for (RoverController.RoverConnection roverCon : this.roverConnections.values()) {
                rovers.add(roverCon.getRoverName());
            }
            response.setRovers(rovers);
            response.setCantidad(rovers.size());

        } catch (Exception e) {
        }

        return response;
    }
}
