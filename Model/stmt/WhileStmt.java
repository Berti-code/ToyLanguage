package Model.stmt;

import Exceptions.TypeExceptions.NotBoolType;
import Model.PrgState;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;
import Model.adt.Dict;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.adt.IStack;
import Model.exp.Exp;

public class WhileStmt implements IStmt{
    Exp expression;
    IStmt statement;

    public WhileStmt(Exp expression, IStmt statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState state) {
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
            stack.push(new WhileStmt(expression, statement));
            stack.push(statement);
        }
        return null;
    }

    @Override
    public String toStringAsCode() {
        return "while(" + expression.toString() +"){\n\t" + statement.toString() + ";\n}";
    }

    @Override
    public IDict<String, Type> typeChecker(IDict<String, Type> typeEnvironment) {
        Type typeExpression = expression.typeChecker(typeEnvironment);
        if(typeExpression.equals(new BoolType())){
            statement.typeChecker(new Dict<>((Dict<String, Type>) typeEnvironment));
            return typeEnvironment;
        }else{
            throw new NotBoolType();
        }
    }

    @Override
    public String toString() {
        return "While("
                + expression.toString() + ") "
                + " (" + statement.toString() + ")";
    }
}
