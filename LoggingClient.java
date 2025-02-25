Kisu
kisu14

Xyanon — 2/23/25, 12:32 AM
ok, you'd better; I think tian has high expectations 😭
you should see our last game jam game. bcuz we had a dedicated artist (like she was in an art program and all)
Kisu — 2/23/25, 12:32 AM
ohh noo
ohhh
Xyanon — 2/23/25, 12:32 AM
actually where is the last game jam game
hold on
Xyanon — 2/23/25, 12:43 AM
https://www.youtube.com/watch?v=XHLhLpMyPhU
YouTube
Tian Yang
Ghost Pop - White Flint Game Studio
Image
Kisu — 2/23/25, 12:46 AM
ohhh nice
Xyanon — 2/23/25, 3:42 PM
ok here's the plan. I'll plan the git commits by tonight, and you can space them out tomorrow
I'll put "wait minimum x time" for each. we also might have to synchronize some so what time are you free tmrw?
we should submit tmrw night
Xyanon — 2/23/25, 4:25 PM
also for game jam the artist is back so you don't have to do the art anymore
Kisu — 2/23/25, 4:27 PM
niceeeeee
Xyanon — 2/23/25, 9:35 PM
btw dont forget to come at 9am tmrw
Kisu — 2/23/25, 9:49 PM
sure I'll be there
Xyanon — 2/23/25, 10:39 PM
oh btw we might end up really with only a 3-4 person team
we will probably not merge
you may actually have to do the art again lol
Kisu — 2/23/25, 11:33 PM
no problem then
Xyanon — Yesterday at 12:08 AM
art assets
food:
prey animal(s): boar
if time allows, also goat and giant-mole
adventurers:
knight, mage, archer
attack animations for each
if time allows, idle animation for each
snake: 
head, body (plus corner segment), tail
armoured versions of body and tail
aura around the snake (to signify magic immunity)
dungeon map:
floor
walls
extra decor
here's the plan
do you think this is doable or do you want to try to recruit an artist
Kisu — Yesterday at 1:02 AM
see I can try I cannot garuntee,  we try recruit an artist if it works for less money
Xyanon — Yesterday at 1:06 AM
what? wdym money
none of us are being paid for game jam???
nor paying for game jam
there's no money involved I'm confused
Kisu — Yesterday at 1:09 AM
ohh
you are saying to recruit an artist
so how does that works
how can we recruit an artist ?
Xyanon — Yesterday at 1:10 AM
?? we ask in looking for group channel in the game jam discord
you joined the server right?
Kisu — Yesterday at 1:10 AM
yess
ohk
we can do that
Xyanon — Yesterday at 1:11 AM
it's very unlikely tho cuz most ppl that join are developers
and there's very few ppl left without a team
Kisu — Yesterday at 1:12 AM
let's at least try asking then
Xyanon — Yesterday at 1:12 AM
we can start with only knight adventurer and only add mage and archer if there's time
we can keep it simple at first and prioritize
we might also be able to get pre existing sprites for the adventurer so maybe just the prey animal, dungeon map, and snake should be the focus
Kisu — Yesterday at 1:16 AM
i can do that
i will give my best
wait what is our team ?
Xyanon — Yesterday at 1:17 AM
might just be 3 ppl really
Kisu — Yesterday at 1:17 AM
can you guys merge
with white flint
if I was not there?
Xyanon — Yesterday at 1:18 AM
probably bhawanjeet would also have to not be there
tian said he only wants 4-5 ppl team at most
Kisu — Yesterday at 1:18 AM
ohh
no worries we will do amazing
Xyanon — Yesterday at 1:19 AM
but I recruited you guys so I can't just leave you to a 2 person group ;w;
Kisu — Yesterday at 1:19 AM
understandable 
Xyanon — Yesterday at 4:55 AM
hmmm I just realized I don't have your finished java client code. this is an incredibly poor oversight on my part
Kisu — Yesterday at 7:01 AM
ohh I will send it just now
Xyanon — Yesterday at 7:02 AM
please do
i havent slept :')
i have to leave pretty soon so ig I just won't sleep
actually not soon but i have to get ready to leave and stuff ig
Xyanon — Yesterday at 7:23 AM
?
Xyanon — Yesterday at 9:21 AM
don't forget to sign in btw
http://simp.ly/p/Px2X48
Kisu — Yesterday at 10:48 AM
i still have to make comments inside the code 
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.time.Instant;
import java.time.ZoneId;
Expand
LoggingClient.java
6 KB
Xyanon — Yesterday at 11:48 AM
client
v0: 
implemented logging client class; main structure and command line args; tested json object

