package server.database;

import com.google.gson.JsonElement;

public interface Database {
    void set(JsonElement key, JsonElement message);
    JsonElement get(JsonElement key);
    void delete(JsonElement key);


}
