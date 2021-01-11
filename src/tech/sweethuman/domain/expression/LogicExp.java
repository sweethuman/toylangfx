package tech.sweethuman.domain.expression;

import tech.sweethuman.adt.IHeap;
import tech.sweethuman.adt.MyIDictionary;
import tech.sweethuman.domain.GeneralException;
import tech.sweethuman.domain.value.BoolValue;
import tech.sweethuman.domain.value.IValue;
import tech.sweethuman.domain.vartype.BoolType;
import tech.sweethuman.domain.vartype.IType;

public class LogicExp implements IExpression {
    IExpression exp1;
    IExpression exp2;
    int op; // 1 - and, 2 - or

    public LogicExp(IExpression exp1, IExpression exp2, int op) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.op = op;
    }

    @Override
    public IValue evaluate(MyIDictionary<String, IValue> symbolTable, IHeap<IValue> memoryHeap) throws GeneralException {
        var value1 = exp1.evaluate(symbolTable, memoryHeap);
        if (!value1.getType().equals(new BoolType())) {
            throw new GeneralException("First operator type is not Boolean");
        }
        var value2 = exp2.evaluate(symbolTable, memoryHeap);
        if (!value2.getType().equals(new BoolType())) {
            throw new GeneralException("Second operator type is not Boolean");
        }
        var bool1 = ((BoolValue) value1).getValue();
        var bool2 = ((BoolValue) value2).getValue();
        return switch (op) {
            case 1 -> new BoolValue(bool1 && bool2);
            case 2 -> new BoolValue(bool1 || bool2);
            default -> throw new GeneralException("Not one of our operators");
        };
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws GeneralException {
        IType typ1, typ2;
        typ1 = exp1.typecheck(typeEnv);
        typ2 = exp2.typecheck(typeEnv);
        if (typ1.equals(new BoolType())) {
            if (typ2.equals(new BoolType())) {
                return new BoolType();
            } else throw new GeneralException("second operand is not an boolean");
        } else
            throw new GeneralException("first operand is not an boolean");
    }
}

