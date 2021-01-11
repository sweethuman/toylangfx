package tech.sweethuman.domain.statement;


import tech.sweethuman.adt.MyIDictionary;
import tech.sweethuman.domain.GeneralException;
import tech.sweethuman.domain.IProgramState;
import tech.sweethuman.domain.vartype.IType;

public class Nop implements IStatement {
    @Override
    public IProgramState execute(IProgramState state) throws GeneralException {
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws GeneralException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "NO-OP";
    }
}
