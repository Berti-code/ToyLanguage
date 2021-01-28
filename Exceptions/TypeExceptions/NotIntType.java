package Exceptions.TypeExceptions;

public class NotIntType extends RuntimeException{
    @Override
    public String toString() { return "The condition is not IntType!"; }
}
