import java.io.*;
import java.net.*;
import java.util.*;
import java.time.Instant;
import org.json.*;

public class LoggingClient {
    private final String host;
    private final int port;
    private final String clientId;
    
    public LoggingClient(String host, int port) {
        this.host = host;
        this.port = port;
        // Generate a random UUID for this client instance
        this.clientId = UUID.randomUUID().toString();
    }
    
    private JSONObject createLogMessage(String level, String message) {
        JSONObject json = new JSONObject();
        json.put("client_id", clientId);
        json.put("timestamp", Instant.now().toString());
        json.put("level", level);
        json.put("message", message);
        return json;
    }
    

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: java LoggingClient <host> <port> <mode>");
            System.out.println("Mode: -m for manual, -a for automated tests");
            return;
        }
        
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        String mode = args[2];
        
        LoggingClient client = new LoggingClient(host, port);
        
        if (mode.equals("-m")) {
            //client.runManualTest();
        } else if (mode.equals("-a")) {
            //client.runAutomatedTests();
        } else {
            System.out.println("Invalid mode. Use -m for manual or -a for automated tests");
        }

        // Test JSON object creation
        JSONObject logMessage = client.createLogMessage("DEBUG", "test message");
        System.out.println(logMessage.toString(4));
    }
}
