package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exceptions.ExceptionHandler;
import io.FileOperator;
import io.Logger;
import server.command.CommandController;
import server.database.Database;
import server.database.JSONDatabase;
import server.model.Response;
import socket.SocketConfig;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ServerSession {

    boolean exit =false;
    private SocketConfig socketConfig;
    ExecutorService executor;
    private final Database database;
    private ServerSocket server;
    private final Logger logger;

    public ServerSession(String filePath, SocketConfig socketConfig){
        this.executor = Executors.newCachedThreadPool();
        this.database = new JSONDatabase(new FileOperator(filePath));
        this.logger =new Logger();
        this.socketConfig = socketConfig;
    }
    public void start(){

        try{
            server = socketConfig.createServerSocket();
            logger.write("Server started!");
            do{
                Socket socket = server.accept();

                executor.submit(()->{
                    try{
                        DataInputStream input = socketConfig.createInputStream(socket);
                        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                        String msg = input.readUTF();
                        logger.write("Received: " + msg);
                        CommandController command = new Gson().fromJson(msg,CommandController.class);
                        Response response = command.execute(database,this);
                        String responseText = new GsonBuilder().create().toJson(response);
                        output.writeUTF(responseText);
                        logger.write("Sent: " + responseText);
                    }catch(IOException e){
                        throw new ExceptionHandler("Server exception", e);
                    }
                });
            }while (!exit);

        } catch (IOException e) {
            throw new ExceptionHandler("Server Exception", e);
        }

    }

    public void close() {
        try {
            exit = true;
            executor.shutdown();

            server.close();
        } catch (IOException e) {
            throw new ExceptionHandler("Sever Closing Exception", e);
        }
    }
}
