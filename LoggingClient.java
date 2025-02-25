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
         ZonedDateTime now = ZonedDateTime.now(ZoneId.systemDefault());
        String timestamp = now.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        json.put("timestamp", timestamp);

        json.put("level", level);
        json.put("message", message);
        return json;
    }

    public String sendLog(String level, String message) {
        try (Socket socket = new Socket(host, port)) {
            JSONObject logMessage = createLogMessage(level, message);
            
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            out.println(logMessage.toString());
            
            String response = in.readLine();
            return response;
            
        } catch (IOException e) {
            return "Error: " + e.getMessage();
        }
    }

    public void runManualTest() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Client ID: " + clientId);
        
        while (true) {
            System.out.println("\nEnter log level (DEBUG/INFO/WARN/ERROR) or 'quit' to exit:");
            String level = scanner.nextLine().toUpperCase();
            
            if (level.equals("QUIT")) {
                break;
            }
            
            if (!Arrays.asList("DEBUG", "INFO", "WARN", "ERROR").contains(level)) {
                System.out.println("Invalid log level!");
                continue;
            }
            
            System.out.println("Enter message:");
            String message = scanner.nextLine();
            
            String response = sendLog(level, message);
            System.out.println("Server response: " + response);
        }
        
        scanner.close();
    }

    public void runAutomatedTests() {
        System.out.println("Running automated tests...");
        System.out.println("Client ID: " + clientId);
        
        // Test different log levels
        testLogLevels();
        
        // Test rate limiting
        testRateLimiting();
        
        // Test long messages
        testLongMessages();
        
        System.out.println("Automated tests completed.");
    }
    
    private void testLogLevels() {
        String[] levels = {"DEBUG", "INFO", "WARN", "ERROR"};
        for (String level : levels) {
            String response = sendLog(level, "Test message for " + level + " level");
            System.out.println(level + " test: " + response);
        }
    }
    
    private void testRateLimiting() {
        System.out.println("\nTesting rate limiting...");
        for (int i = 0; i < 20; i++) {
            String response = sendLog("INFO", "Rate limit test message " + i);
            System.out.println("Message " + i + ": " + response);
        }
    }
    
    private void testLongMessages() {
        StringBuilder longMessage = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            longMessage.append("Long message test ");
        }
        String response = sendLog("INFO", longMessage.toString());
        System.out.println("Long message test: " + response);
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
            client.runManualTest();
        } else if (mode.equals("-a")) {
            client.runAutomatedTests();
        } else {
            System.out.println("Invalid mode. Use -m for manual or -a for automated tests");
        }

        // Test JSON object creation
        JSONObject logMessage = client.createLogMessage("DEBUG", "test message");

        // Print the JSON object
        System.out.println(logMessage.toString(4));  // print with indentation
    }
}
