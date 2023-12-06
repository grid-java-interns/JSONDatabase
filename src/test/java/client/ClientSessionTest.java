package client;

import org.junit.jupiter.api.AfterEach;
import socket.SocketConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static org.mockito.Mockito.*;

class ClientSessionTest {

    @Mock
    private SocketConfig mockSocketWrapper;

    @Mock
    private Socket mockSocket;

    @Mock
    private DataInputStream mockInput;

    @Mock
    private DataOutputStream mockOutput;

    private ClientSession clientSession;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        clientSession = new ClientSession("Hey server", mockSocketWrapper);
    }

    @Test
    void testStart() throws IOException {
        // Arrange
        when(mockSocketWrapper.createSocket()).thenReturn(mockSocket);
        when(mockSocket.getOutputStream()).thenReturn(mockOutput);
        when(mockSocketWrapper.createInputStream(mockSocket)).thenReturn(mockInput);
        when(mockInput.readUTF()).thenReturn("This is the response");

        // Act
        clientSession.start();

        // Assert
        verify(mockInput, times(1)).readUTF();
        //verify(mockSocket, times(1));// If your implementation includes socket closing
    }

    @AfterEach
    void tearDown() throws IOException {
        mockSocket.close();
        mockInput.close();
        mockOutput.close();
    }
}