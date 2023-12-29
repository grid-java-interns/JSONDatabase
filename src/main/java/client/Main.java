package client;
import com.beust.jcommander.JCommander;
import socket.SocketConfig;


public class Main {


    public static void main(String[] args) {
        Task task = new Task();
        JCommander.newBuilder().addObject(task).build().parse(args);
        SocketConfig socketConfig = new SocketConfig();
        ClientSession clientSession = new ClientSession(task.toJson(), socketConfig);
        clientSession.start();
    }
}
