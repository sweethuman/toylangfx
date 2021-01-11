package tech.sweethuman.domain.value;

import tech.sweethuman.domain.vartype.BoolType;
import tech.sweethuman.domain.vartype.IType;

public class BoolValue implements IValue {
    boolean data;

    public BoolValue(boolean value) {
        data = value;
    }

    public boolean getValue() {
        return data;
    }

    @Override
    public IType getType() {
        return new BoolType();
    }

    @Override
    public IValue copy() {
        return new BoolValue(data);
    }

    @Override
    public boolean equals(IValue other) {
        return data == ((BoolValue) other).data;
    }

    @Override
    public String toString() {
        return data + "";
    }

}
