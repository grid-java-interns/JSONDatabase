package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import exceptions.ExceptionHandler;
import io.Logger;
import socket.SocketConfig;

public class ClientSession {
    private final String message;
    private final Logger logger;
    private final SocketConfig socketConfig;
    public ClientSession(String message, SocketConfig socketConfig){
        this.message = message;
        this.logger = new Logger();
        this.socketConfig = socketConfig;

    }
    public void start(){
        try {
            Socket socket = socketConfig.createSocket();
            logger.write("Client started!");
            DataInputStream input = socketConfig.createInputStream(socket);
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
