package Model.exp;

import Model.Types.Type;
import Model.Values.Value;
import Model.adt.IDict;
import Model.adt.IHeap;

public class ValExp implements  Exp{
    private final Value value;

    public ValExp(Value value) {
        this.value = value;
    }

    @Override
    public String toString(){
        return value.toString();
    }

    @Override
    public Value evaluate(IDict<String, Value> symTable, IHeap<Integer, Value> heap) {
        return value;
    }

    @Override
    public Type typeChecker(IDict<String, Type> typeEnvironment) {
        return value.getType();
    }
}
