import java.io.*;
import java.net.*;

public class ChatClient {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java ChatClient <your_name>");
            return;
        }

        String clientName = args[0];
        String hostname = "localhost";
        int port = 3000;

        try (
            Socket socket = new Socket(hostname, port);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            System.out.println("Connected to chat server as '" + clientName + "'");

            // Send the client's name to the server immediately
            out.println(clientName);

            // Thread to listen for server messages
            Thread readerThread = new Thread(() -> {
                String serverMessage;
                try {
                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println(serverMessage);
                    }
                } catch (IOException e) {
                    System.out.println("Disconnected from server.");
                }
            });
            readerThread.start();

            // Read from console and send to server
            String message;
            while ((message = userInput.readLine()) != null) {
                out.println(message);
            }

        } catch (IOException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
    }
}
