package io;

import java.util.Scanner;

public class Logger {
    private Scanner scanner = new Scanner(System.in);

    public void write (String message){
        System.out.println(message);
    }

    public String read(){
        return scanner.nextLine();


    }
}
