package Exceptions;

public class NoStatements extends RuntimeException{
    @Override
    public String toString(){
        return "There are no more statements in program state";
    }
}
