package Model.stmt;

import Model.PrgState;
import Model.Types.Type;
import Model.Values.StringValue;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.exp.Exp;
import Model.exp.RelExp;
import Model.exp.VarExp;

public class ForStmt implements IStmt {
    private Exp exp1;
    private Exp exp2;
    private Exp exp3;
    private IStmt statement;
    private String value;

    public ForStmt(Exp exp1, Exp exp2, Exp exp3, String var, IStmt statement){
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
        this.value = var;
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState state) {
        IStack<IStmt> exe = state.getStack();

        IStmt stat = new CompStmt(new AssignStmt(value, exp1), new WhileStmt(new RelExp(new VarExp(value), exp2, new StringValue(">")),new CompStmt(statement, new AssignStmt(value, exp3))));
        exe.push(stat);
        return null;
    }

    @Override
    public IDict<String, Type> typeChecker(IDict<String, Type> typeEnvironment) {
        Type typeExp = this.exp1.typeChecker(typeEnvironment);

        exp1.typeChecker(typeEnvironment);
        exp2.typeChecker(typeEnvironment);
        exp3.typeChecker(typeEnvironment);

        return typeEnvironment;
    }

    @Override
    public String toString(){
        return "for(" + value + "=" + exp1.toString() + ";" + value + "<" +exp2.toString() + ";" + value + "=" + exp3.toString() + ")" + statement.toString();
    }

    @Override
    public String toStringAsCode(){
        return "for(" + value + "=" + exp1.toString() + ";" + value + "<" +exp2.toString() + ";" + value + "=" + exp3.toString() + ")" + statement.toString();
    }
}

