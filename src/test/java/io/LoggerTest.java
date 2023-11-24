package io;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static org.junit.jupiter.api.Assertions.*;

class LoggerTest {
    private Logger logger;

    // Set up the input and output streams for testing
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    @BeforeEach
    void setUp() {
        // Redirect System.out for testing
        System.setOut(new PrintStream(outputStream));

        // Reset the scanner to use the original System.in
        logger = new Logger();
    }

    @Test
    void testWrite() {
        // Arrange
        String message = "Test Message";

        // Act
        logger.write(message);

        // Assert
        assertEquals(message + System.lineSeparator(), outputStream.toString());
    }

    @AfterEach
    void tearDown() {
        // Reset System.out and System.in to their original values
        System.setOut(originalOut);
        System.setIn(originalIn);
    }
}