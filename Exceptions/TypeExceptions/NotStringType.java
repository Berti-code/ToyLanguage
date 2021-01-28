package Exceptions.TypeExceptions;

public class NotStringType extends RuntimeException {
    @Override
    public String toString() { return "The condition is not String!"; }
}
