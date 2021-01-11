package tech.sweethuman.adt;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class MyDictionary<K, T> implements MyIDictionary<K, T> {
    HashMap<K, T> dict;

    public MyDictionary() {
        this.dict = new HashMap<>();
    }

    @Override
    public void add(K key, T elem) {
        dict.put(key, elem);
    }

    @Override
    public T get(K key) {
        return dict.get(key);
    }

    @Override
    public boolean isDefined(K key) {
        return dict.containsKey(key);
    }

    @Override
    public void update(K key, T newelem) {
        dict.replace(key, newelem);
    }

    @Override
    public void remove(K key) {
        dict.remove(key);
    }

    @Override
    public void clear() {
        dict.clear();
    }

    @Override
    public void setContent(Map<K, T> newContent) {
        dict = new HashMap<>();
        dict.putAll(newContent);
    }

    @Override
    public Map<K, T> toMap() {
        return dict.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public String toString() {
        return dict.toString();
    }
}
