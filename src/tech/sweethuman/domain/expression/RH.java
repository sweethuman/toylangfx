package tech.sweethuman.domain.expression;

import tech.sweethuman.adt.IHeap;
import tech.sweethuman.adt.MyIDictionary;
import tech.sweethuman.domain.GeneralException;
import tech.sweethuman.domain.value.IValue;
import tech.sweethuman.domain.value.RefValue;
import tech.sweethuman.domain.vartype.IType;
import tech.sweethuman.domain.vartype.RefType;

public class RH implements IExpression {
    private final IExpression expression;

    public RH(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public IValue evaluate(MyIDictionary<String, IValue> table, IHeap<IValue> heap) throws GeneralException {
        IValue value = expression.evaluate(table, heap);

        if (!(value.getType() instanceof RefType))
            throw new GeneralException(value + "is not of RefType");

        int address = ((RefValue) value).getAddress();

        if (!heap.isDefined(address))
            throw new GeneralException(value + " is not defined in the heap");

        return heap.get(address);
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws GeneralException {
        IType typ = expression.typecheck(typeEnv);
        if (typ instanceof RefType) {
            RefType reft = (RefType) typ;
            return reft.getInner();
        } else throw new GeneralException("the rH argument is not a Ref Type");
    }

    @Override
    public String toString() {
        return "readHeap(" + expression + ")";
    }
}
