package tech.sweethuman.domain.vartype;

import tech.sweethuman.domain.value.IValue;

public interface IType {
    boolean equals(IType other);

    IValue defaultValue();
}
