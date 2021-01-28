package Model.stmt;

import Exceptions.MyException;
import Model.PrgState;
import Model.Types.Type;
import Model.adt.Dict;
import Model.adt.IDict;

public interface IStmt {
    PrgState execute(PrgState state);
    String toStringAsCode();
    IDict<String, Type> typeChecker(IDict<String,Type> typeEnvironment);
}
