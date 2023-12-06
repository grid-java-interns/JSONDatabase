package io;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class FileOperatorTest {

    @Test
    void testReadExistingFile() throws IOException {
        // Arrange
        String filename = "src/test/resources/test-file.json";
        FileOperator fileOperator = new FileOperator(filename);
        JsonArray testData = new JsonArray();
        testData.add(new JsonPrimitive("test data"));

        // Mocking Files.readAllBytes
        byte[] fileContent = testData.toString().getBytes();
        Files.write(Path.of(filename), fileContent);

        // Act
        JsonArray result = fileOperator.read();

        // Assert
        assertNotNull(result);
        assertEquals(testData, result);
    }


    @Test
    void testReadNonexistentFile() {
        // Arrange
        String filename = "src/test/resources/nonexistent-file.json";
        FileOperator fileOperator = new FileOperator(filename);

        // Act
        JsonArray result = fileOperator.read();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void save() {
        String filename = "src/test/resources/test-save-file.json";
        FileOperator fileOperator = new FileOperator(filename);
        JsonArray testData = new JsonArray();
        testData.add(new JsonPrimitive("test data"));

        fileOperator.save(testData);

        JsonArray testAnswer = fileOperator.read();

        assertEquals(testData,testAnswer);
    }
}