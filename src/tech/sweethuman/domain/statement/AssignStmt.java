package tech.sweethuman.domain.statement;

import tech.sweethuman.adt.MyIDictionary;
import tech.sweethuman.domain.GeneralException;
import tech.sweethuman.domain.IProgramState;
import tech.sweethuman.domain.expression.IExpression;
import tech.sweethuman.domain.vartype.IType;

public class AssignStmt implements IStatement {
    final private String symbolName;
    final private IExpression exp;

    public AssignStmt(String symbolName, IExpression exp) {
        this.symbolName = symbolName;
        this.exp = exp;
    }

    @Override
    public IProgramState execute(IProgramState state) throws GeneralException {
        var symbolTable = state.getSymbolTable();

        if (symbolTable.isDefined(symbolName)) {
            var val = exp.evaluate(symbolTable, state.getMemoryHeap());
            var type = (symbolTable.get(symbolName)).getType();
            if (val.getType().equals(type)) {
                symbolTable.update(symbolName, val);
            } else {
                throw new GeneralException("Variable" + symbolName + " and type of expression" + exp + " do not match");
            }
        } else throw new GeneralException("Variable" + symbolName + " was not declared before statement" + this);
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws GeneralException {
        IType typevar = typeEnv.get(symbolName);
        IType typexp = exp.typecheck(typeEnv);
        if (typevar.equals(typexp)) return typeEnv;
        else throw new GeneralException("Assignment: right hand side and left hand side have different types ");
    }

    @Override
    public String toString() {
        return "" + symbolName + "=" + exp.toString() + "";
    }
}
