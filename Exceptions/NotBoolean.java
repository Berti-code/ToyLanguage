package Exceptions;

public class NotBoolean extends RuntimeException{
    @Override
    public String toString(){
        return "The condition evaluated to not BoolType";
    }
}
