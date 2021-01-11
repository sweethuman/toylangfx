package tech.sweethuman.domain.statement;

import tech.sweethuman.adt.IHeap;
import tech.sweethuman.adt.MyIDictionary;
import tech.sweethuman.domain.GeneralException;
import tech.sweethuman.domain.IProgramState;
import tech.sweethuman.domain.expression.IExpression;
import tech.sweethuman.domain.value.IValue;
import tech.sweethuman.domain.value.RefValue;
import tech.sweethuman.domain.vartype.IType;
import tech.sweethuman.domain.vartype.RefType;

public class WH implements IStatement {
    private final String variableName;
    private final IExpression expression;

    public WH(String variableName, IExpression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public IProgramState execute(IProgramState state) throws GeneralException {
        MyIDictionary<String, IValue> symbolTable = state.getSymbolTable();
        IHeap<IValue> memoryHeap = state.getMemoryHeap();

        if (!symbolTable.isDefined(variableName))
            throw new GeneralException(variableName + " is not defined");

        IValue variable = symbolTable.get(variableName);
        if (!(variable.getType() instanceof RefType))
            throw new GeneralException(variableName + "is not of type RefType");

        int address = ((RefValue) variable).getAddress();

        if (!(memoryHeap.isDefined(address)))
            throw new GeneralException(variableName + "is not defined in heap");

        IValue value = expression.evaluate(symbolTable, memoryHeap);

        if (!value.getType().equals(((RefValue) variable).getLocationType()))
            throw new GeneralException(value + " is of of " + ((RefValue) variable).getLocationType() + " type");

        memoryHeap.update(address, value);
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws GeneralException {
        var x = expression.typecheck(typeEnv);
        var y = typeEnv.get(variableName);
        if (y.equals(new RefType(x))) {
            return typeEnv;
        }
        throw new GeneralException("the wh argument is not the same type as the variable");
    }

    @Override
    public String toString() {
        return "writeHeap(" + variableName + "," + expression + ")";
    }
}
