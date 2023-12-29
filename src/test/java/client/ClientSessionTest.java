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
    private SocketConfig mockClientSocketWrapper;

    @Mock
    private Socket mockClientSocket;

    @Mock
    private DataInputStream mockClientInput;

    @Mock
    private DataOutputStream mockOutput;

    private ClientSession clientSession;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        clientSession = new ClientSession("Hey server", mockClientSocketWrapper);
    }

    @Test
    void testStart() throws IOException {
        // Arrange
        when(mockClientSocketWrapper.createSocket()).thenReturn(mockClientSocket);
        when(mockClientSocket.getOutputStream()).thenReturn(mockOutput);
        when(mockClientSocketWrapper.createInputStream(mockClientSocket)).thenReturn(mockClientInput);
        when(mockClientInput.readUTF()).thenReturn("This is the response");

        // Act
        clientSession.start();

        // Assert
        verify(mockClientInput, times(1)).readUTF();
        //verify(mockClientSocket, times(1));// If your implementation includes socket closing
    }

    @AfterEach
    void tearDown() throws IOException {
        mockClientSocket.close();
        mockClientInput.close();
        mockOutput.close();
    }
}