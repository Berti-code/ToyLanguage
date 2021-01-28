package Model.exp;
import Exceptions.OperationNonExistent;
import Exceptions.TypeExceptions.TypeMismatch;
import Exceptions.ZeroDivision;
import Model.Types.IntType;
import Model.Values.IntValue;
import Model.Values.Value;
import Model.adt.IDict;
import Model.Types.Type;
import Model.adt.IHeap;

public class ArithExp implements Exp{
    private final int operation;
    private final Exp first, second;

    public ArithExp(Exp first, Exp second, int operation) {
        this.first = first;
        this.second = second;
        this.operation = operation;
    }

    @Override
    public Value evaluate(IDict<String, Value> symTable, IHeap<Integer, Value> heap)  {
        Value value1, value2;
        value1 = first.evaluate(symTable, heap);
        if(value1.getType().equals(new IntType() ) ){
            value2 = second.evaluate(symTable, heap);
            if(value2.getType().equals(new IntType() ) ){
                IntValue intValue1 = (IntValue) value1;
                IntValue intValue2 = (IntValue) value2;
                int number1 = intValue1.getValue();
                int number2 = intValue2.getValue();
                switch (operation){
                    case 1:
                        return new IntValue(number1 + number2);
                    case 2:
                        return new IntValue(number1 - number2);
                    case 3:
                        return new IntValue(number1 * number2);
                    case 4:
                        if (number2 == 0){
                            throw new ZeroDivision();
                        }
                        else return new IntValue(number1/number2);
                    default:
                        throw new OperationNonExistent();
                }
            }
            else {
                throw new TypeMismatch();
            }
        }
        else{
            throw new TypeMismatch();
        }
    }

    public String toString() { return first.toString() + " " + operation + " " + second.toString(); }

    @Override
    public Type typeChecker(IDict<String, Type> typeEnvironment) {
        Type typeFistExpression, typeSecondExpression;
        typeFistExpression = first.typeChecker(typeEnvironment);
        typeSecondExpression = second.typeChecker(typeEnvironment);

        if(typeFistExpression.equals(new IntType())){
            if(typeSecondExpression.equals(new IntType())){
                return new IntType();
            }else {
                throw new TypeMismatch();
            }
        }else {
            throw new TypeMismatch();
        }
    }
}
