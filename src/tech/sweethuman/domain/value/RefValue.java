package tech.sweethuman.domain.value;

import tech.sweethuman.domain.vartype.IType;
import tech.sweethuman.domain.vartype.RefType;

public class RefValue implements IValue {
    int address;
    IType locationType;

    public RefValue(int address, IType locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public int getAddress() {
        return address;
    }

    public IType getLocationType() {
        return locationType;
    }

    @Override
    public IType getType() {
        return new RefType(locationType);
    }

    @Override
    public IValue copy() {
        return new RefValue(address, locationType);
    }

    @Override
    public boolean equals(IValue other) {
        return address == ((RefValue) other).address && locationType == ((RefValue) other).locationType;
    }

    @Override
    public String toString() {
        return "(" + address + ", " + locationType + ")";
    }
}
