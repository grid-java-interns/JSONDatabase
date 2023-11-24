package client;

import com.beust.jcommander.JCommander;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest{
    Task task;
    @BeforeEach
    void setup(){
        task = new Task();
    }
    @Test
    public void toJsonTest() {
        // Arrange
        String[] args = {"-t", "set", "-k", "name", "-v", "John"};
        JCommander.newBuilder().addObject(task).build().parse(args);
        // Act
        String json = task.toJson();
        // Assert
        assertNotNull(json);
        assertTrue(json.contains("set"));
        assertTrue(json.contains("name"));
        assertTrue(json.contains("John"));
    }

}