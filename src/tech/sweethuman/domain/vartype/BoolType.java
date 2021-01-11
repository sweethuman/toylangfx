package tech.sweethuman.domain.vartype;

import tech.sweethuman.domain.value.BoolValue;
import tech.sweethuman.domain.value.IValue;

public class BoolType implements IType {
    @Override
    public boolean equals(IType other) {
        return other instanceof BoolType;
    }

    @Override
    public IValue defaultValue() {
        return new BoolValue(false);
    }

    @Override
    public String toString() {
        return "Bool";
    }
}
