package tech.sweethuman.domain.expression;

import tech.sweethuman.adt.IHeap;
import tech.sweethuman.adt.MyIDictionary;
import tech.sweethuman.domain.GeneralException;
import tech.sweethuman.domain.value.BoolValue;
import tech.sweethuman.domain.value.IValue;
import tech.sweethuman.domain.value.IntValue;
import tech.sweethuman.domain.vartype.BoolType;
import tech.sweethuman.domain.vartype.IType;
import tech.sweethuman.domain.vartype.IntType;

public class RelExp implements IExpression {
    IExpression exp1;
    IExpression exp2;
    String op;

    public RelExp(IExpression exp1, String op, IExpression exp2) {
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
        return switch (op) {
            case "<" -> new BoolValue(number1 < number2);
            case ">" -> new BoolValue(number1 > number2);
            case "<=" -> new BoolValue(number1 <= number2);
            case ">=" -> new BoolValue(number1 >= number2);
            case "==" -> new BoolValue(number1 == number2);
            case "!=" -> new BoolValue(number1 != number2);
            default -> throw new GeneralException("Your desired operator is not one of our mathematical operators. Please do better!");
        };
    }

    @Override
    public String toString() {
        return switch (op) {
            case "<" -> this.exp1 + "<" + this.exp2;
            case ">" -> this.exp1 + ">" + this.exp2;
            case "<=" -> this.exp1 + "<=" + this.exp2;
            case ">=" -> this.exp1 + ">=" + this.exp2;
            case "==" -> this.exp1 + "==" + this.exp2;
            case "!=" -> this.exp1 + "!=" + this.exp2;
            default -> this.exp1 + "?Unknown Operator?" + this.exp2;
        };
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws GeneralException {
        IType typ1, typ2;
        typ1 = exp1.typecheck(typeEnv);
        typ2 = exp2.typecheck(typeEnv);
        if (typ1.equals(new IntType())) {
            if (typ2.equals(new IntType())) {
                return new BoolType();
            } else throw new GeneralException("second operand is not an integer");
        } else
            throw new GeneralException("first operand is not an integer");
    }
}
