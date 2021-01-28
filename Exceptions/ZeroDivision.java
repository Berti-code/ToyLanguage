package Exceptions;

public class ZeroDivision extends RuntimeException{
    @Override
    public String toString(){
        return "Division by zero";
    }
}
