package tech.sweethuman.domain.vartype;

import tech.sweethuman.domain.value.IValue;
import tech.sweethuman.domain.value.RefValue;

public class RefType implements IType {
    IType inner;

    public RefType(IType inner) {
        this.inner = inner;
    }

    public IType getInner() {
        return inner;
    }

    @Override
    public boolean equals(IType other) {
        if (other instanceof RefType) {
            return inner.equals(((RefType) other).getInner());
        }
        return false;
    }

    @Override
    public IValue defaultValue() {
        return new RefValue(0,inner);
    }

    @Override
    public String toString() {
        return "Reference to "+inner.toString();
    }
}
