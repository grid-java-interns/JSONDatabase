package server.database;
import com.google.gson.*;
import io.FileOperator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JSONDatabaseTest {

    @Mock
    private FileOperator fileOperatorSpy;

    private JSONDatabase jsonDatabase;
    private FileOperator fileOperator;

    @BeforeEach
    void setUp() {
        fileOperator = new FileOperator("src/test/resources/testFilePath.json");
        fileOperatorSpy = Mockito.spy(fileOperator);

        jsonDatabase = new JSONDatabase(fileOperatorSpy);
    }

    @Test
    void setPrimitiveKey() {
        // Arrange
        JsonElement key = new JsonPrimitive("key");
        JsonElement message = new JsonPrimitive("value");

        // Act
        jsonDatabase.set(key, message);

        // Assert
        JsonObject expected = new JsonObject();
        expected.add("key", new JsonPrimitive("value"));
        verify(fileOperatorSpy, times(1)).save(any(JsonArray.class));
    }

    @Test
    void setArrayKey() {
        // Arrange
        JsonElement key = new JsonArray();
        key.getAsJsonArray().add(new JsonPrimitive("nestedKey"));
        key.getAsJsonArray().add(new JsonPrimitive("nestednested"));
        JsonElement message = new JsonPrimitive("value");

        // Act
        jsonDatabase.set(key, message);

        // Assert

        verify(fileOperatorSpy, times(1)).save(any(JsonArray.class));
    }

    @Test
    void deletePrimitiveKey() {
        // Arrange
        JsonElement key = new JsonPrimitive("key");

        // Act
        jsonDatabase.delete(key);

        // Assert

        verify(fileOperatorSpy, times(1)).save(any(JsonArray.class));
    }

    /*@Test
    void deleteArrayKey() {
        // Arrange
        JsonElement key = new JsonArray();
        key.getAsJsonArray().add(new JsonPrimitive("nestedKey"));
        key.getAsJsonArray().add(new JsonPrimitive("nestednested"));

        // Act
        jsonDatabase.delete(key);

        // Assert
        verify(fileOperatorSpy, times(1)).save(any(JsonArray.class));
    }*/

    @Test
    void getPrimitiveKey() {
        // Arrange
        JsonElement key = new JsonPrimitive("1");

        // Act & Assert
        assertDoesNotThrow(() -> jsonDatabase.get(key));
    }

    @Test
    void getArrayKey() {
        // Arrange
        JsonElement key = new JsonArray();
        key.getAsJsonArray().add(new JsonPrimitive("person"));
        key.getAsJsonArray().add(new JsonPrimitive("name"));

        // Act & Assert
        JsonElement get = jsonDatabase.get(key);

        assertEquals(get,new JsonPrimitive("Elon Musk"));
    }
}