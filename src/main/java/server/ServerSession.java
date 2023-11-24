package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import exceptions.ExceptionHandler;
import io.Logger;
import server.command.CommandController;
import server.database.Database;
import server.database.JSONDatabase;
import server.model.Response;

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
    private final Logger logger;

    public ServerSession(String filePath){
        this.executor = Executors.newCachedThreadPool();
        this.database = new JSONDatabase(filePath);
        this.logger =new Logger();
    }
    public void start(){

        try{
            server = new ServerSocket(PORT);

            while (!exit){
                Socket socket = server.accept();
                logger.write("Server started!");
                executor.submit(()->{
                    try{
                        DataInputStream input = new DataInputStream(socket.getInputStream());
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
            }

        } catch (IOException e) {
            throw new ExceptionHandler("Server Exception", e);
        }

    }

    public Response close() {
        this.executor.shutdown();
        exit =true;
        try {
            server.close();
        } catch (IOException e) {
            throw new ExceptionHandler("Sever Closing Exception", e);
        }
        Response response = new Response();
        response.setResponse("OK");
        return response;
    }
}
