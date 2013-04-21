package cl.devpool.pojo;

/**
 *
 * @author CyberCastle
 */
public class RoverControlData {
    private String commands;
    private String roverName;
    private Integer timeOut; 
    private Integer delay;

    public String getCommands() {
        return commands;
    }

    public void setCommands(String commands) {
        this.commands = commands;
    }

    public String getRoverName() {
        return roverName;
    }

    public void setRoverName(String roverName) {
        this.roverName = roverName;
    }

    public Integer getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Integer timeOut) {
        this.timeOut = timeOut;
    }

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }
}
