package Exceptions.TypeExceptions;

public class TypeMismatch extends RuntimeException {
    @Override
    public String toString() { return "The types don't match!"; }
}
