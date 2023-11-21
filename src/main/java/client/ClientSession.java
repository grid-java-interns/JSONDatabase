package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientSession {
    private final String message;
    public ClientSession(String message){
        this.message = message;

    }

    public void start(){
        int port = 23457;
        String address = "127.0.0.1";
        try (Socket socket = new Socket(address, port)){
            System.out.println("Client started!");
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            output.writeUTF(message);
            System.out.println("Sent: "  + message);
            String response= input.readUTF();
            System.out.println("Received: "+ response);
        }catch (IOException e){
            throw new ExceptionHandler("Client exception",e);
        }
    }
}
