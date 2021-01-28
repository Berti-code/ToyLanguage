package Exceptions.TypeExceptions;

public class NotBoolType extends RuntimeException{
    @Override
    public String toString() { return "The condition is not BoolType!"; }
}
