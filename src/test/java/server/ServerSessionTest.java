package server;

import io.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import server.ServerSession;
import server.database.Database;
import socket.SocketConfig;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ServerSessionTest {

    @Mock
    private SocketConfig mockSocketConfig;

    @Mock
    private ServerSocket mockServerSocket;

    @Mock
    private Socket mockSocket;

    @Mock
    private DataInputStream mockInput;

    @Mock
    private DataOutputStream mockOutput;

    @Mock
    private Database mockDatabase;

    @Mock
    private Logger mockLogger;


    private ServerSession serverSession;

    @BeforeEach
    void setUp() {
        mockSocketConfig = mock(SocketConfig.class);
        mockServerSocket = mock(ServerSocket.class);
        mockSocket = mock(Socket.class);
        mockInput = mock(DataInputStream.class);
        mockOutput = mock(DataOutputStream.class);
        mockDatabase = mock(Database.class);
        mockLogger = mock(Logger.class);

        serverSession = new ServerSession("testFilePath.json", mockSocketConfig);
    }

    @Test
    void testStart() throws IOException {
        // Arrange
        when(mockSocketConfig.createServerSocket()).thenReturn(mockServerSocket);
        when(mockServerSocket.accept()).thenReturn(mockSocket).thenAnswer(invocation -> {
            return null;
        });
        when(mockSocketConfig.createInputStream(any())).thenReturn(mockInput);
        when(mockSocket.getOutputStream()).thenReturn(mockOutput);
        when(mockInput.readUTF()).thenReturn("{\"type\":\"lalala\"}");

        serverSession.exit =true;
        // Act
        serverSession.start();

        // Assert

        //verify(mockLogger, times(1)).write("Server started!");
        verify(mockInput, times(1)).readUTF();
        //verify(mockOutput, times(1)).writeUTF(anyString());
        //verify(mockLogger, times(1)).write("Received: {\"type\":\"lalala\"}");
        //verify(mockLogger, times(1)).write("Sent: ");
    }

    @AfterEach
    void tearDown() throws IOException {
        serverSession.close();
        mockSocket.close();
        mockInput.close();
        mockOutput.close();
        mockServerSocket.close();

    }

    // Add more tests as needed
}