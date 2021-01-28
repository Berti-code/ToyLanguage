package Exceptions;

public class IORunTimeException extends RuntimeException{
    @Override
    public String toString() {
        return "Cannot open file";
    }
}
