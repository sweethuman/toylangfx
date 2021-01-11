package tech.sweethuman.domain.statement;

import tech.sweethuman.adt.MyDictionary;
import tech.sweethuman.adt.MyIDictionary;
import tech.sweethuman.domain.GeneralException;
import tech.sweethuman.domain.IProgramState;
import tech.sweethuman.domain.expression.IExpression;
import tech.sweethuman.domain.value.BoolValue;
import tech.sweethuman.domain.value.IValue;
import tech.sweethuman.domain.vartype.BoolType;
import tech.sweethuman.domain.vartype.IType;

public class WhileStmt implements IStatement {
    private final IExpression conditionExpression;
    private final IStatement blockStatement;

    public WhileStmt(IExpression expression, IStatement blockStatement) {
        this.conditionExpression = expression;
        this.blockStatement = blockStatement;
    }

    @Override
    public IProgramState execute(IProgramState state) throws GeneralException {
        IValue conditionValue = conditionExpression.evaluate(state.getSymbolTable(), state.getMemoryHeap());

        if (!(conditionValue.getType() instanceof BoolType))
            throw new GeneralException(conditionExpression + "is not of BoolType");

        if (((BoolValue) conditionValue).getValue()) {
            state.getExecutionStack().push(this);
            state.getExecutionStack().push(blockStatement);
        }
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws GeneralException {
        if (conditionExpression.typecheck(typeEnv).equals(new BoolType())) {
            var innerTypeEnv = new MyDictionary<String, IType>();
            innerTypeEnv.setContent(typeEnv.toMap());
            blockStatement.typecheck(innerTypeEnv);
            return typeEnv;
        }
        throw new GeneralException("the wh argument is not the same type as the variable");
    }

    @Override
    public String toString() {
        return "while (" + conditionExpression + ") do (" + blockStatement + ")";
    }
}
