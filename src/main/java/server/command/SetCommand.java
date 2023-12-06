package server.command;

import com.google.gson.JsonElement;
import server.database.Database;
public class SetCommand implements Command{
    private JsonElement key;
    private JsonElement message;
    private Database database;
    public SetCommand(Database database, JsonElement key, JsonElement message){
        this.database = database;
        this.key = key;
        this.message = message;
    }
    @Override
    public void execute() {
        database.set(key,message);
    }
}
