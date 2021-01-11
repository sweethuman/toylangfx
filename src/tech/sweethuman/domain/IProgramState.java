package tech.sweethuman.domain;

import tech.sweethuman.adt.IHeap;
import tech.sweethuman.adt.MyIDictionary;
import tech.sweethuman.adt.MyIList;
import tech.sweethuman.adt.MyIStack;
import tech.sweethuman.domain.statement.IStatement;
import tech.sweethuman.domain.value.IValue;

import java.io.BufferedReader;

public interface IProgramState {
    Integer getId();

    MyIStack<IStatement> getExecutionStack();

    MyIList<IValue> getOutput();

    MyIDictionary<String, IValue> getSymbolTable();

    MyIDictionary<String, BufferedReader> getFileTable();
    IHeap<IValue> getMemoryHeap();

    boolean isNotCompleted();

    IProgramState executeOneStep() throws GeneralException;
}
