package server.command;

import com.google.gson.JsonElement;
import server.Response;
import server.database.Database;


public class DeleteCommand implements Command{
    private JsonElement key;
    private Database database;
    public DeleteCommand(Database database, JsonElement key){
        this.database = database;
        this.key =key;
    }


    @Override
    public Response execute() {
        return database.delete(key);
    }
}
