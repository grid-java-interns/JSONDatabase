package client;
import com.beust.jcommander.JCommander;


public class Main {


    public static void main(String[] args) {
        Task task = new Task();
        JCommander.newBuilder().addObject(task).build().parse(args);
        ClientSession clientSession = new ClientSession(task.toJson());
        clientSession.start();
    }
}
