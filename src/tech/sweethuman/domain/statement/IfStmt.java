package tech.sweethuman.domain.statement;


import tech.sweethuman.adt.MyDictionary;
import tech.sweethuman.adt.MyIDictionary;
import tech.sweethuman.domain.GeneralException;
import tech.sweethuman.domain.IProgramState;
import tech.sweethuman.domain.expression.IExpression;
import tech.sweethuman.domain.value.BoolValue;
import tech.sweethuman.domain.vartype.BoolType;
import tech.sweethuman.domain.vartype.IType;

public class IfStmt implements IStatement {
    final private IExpression exp;
    final private IStatement thenStmnt;
    final private IStatement elseStmnt;

    public IfStmt(IExpression exp, IStatement thenStmnt, IStatement elseStmnt) {
        this.exp = exp;
        this.elseStmnt = elseStmnt;
        this.thenStmnt = thenStmnt;
    }

    @Override
    public IProgramState execute(IProgramState state) throws GeneralException {
        var stack = state.getExecutionStack();
        var symbolTable = state.getSymbolTable();

        var val = exp.evaluate(symbolTable, state.getMemoryHeap());
        if (val.getType().equals(new BoolType())) {
            var result = ((BoolValue) val).getValue();
            if (result) {
                stack.push(thenStmnt);
            } else {
                if (this.elseStmnt != null) stack.push(elseStmnt);
            }
        } else throw new GeneralException("Expression type is not a boolean");
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws GeneralException {
        IType typexp = exp.typecheck(typeEnv);
        if (typexp.equals(new BoolType())) {
            var thenTypeEnv = new MyDictionary<String, IType>();
            thenTypeEnv.setContent(typeEnv.toMap());
            var elseTypeEnv = new MyDictionary<String, IType>();
            elseTypeEnv.setContent(typeEnv.toMap());
            thenStmnt.typecheck(thenTypeEnv);
            elseStmnt.typecheck(elseTypeEnv);
            return typeEnv;
        } else throw new GeneralException("The condition of IF has not the type bool");
    }

    @Override
    public String toString() {
        return "if (" + exp.toString() + ") then (" + thenStmnt.toString() + ") else (" + elseStmnt.toString() + ")";
    }
}
