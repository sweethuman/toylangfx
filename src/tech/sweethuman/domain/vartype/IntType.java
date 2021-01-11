package tech.sweethuman.domain.vartype;

import tech.sweethuman.domain.value.IValue;
import tech.sweethuman.domain.value.IntValue;

public class IntType implements IType {
    @Override
    public boolean equals(IType other) {
        return other instanceof IntType;
    }

    @Override
    public IValue defaultValue() {
        return new IntValue(0);
    }

    @Override
    public String toString() {
        return "Int";
    }
}
