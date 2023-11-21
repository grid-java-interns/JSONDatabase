package server;

import client.ExceptionHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import server.command.CommandController;
import server.database.Database;
import server.database.JSONDatabase;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerSession {
    private static final int PORT = 23457;
    private boolean exit =false;

    private final ExecutorService executor;
    private final Database database;
    private ServerSocket server;

    public ServerSession(String filePath){
        this.executor = Executors.newCachedThreadPool();
        this.database = new JSONDatabase(filePath);
    }
    public void start(){

        try{
            server = new ServerSocket(PORT);

            while (!exit){
                Socket socket = server.accept();
                System.out.println("Server started!");
                executor.submit(()->{
                    try{
                        DataInputStream input = new DataInputStream(socket.getInputStream());
                        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                        String msg = input.readUTF();
                        System.out.println("Received: " + msg);
                        CommandController command = new Gson().fromJson(msg,CommandController.class);
                        Response response = command.execute(database,this);
                        String responseText = new GsonBuilder().create().toJson(response);
                        output.writeUTF(responseText);
                        System.out.println("Sent: " + responseText);
                    }catch(IOException e){
                        throw new ExceptionHandler("Server exception", e);
                    }
                });
            }

        } catch (IOException e) {
            throw new ExceptionHandler("Server exception", e);
        }

    }

    public Response close() {
        this.executor.shutdown();
        exit =true;
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Response response = new Response();
        response.setResponse("OK");
        return response;
    }
}
