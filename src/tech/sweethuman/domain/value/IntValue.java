package tech.sweethuman.domain.value;

import tech.sweethuman.domain.vartype.IType;
import tech.sweethuman.domain.vartype.IntType;

public class IntValue implements IValue {
    int data;

    public IntValue(int value) {
        data = value;
    }

    public int getValue() {
        return data;
    }

    @Override
    public IType getType() {
        return new IntType();
    }

    @Override
    public IValue copy() {
        return new IntValue(data);
    }

    @Override
    public boolean equals(IValue other) {
        return data == ((IntValue) other).data;
    }

    @Override
    public String toString() {
        return data + "";
    }

}
