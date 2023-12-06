package server;


import socket.SocketConfig;

public class Main {
    public static void main(String[] args) {

        ServerSession server = new ServerSession("/Users/shanicejones/prj/pet/JSONDatabaseJava/src/main/java/server/data/db.json", new SocketConfig());
        server.start();
    }


}
