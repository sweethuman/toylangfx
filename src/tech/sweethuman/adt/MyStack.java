package tech.sweethuman.adt;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * This class represents a generic stack
 *
 * @param <T> The type of the elements stored
 */
public class MyStack<T> implements MyIStack<T> {
    LinkedList<T> stack;

    public MyStack() {
        this.stack = new LinkedList<>();
    }

    @Override
    public T pop() {
        return stack.pop();
    }

    @Override
    public void push(T el) {
        stack.push(el);
    }

    @Override
    public boolean empty() {
        return stack.isEmpty();
    }

    @Override
    public ArrayList<T> toArrayList() {
        return new ArrayList<>(stack);
    }

    @Override
    public void clear() {
        stack.clear();
    }

    @Override
    public String toString() {
        return stack.toString();
    }
}
