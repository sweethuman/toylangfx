package tech.sweethuman.domain.statement;


import tech.sweethuman.adt.MyIDictionary;
import tech.sweethuman.domain.GeneralException;
import tech.sweethuman.domain.IProgramState;
import tech.sweethuman.domain.expression.IExpression;
import tech.sweethuman.domain.value.IValue;
import tech.sweethuman.domain.value.StringValue;
import tech.sweethuman.domain.vartype.IType;
import tech.sweethuman.domain.vartype.StringType;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseFileStatement implements IStatement {
    final private IExpression exp;

    public CloseFileStatement(IExpression Expression) {
        this.exp = Expression;
    }

    @Override
    public IProgramState execute(IProgramState state) throws GeneralException {
        IValue fileName = this.exp.evaluate(state.getSymbolTable(), state.getMemoryHeap());
        if (fileName.getType().equals(new StringType())) {
            StringValue newValue = (StringValue) fileName;
            MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();
            if (fileTable.isDefined(newValue.toString())) {
                BufferedReader reader = state.getFileTable().get(newValue.toString());
                try {
                    reader.close();
                    fileTable.remove(newValue.toString());
                } catch (IOException exception) {
                    throw new GeneralException("Io Exception");
                }
            } else throw new GeneralException("Undefined file name");
        } else throw new GeneralException("File name is not a string");
        return null;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws GeneralException {
        if (exp.typecheck(typeEnv).equals(new StringType())) {
            return typeEnv;
        }
        throw new GeneralException("Close File Statement doesn't have String as a File Name");
    }

    @Override
    public String toString() {
        return ("close(" + exp.toString() + ")").replaceAll("\n", "\\\\n");
    }

}
