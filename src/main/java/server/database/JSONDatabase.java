package server.database;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.FileOperator;
import exceptions.*;

import java.util.Iterator;


public class JSONDatabase implements Database {

    private JsonArray database;

    private FileOperator databaseFile;



    public JSONDatabase(String filePath) {

        databaseFile = new FileOperator(filePath);
        database = databaseFile.read();
    }

    public void set(JsonElement key, JsonElement message) {
        if (key.isJsonPrimitive()) {
            JsonObject keyValue = new JsonObject();
            keyValue.add(key.getAsString(), message);
            database.add(keyValue);
        } else if (key.isJsonArray()) {
            JsonArray keys = key.getAsJsonArray();
            String toAdd = keys.remove(keys.size() - 1).getAsString();
            findElement(keys, true).getAsJsonObject().add(toAdd, message);
        } else {
            throw new NoSuchKeyException();
        }

        databaseFile.save(database);

    }


    public void delete(JsonElement key) {
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
            throw new InvaildKeyFormatException();
        }

        databaseFile.save(database);

    }

    public JsonElement get(JsonElement key) {
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
            throw new InvaildKeyFormatException();
        }

        if (elem == null) {
            throw new NoSuchKeyException();
        }
        return elem;
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





