package server.command;

import server.Response;
import server.database.Database;


public class ExitCommand implements Command{

    private Database database;

    public ExitCommand(Database database){
        this.database = database;
    }
    @Override
    public Response execute() {

        return database.exit();
    }
}
