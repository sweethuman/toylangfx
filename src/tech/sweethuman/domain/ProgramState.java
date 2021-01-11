package tech.sweethuman.domain;

import tech.sweethuman.adt.IHeap;
import tech.sweethuman.adt.MyIDictionary;
import tech.sweethuman.adt.MyIList;
import tech.sweethuman.adt.MyIStack;
import tech.sweethuman.domain.statement.IStatement;
import tech.sweethuman.domain.value.IValue;

import java.io.BufferedReader;
import java.util.concurrent.atomic.AtomicInteger;

public class ProgramState implements IProgramState {
    MyIStack<IStatement> executionStack;
    MyIDictionary<String, IValue> symbolTable;
    MyIList<IValue> output;
    MyIDictionary<String, BufferedReader> fileTable;
    IHeap<IValue> memoryHeap;
    private final int id;

    private static final AtomicInteger programStatesCount = new AtomicInteger(0);

    private static synchronized int getNewProgramId() {
        return programStatesCount.addAndGet(1);
    }


    public ProgramState(MyIStack<IStatement> executionStack, MyIDictionary<String, IValue> symbolTable, MyIList<IValue> output, MyIDictionary<String, BufferedReader> fileTable, IHeap<IValue> memoryHeap) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.output = output;
        this.fileTable = fileTable;
        this.memoryHeap = memoryHeap;
        id = getNewProgramId();
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public MyIStack<IStatement> getExecutionStack() {
        return executionStack;
    }

    @Override
    public MyIList<IValue> getOutput() {
        return output;
    }

    @Override
    public MyIDictionary<String, IValue> getSymbolTable() {
        return symbolTable;
    }

    @Override
    public MyIDictionary<String, BufferedReader> getFileTable() {
        return fileTable;
    }

    @Override
    public IHeap<IValue> getMemoryHeap() {
        return memoryHeap;
    }

    public String stringifyStack() {
        var str = new StringBuilder();
        str.append("\n");
        for (var i : executionStack.toArrayList()) {
            str.append(i.toString());
            str.append("\n");
        }
        return str.toString();
    }

    public String stringifySymbolTable() {
        var str = new StringBuilder();
        str.append("\n");
        symbolTable.toMap().forEach((k, v) -> {
            str.append(k.toString());
            str.append(" --> ");
            str.append(v.toString());
            str.append("\n");
        });
        return str.toString();
    }

    public String stringifyOutput() {
        var str = new StringBuilder();
        str.append("\n");
        for (var i : output.toArrayList()) {
            str.append(i.toString().replaceAll("\n", "\\\\n"));
            str.append("\n");
        }
        return str.toString();
    }

    public String stringifyFileTable() {
        var str = new StringBuilder();
        str.append("\n");
        fileTable.toMap().forEach((k, v) -> {
            str.append(k.toString());
            str.append("\n");
        });
        return str.toString();
    }

    public String stringifyHeap() {
        var str = new StringBuilder();
        str.append("\n");
        memoryHeap.toMap().forEach((k, v) -> {
            str.append(k.toString());
            str.append(" : ");
            str.append(v.toString());
            str.append("\n");
        });
        return str.toString();
    }

    @Override
    public boolean isNotCompleted() {
        return !executionStack.empty();
    }

    @Override
    public IProgramState executeOneStep() throws GeneralException {
        if (executionStack.empty())
            throw new GeneralException("program state stack is empty");
        try {
            return executionStack.pop().execute(this);
        } catch (GeneralException exception) {
            executionStack.clear();
            throw exception;
        }
    }

    @Override
    public String toString() {
        return "ID: " + id + System.getProperty("line.separator") +
                "Execution Stack: " + stringifyStack() + System.getProperty("line.separator") +
                "Symbol Table: " + stringifySymbolTable() + System.getProperty("line.separator") +
                "Output: " + stringifyOutput() + System.getProperty("line.separator") +
                "FileTable:" + stringifyFileTable() + System.getProperty("line.separator") +
                "MemoryHeap:" + stringifyHeap();
    }
}
