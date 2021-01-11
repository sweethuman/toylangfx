package tech.sweethuman.domain.expression;

import tech.sweethuman.adt.IHeap;
import tech.sweethuman.adt.MyIDictionary;
import tech.sweethuman.domain.GeneralException;
import tech.sweethuman.domain.value.IValue;
import tech.sweethuman.domain.vartype.IType;

public class Var implements IExpression {
    String symbolName;

    public Var(String symbolName) {
        this.symbolName = symbolName;
    }

    @Override
    public IValue evaluate(MyIDictionary<String, IValue> symbolTable, IHeap<IValue> memoryHeap) throws GeneralException {
        var value = symbolTable.get(symbolName);
        if (value == null) {
            throw new GeneralException("Variable \"" + symbolName + "\" has not been declared yet!");
        }
        return value;
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) {
        return typeEnv.get(symbolName);
    }

    @Override
    public String toString() {
        return symbolName;
    }
}
