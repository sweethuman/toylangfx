package tech.sweethuman.domain.statement;


import tech.sweethuman.adt.MyIDictionary;
import tech.sweethuman.domain.GeneralException;
import tech.sweethuman.domain.IProgramState;
import tech.sweethuman.domain.expression.IExpression;
import tech.sweethuman.domain.value.IValue;
import tech.sweethuman.domain.value.IntValue;
import tech.sweethuman.domain.value.StringValue;
import tech.sweethuman.domain.vartype.IType;
import tech.sweethuman.domain.vartype.IntType;
import tech.sweethuman.domain.vartype.StringType;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements IStatement {
    IExpression exp;
    String var;

    public ReadFileStatement(IExpression Expression, String VariableName) {
        this.exp = Expression;
        this.var = VariableName;
    }

    @Override
    public IProgramState execute(IProgramState state) throws GeneralException {
        MyIDictionary<String, IValue> dictionary = state.getSymbolTable();
        if (!dictionary.isDefined(var)) {
            throw new GeneralException("undefined variable");
        }
        if (!dictionary.get(var).getType().equals(new IntType())) {
            throw new GeneralException("invalid type");
        }
        IValue value = exp.evaluate(dictionary, state.getMemoryHeap());
        if (value.getType().equals(new StringType())) {
            StringValue name = (StringValue) value;
            MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();
            if (!fileTable.isDefined(name.toString())) {
                throw new GeneralException("file name undefined");
            }
            BufferedReader reader = fileTable.get(name.toString());
            try {
                String line = reader.readLine();
                IntValue newValue = new IntValue(0);
                if (line != null) {
                    newValue = new IntValue(Integer.parseInt(line));
                }
                dictionary.update(var, newValue);
            } catch (IOException exception) {
                throw new GeneralException("Error reading file!");
            } catch (NumberFormatException exception) {
                throw new GeneralException("Could not read a valid number from file " + name.toString());
            }
        }
        return null;
    }

    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws GeneralException {
        if (exp.typecheck(typeEnv).equals(new StringType()) && typeEnv.get(var).equals(new IntType())) {
            return typeEnv;
        }
        throw new GeneralException("Close File Statement doesn't have String as a File Name");
    }

    @Override
    public String toString() {
        return ("read(" + exp.toString() + ", " + var + ")").replaceAll("\n", "\\\\n");
    }
}
