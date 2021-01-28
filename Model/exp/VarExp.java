package Model.exp;
import Model.Types.Type;
import Model.Values.Value;
import Model.adt.IDict;
import Model.adt.IHeap;

public class VarExp implements Exp{
    private final String id;

    public VarExp(String id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return "@" + id;
    }

    @Override
    public Value evaluate(IDict<String, Value> symTable, IHeap<Integer, Value> heap) {
        return symTable.lookup(id);
    }

    @Override
    public Type typeChecker(IDict<String, Type> typeEnvironment) {
        return typeEnvironment.lookup(id);
    }

}
