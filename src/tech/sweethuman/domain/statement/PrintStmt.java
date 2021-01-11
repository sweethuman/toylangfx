package tech.sweethuman.domain.statement;


import tech.sweethuman.adt.MyIDictionary;
import tech.sweethuman.domain.GeneralException;
import tech.sweethuman.domain.IProgramState;
import tech.sweethuman.domain.expression.IExpression;
import tech.sweethuman.domain.vartype.IType;

public class PrintStmt implements IStatement {
    final private IExpression exp;

    public PrintStmt(IExpression exp) {
        this.exp = exp;
    }

    @Override
    public IProgramState execute(IProgramState state) throws GeneralException {
        var out = state.getOutput();
        var symbolTable = state.getSymbolTable();
        out.add(exp.evaluate(symbolTable, state.getMemoryHeap()));
        return null;
    }

    @Override
    public String toString() {
        return ("print(" + exp.toString() + ")").replaceAll("\n", "\\\\n");
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws GeneralException {
        exp.typecheck(typeEnv);
        return typeEnv;
    }
}
