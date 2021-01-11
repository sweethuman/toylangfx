package tech.sweethuman.domain.expression;

import tech.sweethuman.adt.IHeap;
import tech.sweethuman.adt.MyIDictionary;
import tech.sweethuman.domain.value.IValue;
import tech.sweethuman.domain.vartype.IType;

public class ValueExp implements IExpression {
    IValue e;

    public ValueExp(IValue e) {
        this.e = e;
    }

    @Override
    public IValue evaluate(MyIDictionary<String, IValue> symbolTable, IHeap<IValue> memoryHeap) {
        return e;
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) {
        return e.getType();
    }

    @Override
    public String toString() {
        return e.toString();
    }
}
