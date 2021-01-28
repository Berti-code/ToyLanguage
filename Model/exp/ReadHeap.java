package Model.exp;


import Exceptions.TypeExceptions.TypeMismatch;
import Exceptions.TypeExceptions.ValueNotRefType;
import Exceptions.VarNotDeclared;
import Model.Types.RefType;
import Model.Types.Type;
import Model.Values.RefValue;
import Model.Values.Value;
import Model.adt.IDict;
import Model.adt.IHeap;

public class ReadHeap implements Exp{
    Exp expression;

    public ReadHeap(Exp expression) {
        this.expression = expression;
    }

    @Override
    public Value evaluate(IDict<String, Value> symTable, IHeap<Integer, Value> heap) {

        Value result = expression.evaluate(symTable, heap);
        if (result instanceof RefValue){
            int address = ((RefValue) result).getAddress();
            if (heap.isDefined(address)){
                return heap.lookup(address);
            }else{
                throw new VarNotDeclared();
            }
        }else{
            throw new TypeMismatch();
        }

    }

    @Override
    public Type typeChecker(IDict<String, Type> typeEnvironment) {
        Type typeExpression;
        typeExpression = expression.typeChecker(typeEnvironment);
        if(typeExpression instanceof RefType){
            RefType refType = (RefType)typeExpression;
            return refType.getInner();
        }else{
            throw new ValueNotRefType();
        }
    }

    @Override
    public String toString() {
        return "rH(" + expression + ')';
    }
}
