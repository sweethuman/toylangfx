package tech.sweethuman.domain.expression;

import tech.sweethuman.adt.IHeap;
import tech.sweethuman.adt.MyIDictionary;
import tech.sweethuman.domain.GeneralException;
import tech.sweethuman.domain.value.IValue;
import tech.sweethuman.domain.vartype.IType;

public interface IExpression {
    IValue evaluate(MyIDictionary<String, IValue> symbolTable, IHeap<IValue> memoryHeap) throws GeneralException;

    IType typecheck(MyIDictionary<String, IType> typeEnv) throws GeneralException;
}
