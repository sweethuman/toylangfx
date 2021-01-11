package tech.sweethuman.domain.expression;

import tech.sweethuman.adt.IHeap;
import tech.sweethuman.adt.MyIDictionary;
import tech.sweethuman.domain.GeneralException;
import tech.sweethuman.domain.value.IValue;
import tech.sweethuman.domain.value.IntValue;
import tech.sweethuman.domain.vartype.IType;
import tech.sweethuman.domain.vartype.IntType;

public class ArithExp implements IExpression {
    IExpression exp1;
    IExpression exp2;
    int op; //1-plus, 2-minus, 3-star, 4-divide

    public ArithExp(int op, IExpression exp1, IExpression exp2) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.op = op;
    }

    @Override
    public IValue evaluate(MyIDictionary<String, IValue> symbolTable, IHeap<IValue> memoryHeap) throws GeneralException {
        var value1 = exp1.evaluate(symbolTable, memoryHeap);
        if (!value1.getType().equals(new IntType())) {
            throw new GeneralException("First Operand is not an Integer");
        }
        var value2 = exp2.evaluate(symbolTable, memoryHeap);
        if (!value2.getType().equals(new IntType())) {
            throw new GeneralException("Second Operand is not an Integer");
        }
        var number1 = ((IntValue) value1).getValue();
        var number2 = ((IntValue) value2).getValue();
        switch (op) {
            case 1:
                return new IntValue(number1 + number2);
            case 2:
                return new IntValue(number1 - number2);
            case 3:
                return new IntValue(number1 * number2);
            case 4: {
                if (number2 == 0) throw new GeneralException("Division by Zero, ugh, again?");
                else return new IntValue(number1 / number2);
            }
            default:
                throw new GeneralException("Your desired operator is not one of our mathematical operators. Please do better!");
        }
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws GeneralException {
        IType typ1, typ2;
        typ1 = exp1.typecheck(typeEnv);
        typ2 = exp2.typecheck(typeEnv);
        if (typ1.equals(new IntType())) {
            if (typ2.equals(new IntType())) {
                return new IntType();
            } else throw new GeneralException("second operand is not an integer");
        } else
            throw new GeneralException("first operand is not an integer");
    }

    @Override
    public String toString() {
        return switch (op) {
            case 1 -> this.exp1 + "+" + this.exp2;
            case 2 -> this.exp1 + "-" + this.exp2;
            case 3 -> this.exp1 + "*" + this.exp2;
            case 4 -> this.exp1 + "/" + this.exp2;
            default -> this.exp1 + "?Unknown Operator?" + this.exp2;
        };
    }
}
