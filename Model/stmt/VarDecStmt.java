package Model.stmt;

import Exceptions.AlreadyDeclared;
import Model.PrgState;
import Model.Types.Type;
import Model.Values.Value;
import Model.adt.IDict;

public class VarDecStmt implements IStmt{
    String name;
    Type type;

    public VarDecStmt(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString(){

        return type + " " + name +"; ";
    }

    @Override
    public String toStringAsCode() {
        return type + " " + name +"; ";
    }

    @Override
    public PrgState execute(PrgState state) {
        IDict<String, Value> symTable = state.getSymTable();

        if (symTable.isDefined(name) ){
            throw new AlreadyDeclared();
        }
        else{
            symTable.add(name,type.defaultValue());
        }

        return null;
    }

    @Override
    public IDict<String, Type> typeChecker(IDict<String, Type> typeEnvironment) {
        typeEnvironment.add(name, type);
        return typeEnvironment;
    }
}
