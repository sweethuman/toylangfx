package tech.sweethuman.domain.value;

import tech.sweethuman.domain.vartype.IType;

public interface IValue {
    IType getType();

    IValue copy();

    boolean equals(IValue other);
}
