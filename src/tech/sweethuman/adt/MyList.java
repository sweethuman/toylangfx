package tech.sweethuman.adt;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class represents a generic list
 *
 * @param <T> The type of the elements stored
 */
public class MyList<T> implements MyIList<T> {
    ArrayList<T> list;

    public MyList() {
        this.list = new ArrayList<>();
    }

    @Override
    public void add(T el) {
        list.add(el);
    }

    @Override
    public void empty() {
        list.clear();
    }

    @Override
    public Collection<T> getContent() {
        return list;
    }

    @Override
    public void setContent(Collection<T> newContent) {
        list = new ArrayList<>(newContent);
    }

    @Override
    public ArrayList<T> toArrayList() {
        return list;
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
