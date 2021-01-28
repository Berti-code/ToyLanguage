package Model.stmt;

import Exceptions.TypeExceptions.TypeMismatch;
import Exceptions.VarNotDeclared;
import Model.PrgState;
import Model.Types.Type;
import Model.Values.Value;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.exp.Exp;

public class AssignStmt implements IStmt{
    String variableId;
    Exp expression;

    public AssignStmt(String v, Exp valueExpression) {
        this.variableId = v;
        this.expression = valueExpression;
    }

    @Override
    public String toString(){
        return variableId + "=" +expression.toString();
    }

    @Override
    public String toStringAsCode() {
        return variableId + "=" +expression.toString()+ ";";
    }

    @Override
    public PrgState execute(PrgState state)  {
        IDict<String, Value> symTable = state.getSymTable();
        IHeap<Integer, Value> heap = state.getHeap();

        if (symTable.isDefined(variableId) ){
            Value value = expression.evaluate(symTable, heap);
            Type type = (symTable.lookup(variableId).getType() );
            if (value.getType().equals(type)){
                symTable.update(variableId, value);
            }
            else{
                throw new TypeMismatch();
            }
        }
        else{
            throw new VarNotDeclared();
        }

        return null;
    }

    @Override
    public IDict<String, Type> typeChecker(IDict<String, Type> typeEnvironment) {
        Type typeVariableId = typeEnvironment.lookup(variableId);
        Type typeExpression = expression.typeChecker(typeEnvironment);
        if(typeVariableId.equals(typeExpression)){
            return typeEnvironment;
        }else{
            throw new TypeMismatch();
        }
    }
}
