package tech.sweethuman.domain.statement;

import tech.sweethuman.adt.MyIDictionary;
import tech.sweethuman.domain.GeneralException;
import tech.sweethuman.domain.IProgramState;
import tech.sweethuman.domain.vartype.IType;

public interface IStatement {
    IProgramState execute(IProgramState state) throws GeneralException;

    MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws GeneralException;
}
