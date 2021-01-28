package Model.stmt;

import Exceptions.FilenameAlreadyDeclared;
import Exceptions.IORunTimeException;
import Exceptions.TypeExceptions.NotStringType;
import Model.PrgState;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.StringValue;
import Model.Values.Value;
import Model.adt.IDict;
import Model.adt.IFileTable;
import Model.adt.IHeap;
import Model.exp.Exp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class openRFile implements IStmt{
    Exp expression;

    public openRFile(Exp expression) {
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) {
        IDict<String, Value> symbolTable = state.getSymTable();
        IFileTable<StringValue, BufferedReader> fileTable = state.getFileTable();
        IHeap<Integer, Value> heap = state.getHeap();

        Value evaluatedExpression = expression.evaluate(symbolTable, heap);
        if (!evaluatedExpression.getType().equals(new StringType())){
            throw new NotStringType(); // check if the expression evaluates to StringType
        }
        if (fileTable.isDefined((StringValue) evaluatedExpression))
        {
            throw new FilenameAlreadyDeclared(); // check if the expression evaluated is declared in the file table
        }


        BufferedReader bufferedReader;
        try{
            bufferedReader = new BufferedReader(new FileReader(((StringValue) evaluatedExpression).getValue()));
            fileTable.add((StringValue) evaluatedExpression,bufferedReader);
        } catch (FileNotFoundException e) {
            throw new IORunTimeException();
        }

        return  null;
    }

    @Override
    public String toStringAsCode() {
        return "you should do toStringAsCode for openRFile";
    }

    @Override
    public IDict<String, Type> typeChecker(IDict<String, Type> typeEnvironment) {
        expression.typeChecker(typeEnvironment);
        return typeEnvironment;
    }
}
