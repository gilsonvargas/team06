package cl.devpool.service.websocket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.catalina.websocket.WsOutbound;

/**
 *
 * @author CyberCastle
 */
@WebServlet(urlPatterns = "/service/rover")
public class RoverController extends WebSocketServlet {

    private static final long serialVersionUID = -6722834976584459022L;
    private static final Map<String, RoverConnection> roverConnections = new HashMap<String, RoverConnection>();

    public static Map<String, RoverConnection> getRoverConnections() {
        return roverConnections;
    }
    
    @Override
    protected StreamInbound createWebSocketInbound(String subProtocol, HttpServletRequest request) {
        final String _roverSessionId = request.getSession().getId();
        final String _roverName = request.getParameter("roverName");        
        return new RoverConnection(_roverSessionId, _roverName);
    }

    public static class RoverConnection extends MessageInbound {
        private final String roverSessionId;
        private final String roverName; 
        
        public RoverConnection(String roverSessionId, String roverName) {
            this.roverName = roverName;
            this.roverSessionId = roverSessionId;
        }
        
        public String getRoverName() {
            return this.roverName;
        }
        
        @Override
        protected void onOpen(WsOutbound outbound) {
            System.out.println("Conexión abierta");
            roverConnections.put(this.roverSessionId, this);
        }

        @Override
        protected void onClose(int status) {
            System.out.println("Conexión cerrada");
            roverConnections.remove(this.roverSessionId);
        }

        @Override
        protected void onBinaryMessage(ByteBuffer bb) throws IOException {
            throw new UnsupportedOperationException("Binary messages not supported.");
        }

        @Override
        protected void onTextMessage(CharBuffer cb) throws IOException {
            final String user = cb.toString();
            System.out.println("Mensaje recibido: " + user);
            this.getWsOutbound().writeTextMessage(CharBuffer.wrap("Hola rover: " + user));
        }
    }
}
