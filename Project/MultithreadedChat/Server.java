
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;
import java.net.*;

public class Server {
    public static class SocketHandler extends Thread{

        Socket userSocket;
        PrintWriter p = null;
        BufferedReader br = null;
        ArrayList<SocketHandler> socketList;
        String username;
        private static final Object lock = new Object();

        public SocketHandler(Socket user, ArrayList<SocketHandler> socketList) throws IOException{
            this.userSocket = user;
            this.p = new PrintWriter(this.userSocket.getOutputStream(), true);
            this.br = new BufferedReader(new InputStreamReader(this.userSocket.getInputStream())); 
            this.socketList = socketList;
            synchronized(lock){
                socketList.add(this);
                this.username = br.readLine(); 
                broadcastAll(this.username + " has connected");
            }
        }

        
        private void broadcastAll(String message){
            synchronized(lock){
                for (SocketHandler handler : socketList){
                    // Doesnt broadcast to self
                    if (!handler.username.equals(this.username)){
                        handler.p.println(message); 
                    }
                    
                }
            }
        }

        @Override
        public void run(){
            String inputLine;

            try {
                while ((inputLine = br.readLine()) != null){
                    broadcastAll(inputLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    synchronized(lock){
                        socketList.remove(this);
                        broadcastAll("client disconnected");
                    }
                    
                    userSocket.close();
                    br.close();
                    p.close();
                } catch (IOException e){}
            }
        }
    }

    public static void main(String[] args) throws IOException{
        int defaultPort = 3000;
        ArrayList<SocketHandler> userList = new ArrayList<>();

        try (
            ServerSocket serverSocket = new ServerSocket(defaultPort);
        ){
            while(true){
                // A user per thread
                Socket clientSocket = serverSocket.accept();
                SocketHandler user = new SocketHandler(clientSocket, userList);
                user.start(); 
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
