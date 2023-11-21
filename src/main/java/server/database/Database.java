package server.database;

import com.google.gson.JsonElement;
import server.Response;

import java.util.Optional;

public interface Database {
    Response set(JsonElement key, JsonElement message);
    Response get(JsonElement key);
    Response delete(JsonElement key);

    Response exit();
}
