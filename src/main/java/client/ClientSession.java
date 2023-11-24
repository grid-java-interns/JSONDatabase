package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import exceptions.ExceptionHandler;
import io.Logger;

public class ClientSession {
    private final String message;
    private final Logger logger;
    public ClientSession(String message){
        this.message = message;
        this.logger = new Logger();
    }

    public void start(){

        int port = 23457;
        String address = "127.0.0.1";
        try (Socket socket = new Socket(address, port)){
            logger.write("Client started!");
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            output.writeUTF(message);
            logger.write("Sent: "  + message);
            String response= input.readUTF();
            logger.write("Received: "+ response);
        }catch (IOException e){
            throw new ExceptionHandler("Client Exception", e);
        }
    }
}
