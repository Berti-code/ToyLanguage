package Model.exp;
import Model.Types.Type;
import Model.Values.Value;
import Model.adt.IDict;
import Model.adt.IHeap;

public interface Exp {

    Value evaluate(IDict<String, Value> symTable, IHeap<Integer, Value> heap);
    Type typeChecker(IDict<String, Type> typeEnvironment);
}
