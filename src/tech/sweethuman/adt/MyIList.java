package tech.sweethuman.adt;

import java.util.ArrayList;
import java.util.Collection;

public interface MyIList<T> {
    /**
     * This method adds an element to the list
     *
     * @param el The element to be added
     */
    void add(T el);

    /**
     * This method clears the list
     */
    void empty();

    Collection<T> getContent();

    void setContent(Collection<T> newContent);

    ArrayList<T> toArrayList();
}
