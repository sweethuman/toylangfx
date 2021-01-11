package tech.sweethuman.domain.statement;


import tech.sweethuman.adt.MyIDictionary;
import tech.sweethuman.domain.GeneralException;
import tech.sweethuman.domain.IProgramState;
import tech.sweethuman.domain.vartype.IType;

public class VarDecl implements IStatement {
    final private String name;
    final private IType type;

    public VarDecl(String name, IType type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public IProgramState execute(IProgramState state) throws GeneralException {
        var symbolTable = state.getSymbolTable();
        var value = type.defaultValue();
        if (symbolTable.isDefined(name)) {
            throw new GeneralException("Variable named " + name + " already exists!");
        }
        symbolTable.add(name, value);
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws GeneralException {
        typeEnv.add(name, type);
        return typeEnv;
    }

    @Override
    public String toString() {
        return "" + type + " " + name + "";
    }
}
