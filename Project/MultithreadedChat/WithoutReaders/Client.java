import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
        String serverAddress = "localhost";
        int portNumber = 3000;
        final String END_OF_MESSAGE = "-END";
        Socket server = null;
        OutputStream outStream = null;
        InputStream inStream = null;
        Scanner scanner = null;

        try {
            server = new Socket(serverAddress, portNumber);
            outStream = server.getOutputStream();
            inStream = server.getInputStream();
            scanner = new Scanner(System.in);

            boolean clientIsRunning = true;

            while (clientIsRunning) {
                System.out.println("Enter your message. Type '-END' on a new line to finish:");
                StringBuilder messageBuilder = new StringBuilder();

                while (true) {
                    String line = scanner.nextLine();
                    if (line.equals(END_OF_MESSAGE)) {
                        messageBuilder.append(END_OF_MESSAGE);
                        break;
                    }
                    messageBuilder.append(line).append("\n");
                }

                String messageToSend = messageBuilder.toString();
                byte[] messageBytes = messageToSend.getBytes();
                outStream.write(messageBytes);
                outStream.flush();

                StringBuilder responseBuilder = new StringBuilder();
                int curByte;

                while ((curByte = inStream.read()) != -1) {
                    char character = (char) curByte;
                    responseBuilder.append(character);
                    if (inStream.available() == 0) {
                        break;
                    }
                }

                String serverResponse = responseBuilder.toString();
                System.out.println(serverResponse);
            }

        } catch (IOException e) {
            System.out.println("Failed to connect to server");
        } finally {
            if (scanner != null) scanner.close();
            if (inStream != null) inStream.close();
            if (outStream != null) outStream.close();
            if (server != null) server.close();
        }
    }
}
