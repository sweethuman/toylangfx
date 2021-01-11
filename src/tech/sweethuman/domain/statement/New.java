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

public class New implements IStatement {
    private final String variableName;
    private final IExpression expression;

    public New(String variableName, IExpression expression) {
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

        IValue value = expression.evaluate(symbolTable, state.getMemoryHeap());

        if (!value.getType().equals(((RefValue) variable).getLocationType()))
            throw new GeneralException(value + " is of of " + ((RefValue) variable).getLocationType() + " type");

        int address = memoryHeap.add(value);
        symbolTable.update(variableName, new RefValue(address, ((RefValue) variable).getLocationType()));
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws GeneralException {
        IType typevar = typeEnv.get(variableName);
        IType typexp = expression.typecheck(typeEnv);
        if (typevar.equals(new RefType(typexp))) return typeEnv;
        else throw new GeneralException("NEW stmt: right hand side and left hand side have different types ");
    }

    @Override
    public String toString() {
        return "new(" + variableName + "," + expression + ")";
    }
}
