package Model.stmt;

import Exceptions.AddressNotExistent;
import Exceptions.TypeExceptions.TypeMismatch;
import Exceptions.TypeExceptions.ValueNotRefType;
import Exceptions.VarNotDeclared;
import Model.PrgState;
import Model.Types.RefType;
import Model.Types.Type;
import Model.Values.RefValue;
import Model.Values.Value;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.exp.Exp;

public class WriteToHeapStmt implements IStmt{
    String variableId;
    Exp expression;

    public WriteToHeapStmt(String variableId, Exp expression) {
        this.variableId = variableId;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) {
        IDict<String, Value> symTable = state.getSymTable();
        IHeap<Integer, Value> heap = state.getHeap();

        if (symTable.isDefined(variableId) ) {
            if (symTable.lookup(variableId).getType() instanceof RefType) {
                if(symTable.lookup(variableId) instanceof RefValue){
                    int address = ((RefValue) symTable.lookup(variableId)).getAddress();
                    if(heap.isDefined(address)){
                        Value result = expression.evaluate(symTable,heap);
                        if (heap.lookup(address).getType().equals(result.getType())){
                            heap.update(address,result);
                        }else {
                            throw new TypeMismatch();
                        }
                    }else {
                        throw new AddressNotExistent();
                    }
                }
            } else {
                throw new ValueNotRefType();
            }
        }else{
            throw new VarNotDeclared();
        }

        return null;
    }

    @Override
    public String toStringAsCode() {
        return "wHeap(" + variableId + "," + expression.toString() + ");";
    }

    @Override
    public IDict<String, Type> typeChecker(IDict<String, Type> typeEnvironment) {
        Type typeVariableId = typeEnvironment.lookup(variableId);
        Type typeExpression = expression.typeChecker(typeEnvironment);
        if(typeVariableId.equals(new RefType(typeExpression))){
            return typeEnvironment;
        }else{
            throw new TypeMismatch();
        }
    }

    @Override
    public String toString() {
        return "wHeap(" + variableId + "," + expression.toString() + ")  ";
    }
}