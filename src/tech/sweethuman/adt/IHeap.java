package tech.sweethuman.adt;

public interface IHeap<TValue> extends MyIDictionary<Integer, TValue> {
    int add(TValue value);

}
