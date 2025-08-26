

import java.io.*;
import java.net.*;
import java.util.*;

public class server{
    
    public static void main(String[] args) throws IOException{
        
        int portNumber = 3000;
        // boolean serverRunning = true;

        try(
            ServerSocket serverSocket = new ServerSocket(portNumber);
            // accept is IO blocking apparently
            Socket clientSocket = serverSocket.accept();
            OutputStream outStream = clientSocket.getOutputStream();
            InputStream inStream = clientSocket.getInputStream();
        ){
            ArrayList<Integer> intByteArray = new ArrayList<>();
            
            System.out.println("connected");

            int curByte;
            while ((curByte = inStream.read()) != -1) {
                intByteArray.add(curByte);
                System.out.print(curByte + "\n");
                System.out.flush();
            }

        } catch (IOException e){
            System.out.println("Exception caught when running server");
        }
    }
}
