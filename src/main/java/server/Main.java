package server;


public class Main {
    public static void main(String[] args) {

        ServerSession server = new ServerSession(System.getProperty("user.dir") + "/src/server/data/db.json");
        server.start();
    }


}
