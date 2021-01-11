package tech.sweethuman.domain.statement;

import tech.sweethuman.adt.MyDictionary;
import tech.sweethuman.adt.MyIDictionary;
import tech.sweethuman.adt.MyStack;
import tech.sweethuman.domain.GeneralException;
import tech.sweethuman.domain.IProgramState;
import tech.sweethuman.domain.ProgramState;
import tech.sweethuman.domain.value.IValue;
import tech.sweethuman.domain.vartype.IType;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ForkStmt implements IStatement {
    private final IStatement blockStatement;

    public ForkStmt(IStatement blockStatement) {
        this.blockStatement = blockStatement;
    }

    @Override
    public IProgramState execute(IProgramState state) throws GeneralException {
        MyIDictionary<String, IValue> newSymbolTable = new MyDictionary<>();
        newSymbolTable.setContent(
                state.getSymbolTable().toMap().entrySet().stream()
                        .map((Map.Entry<String, IValue> entry) -> new AbstractMap.SimpleEntry<>(entry.getKey(), entry.getValue().copy()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

        var stack = new MyStack<IStatement>();
        stack.push(blockStatement);
        return new ProgramState(stack, newSymbolTable, state.getOutput(), state.getFileTable(), state.getMemoryHeap());
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws GeneralException {
        var innerTypeEnv = new MyDictionary<String, IType>();
        innerTypeEnv.setContent(typeEnv.toMap());
        blockStatement.typecheck(innerTypeEnv);
        return typeEnv;
    }

    @Override
    public String toString() {
        return "fork(" + blockStatement.toString() + ")";
    }
}