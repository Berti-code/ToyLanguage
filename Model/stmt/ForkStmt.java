package Model.stmt;

import Exceptions.TypeExceptions.TypeMismatch;
import Model.PrgState;
import Model.Types.Type;
import Model.Values.StringValue;
import Model.Values.Value;
import Model.adt.*;

import java.io.BufferedReader;

public class ForkStmt implements IStmt{
    private IStmt statement;

    public ForkStmt(IStmt statement) { this.statement = statement; }

    @Override
    public String toString() {
        return "fork(" + this.statement.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) {
        IDict<String, Value> symbolTable                = state.getSymTable();
        IList<Value> outputList                         = state.getList();
        IFileTable<StringValue, BufferedReader> fileTable    = state.getFileTable();
        IHeap<Integer, Value> heap                      = state.getHeap();

        // new stack which contains only the statement to be executed within fork
        IStack<IStmt> newStack = new MyStack<>();
        newStack.push(statement);

        // clone object of symbol table so we have all variables but we cannot change the ones from the outer programState
        if(symbolTable instanceof Dict){
            IDict<String, Value> newSymbolTable = new Dict<>((Dict<String, Value>) symbolTable);
            PrgState newProgramState = new PrgState(newStack,newSymbolTable,outputList,fileTable,heap);
            newProgramState.setId(PrgState.nextId());

            return newProgramState;
        }else {
            throw new TypeMismatch();
        }
    }

    @Override
    public String toStringAsCode() {
        return "fork(" + statement.toString() + ");";
    }

    @Override
    public IDict<String, Type> typeChecker(IDict<String, Type> typeEnvironment) {
        // every "scope has a new type environment"
        statement.typeChecker(new Dict<>((Dict<String, Type>) typeEnvironment));
        return typeEnvironment;
    }
}
