<<<<<<< HEAD
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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
        final int MAX_MESSAGE_LENGTH = 3090;
    
    if (message.length() > MAX_MESSAGE_LENGTH) {
        String truncationNotice = "[MESSAGE TRUNCATED; ORIGINAL LENGTH: " + message.length() + "]";
        int truncatedLength = MAX_MESSAGE_LENGTH - truncationNotice.length();
        message = message.substring(0, truncatedLength) + truncationNotice;
    }

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
        for (int i = 0; i < 2000; i++) {
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
    }
}
=======
//implementing header files and json files for the code
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.json.*;

// creating logging class 
public class LoggingClient {
    private final String host;
    private final int port;
    private final String clientId;
    
    /*LoggingClient: this function creates id for the client in order to get the identification
     * parameter: host: it takes hosts ip address
     *          : port: it takes the host port on which service is running 
     * retun: none
     */
    public LoggingClient(String host, int port) {
        this.host = host;
        this.port = port;
        // Generate a random UUID for this client instance
        this.clientId = UUID.randomUUID().toString();
    }

    /*createLogMessage: this function creates json object and it creates desire json format for the serilisation 
     * parameter: level: it has desired levels for the logging servise 
     *          : message: it creates message 
     * retun: json object
     */
    private JSONObject createLogMessage(String level, String message) {
        JSONObject json = new JSONObject();
        final int MAX_MESSAGE_LENGTH = 3090;
    
    if (message.length() > MAX_MESSAGE_LENGTH) {
        String truncationNotice = "[MESSAGE TRUNCATED; ORIGINAL LENGTH: " + message.length() + "]";
        int truncatedLength = MAX_MESSAGE_LENGTH - truncationNotice.length();
        message = message.substring(0, truncatedLength) + truncationNotice;
    }

        json.put("client_id", clientId);
        
        ZonedDateTime now = ZonedDateTime.now(ZoneId.systemDefault());
        String timestamp = now.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        json.put("timestamp", timestamp);

        json.put("level", level);

        json.put("message", message);
        return json;
    }
    
    /*createLogMessage: this function send the message created by user to server  
     * parameter: level: it has desired levels for the logging servise 
     *          : message: it creates message 
     * retun: none
     */
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
    
    /*runManualTest: this function allows user to manually create the logs and send to the service 
     * parameter: none 
     * retun: none
     */
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
    

     /*runAutomatedTests: this function runs automatic pre set operation in order to check the service and logging client  
     * parameter: none 
     * retun: none
     */
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

    /*runManualTest: this function allows user to choose the level of message they want to send   
     * parameter: none 
     * retun: none
     */
    private void testLogLevels() {
        String[] levels = {"DEBUG", "INFO", "WARN", "ERROR"};
        for (String level : levels) {
            String response = sendLog(level, "Test message for " + level + " level");
            System.out.println(level + " test: " + response);
        }
    }
    
    /*testRateLimiting: this function restrict the limit of mesaasge if clients are semding multiple message at the same time   
     * parameter: none 
     * retun: none
     */
    private void testRateLimiting() {
        System.out.println("\nTesting rate limiting...");
        for (int i = 0; i < 20; i++) {
            String response = sendLog("INFO", "Rate limit test message " + i);
            System.out.println("Message " + i + ": " + response);
        }
    }
    
    /*testLongMessages: this function is specifically design for testing very long messages that wether it passes or not    
     * parameter: none 
     * retun: none
     */
    private void testLongMessages() {
        StringBuilder longMessage = new StringBuilder();
        for (int i = 0; i < 2000; i++) {
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
        //storing arguments
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        String mode = args[2];
        
        // calling funcrion to connect with the server 
        LoggingClient client = new LoggingClient(host, port);

        // user can select the mode from here that if it is manual or automatictests 
        
        if (mode.equals("-m")) {
            client.runManualTest();
        } else if (mode.equals("-a")) {
            client.runAutomatedTests();
        } else {
            System.out.println("Invalid mode. Use -m for manual or -a for automated tests");
        }
    }
}
>>>>>>> 603f81f (Save local changes before merge)
