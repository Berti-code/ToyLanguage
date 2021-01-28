package Model.exp;

import Exceptions.OperationNonExistent;
import Exceptions.TypeExceptions.TypeMismatch;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;
import Model.adt.IDict;
import Model.adt.IHeap;

public class LogicExp implements Exp {
    private final Exp firstExpression;
    private final Exp secondExpression;
    private final int operation;

    public LogicExp(Exp firstExpression, Exp secondExpression, int op) {
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        this.operation = op;
    }


    @Override
    public Value evaluate(IDict<String, Value> symTable, IHeap<Integer, Value> heap) {
        Value value1, value2;
        value1 = firstExpression.evaluate(symTable, heap);
        if (value1.getType().equals(new BoolType())) {
            value2 = secondExpression.evaluate(symTable, heap);
            if (value2.getType().equals(new BoolType())) {

                BoolValue boolValue1 = (BoolValue) value1;
                BoolValue boolValue2 = (BoolValue) value2;

                boolean element1 = boolValue1.getValue();
                boolean element2 = boolValue2.getValue();

                return switch (operation) {
                    case 1 -> new BoolValue(element1 && element2);
                    case 2 -> new BoolValue(element1 || element2);
                    default -> throw new OperationNonExistent();
                };
            } else {
                throw new TypeMismatch();
            }
        } else {
            throw new TypeMismatch();
        }
    }

    @Override
    public Type typeChecker(IDict<String, Type> typeEnvironment) {
        Type typeFistExpression, typeSecondExpression;
        typeFistExpression = firstExpression.typeChecker(typeEnvironment);
        typeSecondExpression = secondExpression.typeChecker(typeEnvironment);

        if (typeFistExpression.equals(new BoolType())) {
            if (typeSecondExpression.equals(new BoolType())) {
                return new BoolType();
            } else {
                throw new TypeMismatch();
            }
        } else {
            throw new TypeMismatch();
        }
    }

}