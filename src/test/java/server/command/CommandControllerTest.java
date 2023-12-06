package server.command;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.ServerSession;
import server.database.Database;
import server.database.JSONDatabase;
import server.model.Response;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommandControllerTest {

    private CommandController commandController;
    private Database mockDatabase;
    private ServerSession mockServer;

    @BeforeEach
    void setUp() {
        mockDatabase = mock(JSONDatabase.class);
        mockServer = mock(ServerSession.class);

    }

    @Test
    void executeGetCommand() {
        // Arrange
        String msg ="{\"type\":\"get\",\"key\":\"someKey\"}";
        commandController = new Gson().fromJson(msg,CommandController.class);
        JsonElement key = new JsonPrimitive("someKey");
        when(mockDatabase.get(key)).thenReturn(new JsonPrimitive("someValue"));

        // Act
        Response response = commandController.execute(mockDatabase, mockServer);
        String responseText = new GsonBuilder().create().toJson(response);

        // Assert
        assertEquals("OK", response.getResponse());
        assertNull(response.getReason());
        assertEquals(new JsonPrimitive("someValue"), response.getValue());

        // Verify that the GetCommand was created and executed
        verify(mockDatabase, times(1)).get(key);
    }

    @Test
    void executeSetCommand() {
        // Arrange
        String msg ="{\"type\":\"set\",\"key\":\"someKey\",\"value\":\"someValue\"}";
        commandController = new Gson().fromJson(msg,CommandController.class);

        // Act
        Response response = commandController.execute(mockDatabase, mockServer);

        // Assert
        assertEquals("OK", response.getResponse());
        assertNull(response.getReason());
        assertNull(response.getValue()); // SetCommand doesn't set response.getValue()

        // Verify that the SetCommand was created and executed
        verify(mockDatabase, times(1)).set(new JsonPrimitive("someKey"), new JsonPrimitive("someValue"));
    }

    @Test
    void executeDeleteCommand() {
        // Arrange

        String msg ="{\"type\":\"delete\",\"key\":\"someKey\"}";
        commandController = new Gson().fromJson(msg,CommandController.class);
        // Act
        Response response = commandController.execute(mockDatabase, mockServer);

        // Assert
        assertEquals("OK", response.getResponse());
        assertNull(response.getReason());
        assertNull(response.getValue()); // DeleteCommand doesn't set response.getValue()

        // Verify that the DeleteCommand was created and executed
        verify(mockDatabase, times(1)).delete(new JsonPrimitive("someKey"));
    }

    @Test
    void executeExitCommand() {
        // Arrange
        String msg ="{\"type\":\"exit\"}";
        commandController = new Gson().fromJson(msg,CommandController.class);

        // Act


        // Act
        Response response = commandController.execute(mockDatabase, mockServer);
        // Assert
        assertEquals("OK", response.getResponse());
        assertNull(response.getReason());
        assertNull(response.getValue()); // Exit command doesn't set response.getValue()

        // Verify that the ServerSession's close method was called
        verify(mockServer, times(1)).close();
    }

    @Test
    void executeInvalidCommand() {
        // Arrange
        String msg ="{\"type\":\"invalid\"}";
        commandController = new Gson().fromJson(msg,CommandController.class);

        // Act
        Response response = commandController.execute(mockDatabase, mockServer);

        // Assert
        assertEquals("ERROR", response.getResponse());
        assertNotNull(response.getReason());

        // Verify that no database operations were performed
        verifyNoInteractions(mockDatabase);
    }

}