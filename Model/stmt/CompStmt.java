package Model.stmt;

import Model.PrgState;
import Model.Types.Type;
import Model.adt.IDict;
import Model.adt.IStack;

public class CompStmt implements IStmt{
    private final IStmt firstStatement;
    private final IStmt secondStatement;

    public CompStmt(IStmt firstStatement, IStmt secondStatement) {
        this.firstStatement = firstStatement;
        this.secondStatement = secondStatement;
    }

    @Override
    public String toString(){
        return "(" + firstStatement.toString() + "\n" + secondStatement.toString() + ")";
    }

    @Override
    public String toStringAsCode() {
        return firstStatement.toStringAsCode() + "\n" + secondStatement.toStringAsCode() ;
    }

    @Override
    public PrgState execute(PrgState state) {
        IStack<IStmt> stack = state.getStack();
        stack.push(secondStatement);
        stack.push(firstStatement);
        return null;
    }

    @Override
    public IDict<String, Type> typeChecker(IDict<String, Type> typeEnvironment) {
        return secondStatement.typeChecker(firstStatement.typeChecker(typeEnvironment));
    }
}
