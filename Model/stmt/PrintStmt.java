package Model.stmt;

import Model.PrgState;
import Model.Types.Type;
import Model.Values.Value;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.adt.IList;
import Model.exp.Exp;

public class PrintStmt implements  IStmt{
    private final Exp expression;

    public PrintStmt(Exp v) {
        this.expression = v;
    }

    @Override
    public String toString(){
        return  "print(" + expression.toString() + ")\n";
    }

    public String toStringAsCode() {
        return  "print(" + expression.toString() + ");";
    }

    @Override
    public PrgState execute(PrgState state)  {
        IDict<String, Value> symbolTable = state.getSymTable();
        IList<Value> out = state.getList();
        IHeap<Integer, Value> heap = state.getHeap();

        out.add(expression.evaluate(symbolTable, heap));
        return null;
    }

    @Override
    public IDict<String, Type> typeChecker(IDict<String, Type> typeEnvironment) {
        expression.typeChecker(typeEnvironment);
        return typeEnvironment;
    }
}
