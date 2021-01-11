package tech.sweethuman.adt;

public class Heap<TValue> extends MyDictionary<Integer, TValue> implements IHeap<TValue> {
    private int freeAddresses = 0;

    @Override
    public int add(TValue value) {
        add(++freeAddresses, value);
        return freeAddresses;
    }
}
