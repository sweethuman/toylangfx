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
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenFileStatement implements IStatement {
    final private IExpression exp;

    public OpenFileStatement(IExpression Expression) {
        this.exp = Expression;
    }

    @Override
    public IProgramState execute(IProgramState state) throws GeneralException {
        IValue fileName = this.exp.evaluate(state.getSymbolTable(), state.getMemoryHeap());
        if (fileName.getType().equals(new StringType())) {
            StringValue file = (StringValue) fileName;
            MyIDictionary<String, BufferedReader> file_table = state.getFileTable();
            if (file_table.isDefined(file.toString()))
                throw new GeneralException("Already existing file");
            else
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(file.toString()));
                    file_table.add(file.toString(), reader);
                } catch (FileNotFoundException exception) {
                    throw new GeneralException("File not found");
                }
        } else throw new GeneralException("The expression is not a string type");
        return null;
    }

    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws GeneralException {
        if (exp.typecheck(typeEnv).equals(new StringType())) {
            return typeEnv;
        }
        throw new GeneralException("Close File Statement doesn't have String as a File Name");
    }

    @Override
    public String toString() {
        return ("open(" + exp.toString() + ")").replaceAll("\n", "\\\\n");
    }

}
