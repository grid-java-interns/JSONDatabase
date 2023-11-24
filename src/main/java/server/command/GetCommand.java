package server.command;

import com.google.gson.JsonElement;
import server.database.Database;


public class GetCommand implements  Command{
    private JsonElement key;
    private Database database;
    private JsonElement value;
    public GetCommand(Database database, JsonElement key){
        this.database = database;
        this.key = key;

    }

    public void setValue(JsonElement value) {
        this.value = value;
    }

    public JsonElement getValue() {
        return value;
    }

    @Override
    public void execute() {
        setValue(database.get(key));
    }
}
