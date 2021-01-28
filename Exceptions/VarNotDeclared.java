package Exceptions;

public class VarNotDeclared extends RuntimeException{
    @Override
    public String toString(){
        return "Variable has not been declared";
    }
}
