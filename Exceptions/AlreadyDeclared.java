package Exceptions;

public class AlreadyDeclared extends RuntimeException{
    @Override
    public String toString(){
        return "Tha variable has already been declared";
    }
}
