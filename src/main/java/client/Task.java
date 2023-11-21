package client;

import com.beust.jcommander.Parameter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Task {
    @Parameter(names = "-t")
    private String type;

    @Parameter(names ="-k")
    private String key;

    @Parameter(names = "-v")
    private String value;

    @Parameter(names = "-in")
    private String file;

    private String readFromFile(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }
    public String toJson(){
        if (file != null){
            try{
                return readFromFile(System.getProperty("user.dir") + "/src/client/data/"+file);

            }catch (IOException e) {
                System.out.println("Cannot read file: " + e.getMessage());
            }
        }
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }
}
