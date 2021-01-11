package tech.sweethuman.adt;

import java.util.Map;

/**
 * A dictionary of generic elements with generic keys
 *
 * @param <K> The type of the key
 * @param <T> The type of the element
 */
public interface MyIDictionary<K, T> {
    void add(K key, T elem);

    T get(K key);

    boolean isDefined(K key);

    void setContent(Map<K, T> newContent);

    void update(K key, T newelem);

    void remove(K key);

    void clear();

    Map<K, T> toMap();
}
