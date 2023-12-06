package socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketConfig {

    private static final int PORT = 23457;
    private static final String ADDRESS = "127.0.0.1";
    public Socket createSocket() throws IOException {
        return new Socket(ADDRESS, PORT);
    }

    public DataInputStream createInputStream(Socket socket) throws IOException{

        return new DataInputStream(socket.getInputStream());

    }

    public DataOutputStream createOutputStream(Socket socket) throws IOException{
        return new DataOutputStream(socket.getOutputStream());
    }

    public ServerSocket createServerSocket() throws IOException{
        return new ServerSocket(PORT, 50, InetAddress.getByName(ADDRESS));
    }
}
