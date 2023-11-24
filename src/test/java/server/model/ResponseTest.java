package server.model;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResponseTest {
    Response response;
    @BeforeEach
    void setup(){
        response = new Response();
    }
    @Test
    void setResponse() {
        response.setResponse("OK");
        String responseText = new GsonBuilder().create().toJson(response);

        assertNotNull(responseText);
        assertTrue(responseText.contains("OK"));

    }

    @Test
    void setReason() {
        response.setReason("Invalid Input");
        String responseText = new GsonBuilder().create().toJson(response);

        assertNotNull(responseText);
        assertTrue(responseText.contains("Invalid Input"));
    }


    @Test
    void setValue() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name","Shanice");
        response.setValue(jsonObject);
        String responseText = new GsonBuilder().create().toJson(response);

        assertNotNull(responseText);
        assertTrue(responseText.contains("name"));
        assertTrue(responseText.contains("Shanice"));
    }
}