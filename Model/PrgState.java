package Model;
import Exceptions.MyException;
import Exceptions.NoStatements;
import Model.Values.StringValue;
import Model.adt.*;
import Model.stmt.IStmt;
import Model.Values.Value;
import Model.stmt.NopStmt;

import java.io.BufferedReader;
import java.util.Stack;

public class PrgState {

    private int id = 0;
    private static int threadCount = 0;
    public synchronized static int nextId(){
        threadCount++;
        return threadCount;
    }
    public void setId(int newId){id = newId;}


    private final IStack<IStmt> stackOfStatements;
    private final IDict<String, Value> symbolTable;
    private final IList<Value> outputList;
    private final IFileTable<StringValue, BufferedReader> fileTable;
    private final IHeap<Integer,Value> heap;
    IStmt originalProgram;

    public PrgState(IStack<IStmt> stack, IDict<String, Value> dict, IList<Value> list) {
        stackOfStatements = stack;
        symbolTable = dict;
        outputList = list;
        fileTable = new MyFileTable<>();
        heap = new Heap<>();
    }

    public PrgState(IStack<IStmt> stack, IDict<String, Value> dict, IList<Value> list, IFileTable<StringValue, BufferedReader> fileDict) {
        stackOfStatements = stack;
        symbolTable = dict;
        outputList = list;
        fileTable = fileDict;
        heap = new Heap<>();
    }

    public PrgState(IStack<IStmt> stack, IDict<String, Value> dict, IList<Value> list, IFileTable<StringValue, BufferedReader> fileDict, IHeap<Integer,Value> heap) {
        stackOfStatements = stack;
        symbolTable = dict;
        outputList = list;
        fileTable = fileDict;
        this.heap = heap;
    }

    public PrgState() {
        stackOfStatements = new MyStack<>();
        symbolTable = new Dict<>();
        outputList = new List<>();
        fileTable = new MyFileTable<>();
        heap = new Heap<>();
        originalProgram = new NopStmt();
    }

    public IFileTable<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public IStack<IStmt> getStack() { return stackOfStatements; }

    public IDict<String, Value> getSymTable() {
        return symbolTable;
    }

    public IList<Value> getList() {
        return outputList;
    }

    public IHeap<Integer, Value> getHeap() {
        return heap;
    }

    public String toStringAsCode(){
        StringBuilder output = new StringBuilder();
        Stack<IStmt> stack = stackOfStatements.getStack();
        for(IStmt statement : stack){
            output.append(statement.toStringAsCode());
        }
        return output.toString();
    }

    public boolean isNotCompleted(){
        return !(stackOfStatements.isEmpty() );
    }

    public PrgState oneStep() {
        if(stackOfStatements.isEmpty()){
            throw new NoStatements();
        }
        IStmt currentStatement = stackOfStatements.pop();
        return currentStatement.execute(this);
    }

    @Override
    public String toString(){
        String output = "\n\n";

        output += "\nid = ";
        output += id;

        output += "\n[stackOfStatements]\n";
        output += stackOfStatements.toString();

        output += "\n[symbolTable]\n";
        output += symbolTable.toString();

        output += "\n[outputList]\n";
        output += outputList.toString();

        output += "\n[heap]\n";
        output += heap.toString();

        output += "\n\n";
        return  output;

    }

}