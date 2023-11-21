package server;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class FileOperator {

    private final String filename;
    private final Gson gson= new Gson();
    private final File file;
    private final ReadWriteLock lock;

    public FileOperator(String filename){
        this.filename = filename;
        this.file = new File(filename);

        lock = new ReentrantReadWriteLock();

    }

    public JsonArray read() {

        JsonArray database = new JsonArray();
        lock.readLock().lock();
        try{
            if (Files.exists(Path.of(filename))) {
                String content = new String(Files.readAllBytes(Path.of(filename)));
                database = new Gson().fromJson(content, JsonArray.class);
            } else {
                Files.createFile(Path.of(filename));
                database = new JsonArray();

            }
        }catch (IOException e){
            e.printStackTrace();
        }
        lock.readLock().unlock();
        return database;
    }


    public void save(JsonArray obj) {
        lock.writeLock().lock();
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(obj,writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        lock.writeLock().unlock();

    }
}


