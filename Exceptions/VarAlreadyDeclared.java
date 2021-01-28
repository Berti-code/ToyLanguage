package Exceptions;

public class VarAlreadyDeclared extends RuntimeException{
    @Override
    public String toString(){
        return "Tha variable has already been declared";
    }
}
