package Model.exp;

import Exceptions.OperationNonExistent;
import Exceptions.TypeExceptions.TypeMismatch;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Model.Values.Value;
import Model.adt.IDict;
import Model.adt.IHeap;

public class RelExp implements Exp {
    private final Exp firstExpression;
    private final Exp secondExpression;
    final StringValue operation;

    public RelExp(Exp firstExpression, Exp secondExpression, StringValue operation) {
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        this.operation = operation;
    }

    @Override
    public String toString(){
        return switch (operation.getValue()) {
            case "<" -> "( " + firstExpression.toString() + "<" + secondExpression.toString() + " )";
            case "<=" -> "( " + firstExpression.toString() + "<=" + secondExpression.toString() + " )";
            case "==" -> "( " + firstExpression.toString() + "==" + secondExpression.toString() + " )";
            case "!=" -> "( " + firstExpression.toString() + "!=" + secondExpression.toString() + " )";
            case ">" -> "( " + firstExpression.toString() + ">" + secondExpression.toString() + " )";
            case ">=" -> "( " + firstExpression.toString() + ">=" + secondExpression.toString() + " )";
            default -> "";
        };
    }

    @Override
    public Value evaluate(IDict<String, Value> symTable, IHeap<Integer, Value> heap) {
        Value value1, value2;
        value1 = firstExpression.evaluate(symTable, heap);
        if(value1.getType().equals(new IntType() ) ){
            value2 = secondExpression.evaluate(symTable, heap);
            if(value2.getType().equals(new IntType() ) ){
                IntValue intValue1 = (IntValue) value1;
                IntValue intValue2 = (IntValue) value2;
                int number1 = intValue1.getValue();
                int number2 = intValue2.getValue();
                switch (operation.getValue()){
                    case "<":
                        if(number1 < number2){
                            return new BoolValue(true);
                        }else{
                            return new BoolValue(false);
                        }
                    case "<=":
                        if(number1 <= number2){
                            return new BoolValue(true);
                        }else{
                            return new BoolValue(false);
                        }
                    case "==":
                        if(number1 == number2){
                            return new BoolValue(true);
                        }else{
                            return new BoolValue(false);
                        }
                    case "!=":
                        if(number1 != number2){
                            return new BoolValue(true);
                        }else{
                            return new BoolValue(false);
                        }
                    case ">":
                        if(number1 > number2){
                            return new BoolValue(true);
                        }else{
                            return new BoolValue(false);
                        }
                    case ">=":
                        if(number1 >= number2){
                            return new BoolValue(true);
                        }else{
                            return new BoolValue(false);
                        }
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

    @Override
    public Type typeChecker(IDict<String, Type> typeEnvironment) {
        Type typeFistExpression, typeSecondExpression;
        typeFistExpression = firstExpression.typeChecker(typeEnvironment);
        typeSecondExpression = secondExpression.typeChecker(typeEnvironment);

        if(typeFistExpression.equals(new IntType())){
            if(typeSecondExpression.equals(new IntType())){
                return new BoolType();
            }else {
                throw new TypeMismatch();
            }
        }else {
            throw new TypeMismatch();
        }
    }

}