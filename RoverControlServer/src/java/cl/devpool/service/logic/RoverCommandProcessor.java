package cl.devpool.service.logic;

import cl.devpool.pojo.RoverControlData;
import cl.devpool.service.websocket.RoverController;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.Map;

/**
 *
 * @author CyberCastle
 */
public class RoverCommandProcessor {

    public RoverCommandProcessor() {
    }

    public void runCommand(RoverControlData commands) {
        Thread runcmd = new Thread(new RunCommandThread(commands));
        runcmd.start();
    }
    

    private static class RunCommandThread implements Runnable {

        private RoverControlData controlData;
        private Map<String, RoverController.RoverConnection> roverConnections;

        public RunCommandThread(RoverControlData controlData) {
            this.controlData = controlData;
            this.roverConnections = RoverController.getRoverConnections();
        }

        @Override
        public void run() {
            try {
                String _roverName = this.controlData.getRoverName();
                RoverController.RoverConnection roverCon = this.getRoverConnection(_roverName);
                if (roverCon != null) {
                    //Aquí enviamos las instrucciones al robot

                    roverCon.getWsOutbound().writeTextMessage(CharBuffer.wrap("Ejecutando el comando: " + this.controlData.getCommands()));

                }
            } catch (IOException e) {
                e.printStackTrace(System.err);
            }
        }

        private RoverController.RoverConnection getRoverConnection(String roverName) {
            for (RoverController.RoverConnection roverCon : this.roverConnections.values()) {
                if (roverName.equals(roverCon.getRoverName())) {
                    return roverCon;
                }
            }
            return null;
        }
    }
}
