package Model.stmt;

import Exceptions.FilenameNotDeclared;
import Exceptions.ReadLineException;
import Exceptions.TypeExceptions.NotStringType;
import Model.PrgState;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Model.Values.Value;
import Model.adt.IDict;
import Model.adt.IFileTable;
import Model.adt.IHeap;
import Model.exp.Exp;

import java.io.BufferedReader;
import java.io.IOException;

public class readFile implements IStmt{
    Exp expression;
    String variableName;

    public readFile(Exp expression, String variableName) {
        this.expression = expression;
        this.variableName = variableName;
    }

    @Override
    public PrgState execute(PrgState state) {
        IDict<String, Value> symbolTable = state.getSymTable();
        IFileTable<StringValue, BufferedReader> fileTable = state.getFileTable();
        IHeap<Integer, Value> heap = state.getHeap();

        Value evaluatedExpression = expression.evaluate(symbolTable,heap);
        if (!evaluatedExpression.getType().equals(new StringType())){
            // check if the expression evaluates to StringType
            throw new NotStringType();
        }
        if (!fileTable.isDefined((StringValue) evaluatedExpression)){
            // check if the file name is defined in the file table
            throw new FilenameNotDeclared();
        }

        BufferedReader bufferedReader = fileTable.lookup((StringValue) evaluatedExpression);
        try {
            String valueAsString = bufferedReader.readLine();
            int value;
            if (valueAsString.equals("")){
                value = 0;
            } else {
                value = Integer.parseInt(valueAsString);
            }

            symbolTable.add(variableName,new IntValue(value));
        } catch (IOException e) {
            throw new ReadLineException();
        }

        return null;
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
