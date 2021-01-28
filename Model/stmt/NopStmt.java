package Model.stmt;

import Model.PrgState;
import Model.Types.Type;
import Model.adt.IDict;

public class NopStmt implements IStmt{
    @Override
    public PrgState execute(PrgState state) {
        return null;
    }

    @Override
    public String toStringAsCode() {
        return "Nop Statement  ";
    }

    @Override
    public IDict<String, Type> typeChecker(IDict<String, Type> typeEnvironment) {
        return typeEnvironment;
    }
}
