package Model.Values;

import Model.Types.IntType;
import Model.Types.Type;

public class IntValue implements  Value{
    private final int value;

    public IntValue(int newValue) { value = newValue; }

    public int getValue(){
        return this.value;
    }

    public Type getType() { return new IntType(); }

    @Override
    public boolean equals(Object another){
        if (another == this) return  true; // if compared to itself return true

        if ( !(another instanceof IntValue)) return false; // return false if different type

        IntValue anotherInt = (IntValue) another; // cast Object to actual type

        return (this.value == anotherInt.value); // perform equality check
    }

    @Override
    public String toString(){
        return Integer.toString(this.value);
    }

}
