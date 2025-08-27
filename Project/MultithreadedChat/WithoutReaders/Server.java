import java.util.ArrayList;

import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws IOException {
        int portNumber = 3000;
        final int ENDOFSTREAM = -1;
        final String END_OF_MESSAGE = "-END";
        boolean serverIsRunning = true;
        Socket client = null;
        InputStream inStream = null;
        OutputStream outStream = null;

        try (
            ServerSocket serverSocket = new ServerSocket(portNumber);
        ) {
            client = serverSocket.accept();
            inStream = client.getInputStream();
            outStream = client.getOutputStream();
            ArrayList<Character> buffer = new ArrayList<>();
            int curByte;

            while (serverIsRunning) {
                StringBuilder messageBuilder = new StringBuilder();

                while (true) {
                    curByte = inStream.read();
                    if (curByte == ENDOFSTREAM) {
                        serverIsRunning = false;
                        break;
                    }

                    char character = (char) curByte;
                    messageBuilder.append(character);
                    buffer.add(character);

                    if (messageBuilder.length() >= END_OF_MESSAGE.length()) {
                        String tail = messageBuilder.substring(messageBuilder.length() - END_OF_MESSAGE.length());
                        if (tail.equals(END_OF_MESSAGE)) {
                            break; 
                        }
                    }
                }

                String fullMessage = messageBuilder.toString();
                String cleanMessage = fullMessage.replace(END_OF_MESSAGE, "");
                System.out.println(cleanMessage);
                buffer.clear();

                byte[] response = new String("Server has received your message").getBytes();
                outStream.write(response, 0, response.length);
                outStream.flush();
            }

        } catch (IOException e) {
            System.out.println("Failed to start server");
        } finally {
            if (inStream != null) inStream.close();
            if (outStream != null) outStream.close();
            if (client != null) client.close();
        }
    }
}
