package tech.sweethuman.domain.statement;


import tech.sweethuman.adt.MyIDictionary;
import tech.sweethuman.domain.GeneralException;
import tech.sweethuman.domain.IProgramState;
import tech.sweethuman.domain.vartype.IType;

public class CompStmt implements IStatement {
    final private IStatement first;
    final private IStatement second;

    public CompStmt(IStatement first, IStatement second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return "(" + this.first.toString() + ";" + this.second.toString() + ")";
    }

    @Override
    public IProgramState execute(IProgramState state) {
        var stack = state.getExecutionStack();
        stack.push(second);
        stack.push(first);
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws GeneralException {
        return second.typecheck(first.typecheck(typeEnv));
    }
}
