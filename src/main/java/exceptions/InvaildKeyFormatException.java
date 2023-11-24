package exceptions;

public class InvaildKeyFormatException extends RuntimeException{
    public InvaildKeyFormatException(){
        super("Invalid Key format");
    }
}
