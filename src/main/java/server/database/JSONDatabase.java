package server.database;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import server.FileOperator;
import server.Record;
import server.Response;

import java.util.Iterator;
import java.util.List;


public class JSONDatabase implements Database {

    private JsonArray database;

    private FileOperator databaseFile;

    private static final String ERROR = "ERROR";
    private static final String OK = "OK";

    public JSONDatabase(String filePath) {

        databaseFile = new FileOperator(filePath);
        database = databaseFile.read();
    }

    public Response set(JsonElement key, JsonElement message) {
        Response res = new Response();
        if (key.isJsonPrimitive()) {
            JsonObject keyValue = new JsonObject();
            keyValue.add(key.getAsString(), message);
            database.add(keyValue);
        } else if (key.isJsonArray()) {
            JsonArray keys = key.getAsJsonArray();
            String toAdd = keys.remove(keys.size() - 1).getAsString();
            findElement(keys, true).getAsJsonObject().add(toAdd, message);
        } else {
            res.setResponse(ERROR);
            res.setReason("No such key");
            return res;
        }

        databaseFile.save(database);
        res.setResponse(OK);
        return res;
    }


    public Response delete(JsonElement key) {
        Response res = new Response();
        if (key.isJsonPrimitive()) {
            String keyToDelete = key.getAsString();
            Iterator<JsonElement> iterator = database.iterator();
            while (iterator.hasNext()) {
                JsonElement entry = iterator.next();
                if (entry.isJsonObject() && entry.getAsJsonObject().has(keyToDelete)) {
                    iterator.remove();
                }
            }
        } else if (key.isJsonArray()) {
            JsonArray keys = key.getAsJsonArray();
            String toRemove = keys.remove(keys.size() - 1).getAsString();
            findElement(keys, false).getAsJsonObject().remove(toRemove);
        } else {
            res.setResponse(ERROR);
            res.setReason("Invalid key format");
            return res;
        }

        databaseFile.save(database);
        res.setResponse(OK);
        return res;
    }

    public Response get(JsonElement key) {
        Response response = new Response();
        JsonElement elem = null;

        if (key.isJsonPrimitive()) {
            String keyToFind = key.getAsString();
            for (JsonElement entry : database) {
                if (entry.isJsonObject() && entry.getAsJsonObject().has(keyToFind)) {
                    elem = entry.getAsJsonObject().get(keyToFind);
                    break;
                }
            }
        } else if (key.isJsonArray()) {
            elem = findElement(key.getAsJsonArray(), false);
        } else {
            response.setResponse(ERROR);
            response.setReason("Invalid key format");
            return response;
        }

        if (elem == null) {
            response.setResponse(ERROR);
            response.setReason("No such key");
        } else {
            response.setResponse(OK);
            response.setValue(elem);
        }

        return response;
    }

    public Response exit() {
        Response response = new Response();
        response.setResponse(OK);
        return response;
    }

    public JsonElement findElement(JsonArray keys, boolean isSet) {
        JsonElement tmp = database;

        for (JsonElement key : keys) {
            if (!key.isJsonPrimitive()) {
                throw new IllegalArgumentException("Invalid key format");
            }

            String keyToFind = key.getAsString();

            boolean found = false;
            if (tmp.isJsonArray()) {
                for (JsonElement entry : tmp.getAsJsonArray()) {
                    if (entry.isJsonObject() && entry.getAsJsonObject().has(keyToFind)) {
                        tmp = entry.getAsJsonObject().get(keyToFind);
                        found = true;
                        break;
                    }
                }
            } else if (tmp.isJsonObject() && tmp.getAsJsonObject().has(keyToFind)) {
                tmp = tmp.getAsJsonObject().get(keyToFind);
                found = true;
            }

            if (!found && isSet) {
                JsonObject newObject = new JsonObject();
                tmp.getAsJsonArray().add(newObject);
                tmp = newObject;
            } else if (!found) {
                return null;  // Key not found
            }
        }

        return tmp;
    }
}





