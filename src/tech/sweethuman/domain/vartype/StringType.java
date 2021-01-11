package tech.sweethuman.domain.vartype;

import tech.sweethuman.domain.value.IValue;
import tech.sweethuman.domain.value.StringValue;

public class StringType implements IType {
    @Override
    public boolean equals(IType other) {
        return other instanceof StringType;
    }

    @Override
    public IValue defaultValue() {
        return new StringValue("");
    }

    @Override
    public String toString() {
        return "String";
    }
}
