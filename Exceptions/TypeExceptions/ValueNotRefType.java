package Exceptions.TypeExceptions;

public class ValueNotRefType extends RuntimeException{
    @Override
    public String toString() {
        return "The condition evaluated to not BoolType\n";
    }
}