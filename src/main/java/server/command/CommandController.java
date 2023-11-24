package server.command;

import com.google.gson.JsonElement;
import exceptions.InvaildKeyFormatException;
import exceptions.NoSuchKeyException;
import server.model.Response;
import server.ServerSession;
import server.database.Database;

public class CommandController{
    private String type;
    private JsonElement key;
    private JsonElement value;
    private static final String ERROR = "ERROR";
    private static final String OK = "OK";
    public Response execute(Database database, ServerSession server) {
        Response response = new Response();
        try{
            Command command;
            if ("get".equals(type)) {
                command = new GetCommand(database,key);
                command.execute();
                response.setValue(((GetCommand) command).getValue());
            } else if ("set".equals(type)) {
                command = new SetCommand(database,key, value);
                command.execute();
            } else if ("delete".equals(type)) {
                command = new DeleteCommand(database,key);
                command.execute();
            }else if ("exit".equals(type)) {

                return server.close();
            }

            response.setResponse(OK);
        }catch (InvaildKeyFormatException | NoSuchKeyException e){
            response.setResponse(ERROR);
            response.setReason(e.getMessage());
        }

        return response;

    }
}
