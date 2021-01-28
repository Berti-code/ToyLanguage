package Model.stmt;

import Exceptions.TypeExceptions.NotBoolType;
import Exceptions.TypeExceptions.TypeMismatch;
import Model.PrgState;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.adt.Dict;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.adt.IStack;
import Model.exp.Exp;
import Model.Values.Value;
import Model.Values.BoolValue;

public class IfStmt implements IStmt {
    Exp expression;
    IStmt thenStatement;
    IStmt elseStatement;

    public IfStmt(Exp expression, IStmt thenStatement, IStmt elseStatement) {
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public String toString() {
        return "If("
                + expression.toString() + ") "
                + "\n   (" + thenStatement.toString() + ")"
                + "\nelse (" + elseStatement.toString() + ")";
    }

    @Override
    public String toStringAsCode() {
        return "If("
                + expression.toString() + "){ "
                + "\n\t" + thenStatement.toStringAsCode()
                + "\n}Else{ \n\t" + elseStatement.toStringAsCode() + "\n}";
    }


    @Override
    public PrgState execute(PrgState state)  {
        IStack<IStmt> stack = state.getStack();
        IDict<String, Value> symbolTable = state.getSymTable();
        IHeap<Integer, Value> heap = state.getHeap();

        Value condition = expression.evaluate(symbolTable,heap);
        if (!condition.getType().equals(new BoolType())){
            throw new NotBoolType();
        }

        BoolValue boolCondition = (BoolValue) condition;
        if(boolCondition.getValue())
        {
            //true
            stack.push(thenStatement);
        } else{
            //false
            stack.push(elseStatement);
        }

        return null;
    }

    @Override
    public IDict<String, Type> typeChecker(IDict<String, Type> typeEnvironment) {
        Type typeExpression = expression.typeChecker(typeEnvironment);
        if(typeExpression.equals(new BoolType())){
            if(typeEnvironment instanceof Dict){
                thenStatement.typeChecker(new Dict<>((Dict<String, Type>) typeEnvironment));
                elseStatement.typeChecker(new Dict<>((Dict<String, Type>) typeEnvironment));
                return typeEnvironment;
            }else {
                throw new TypeMismatch();
            }
        }else {
            throw new NotBoolType();
        }
    }
}
