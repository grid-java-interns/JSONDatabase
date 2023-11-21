package server.command;

import com.google.gson.JsonElement;
import server.Response;
import server.ServerSession;
import server.database.Database;

public class CommandController{
    private String type;
    private JsonElement key;
    private JsonElement value;
    public Response execute(Database database, ServerSession server) {

        Command command;
        if ("get".equals(type)) {
            command = new GetCommand(database,key);
            return command.execute();
        } else if ("set".equals(type)) {
            command = new SetCommand(database,key, value);
            return command.execute();
        } else if ("delete".equals(type)) {
            command = new DeleteCommand(database,key);
            return command.execute();
        }else {
            return server.close();
        }

    }
}
