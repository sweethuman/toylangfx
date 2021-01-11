package tech.sweethuman.domain.value;

import tech.sweethuman.domain.vartype.IType;
import tech.sweethuman.domain.vartype.StringType;

public class StringValue implements IValue {
    String val;

    public StringValue(String val) {
        this.val = val;
    }

    @Override
    public IType getType() {
        return new StringType();
    }

    @Override
    public IValue copy() {
        return new StringValue(val);
    }

    @Override
    public boolean equals(IValue other) {
        return this.val.equals(((StringValue) other).val);
    }

    @Override
    public String toString() {
        return val;
    }
}
