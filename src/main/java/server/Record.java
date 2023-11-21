package server;

import com.google.gson.JsonElement;
import com.google.gson.annotations.Expose;

public class Record {
    @Expose
    private JsonElement key;
    @Expose
    private JsonElement value;

    public Record(JsonElement key, JsonElement value){
        this.key = key;
        this.value = value;
    }

    public Record() {
    }

    public JsonElement getKey() {
        return key;
    }

    public JsonElement getValue() {
        return value;
    }
}
