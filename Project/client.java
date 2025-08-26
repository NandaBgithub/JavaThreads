import java.net.*;
import java.io.*;

public class client {

    // Literally cooked this up from documentation no clue wtf Im doing, I have no business slinging
    // raw bytes but here we are.
    public static void main(String[] args) {
        String hostName = "localhost";  // Use localhost instead of "client"
        int portNumber = 3000;

        try (
            Socket clientSocket = new Socket(hostName, portNumber);
            OutputStream out = clientSocket.getOutputStream();
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
        ) {
            System.out.println("Client connected to server on port " + portNumber);

            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                byte[] bytesToSend = userInput.getBytes("UTF-8");
                out.write(bytesToSend);
                out.flush(); 
            }

        } catch (IOException e) {
            System.out.println("Client error:");
            e.printStackTrace();
        }
    }
}
