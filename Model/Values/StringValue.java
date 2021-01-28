package Model.Values;

import Model.Types.StringType;
import Model.Types.Type;

public class StringValue implements Value {
    private final String value;

    public StringValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public boolean equals(Object another) {
        if (another == this) return true; // if compared to itself return true

        if (!(another instanceof StringValue)) return false; // return false if different type

        StringValue anotherStringValue = (StringValue) another; // cast Object to actual type

        return (this.value.equals(anotherStringValue.value)); // perform equality check
    }
}