v1: +1h
added sendLog function and runManualTest

v2: +2h
added runAutomatedTests and automated test functions

v3: (+30min from adding \n in server)
fixed timezone issue in timestamp

v4: +1h (sync with adding buffer size constant in server)
added message truncation for long messages to fit 4096 buffer size

v5: +20min
fixed message truncation length
import java.io.*;
import java.net.*;
import java.util.*;
import java.time.Instant;
import org.json.*;
Expand
client-v0.java
2 KB
import java.io.*;
import java.net.*;
import java.util.*;
import java.time.Instant;
import org.json.*;
Expand
client-v1.java
4 KB
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
... (40 lines left)
Collapse
client-v2.java
5 KB
Xyanon — Yesterday at 8:21 PM
hello there!
Kisu — Yesterday at 8:24 PM
How are you?
Xyanon — Yesterday at 8:24 PM
im a bit behind on my commits, but we should be able to start around 2130
here's the summary of our discord history:

started testing at around 21:13
at around 22:16, figured out changing the python code (in handle_client, add the \n stuff in 3 lines)

at around 22:47, remembered the timezone issue. fix that part of client.
also we were debugging the long messages issue.
around 23:31, figured out the buffer size. 
we very quickly tested and realized long msg of x200 is ok, but then implemented the message truncation instead. also update server to add the buffer size constant
at 23:35, update client to the message truncation version.
at 23:56, realized we miscalculated the json byte size. update the message truncation again.
 
Kisu — Yesterday at 8:25 PM
got it
see if you are behind the comit
let's do one thing
you can comit your remaining comity until 12
Xyanon — Yesterday at 8:26 PM
wdym until 12
Kisu — Yesterday at 8:26 PM
and we will do our sync comit after 12
bcs we just cannot do in 20 mins
Xyanon — Yesterday at 8:26 PM
no no we can start syncing at real time, 22:40
it's 20:26 right now and I just did a commit
and the last commit is just a small function
so we can just wait 1-2h
Kisu — Yesterday at 8:27 PM
ohk but the thing is that I am  at work right now so I might be not able to do at 22 40
it's just 2 hours
Xyanon — Yesterday at 8:27 PM
oh you're at work 😭 i thought you said you'd be free in the evening
that's fine I mean i'll probably be up til like 5am
Kisu — Yesterday at 8:28 PM
so sorry
about that
Xyanon — Yesterday at 8:29 PM
alr I'll try to work on game jam stuff in the meantime!
Kisu — Yesterday at 8:29 PM
I got un  schedule shift
Kisu — Yesterday at 8:29 PM
suree
Xyanon — Yesterday at 8:29 PM
ohh i see, it's ok
Kisu — Yesterday at 8:29 PM
I have started working on map
Xyanon — Yesterday at 8:29 PM
awesome
Xyanon — Today at 12:18 AM
hi; lmk when we can finish the fake commits
Kisu — Today at 12:23 AM
yeah I just got off
I 'll be home in Luke 15 minutes
Xyanon — Today at 12:23 AM
alr 👍 just msg when ready
Kisu — Today at 12:45 AM
I am ready let's start
Xyanon — Today at 12:45 AM
ok i'll get ready
update handle_client in server
wait 31 minutes
update timezone in client
wait 44 minutes
update server to add buffer size constant
wait 4 minutes
update client to add message truncation function with 4000 msg limit
wait 21 minutes
update client to fix the message truncation with final version
I picked some organic times
Kisu — Today at 12:50 AM
okiee got it
Xyanon — Today at 12:50 AM
I'm gonna set up a series of timers
Kisu — Today at 12:50 AM
okay got it
﻿
dreams are but a fleeting whimsy, lost to time
Xyanon
xyanon
 
technically this is my alt account
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
client-v2.java
5 KB
