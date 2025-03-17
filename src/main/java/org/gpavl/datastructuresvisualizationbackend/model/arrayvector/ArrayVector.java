package org.gpavl.datastructuresvisualizationbackend.model.arrayvector;

import lombok.Getter;

@Getter
public class ArrayVector {

    private static final int INITIAL_CAPACITY = 10;

    private String[] array;
    private int capacity;
    private int count;

    public ArrayVector() {
        capacity = INITIAL_CAPACITY;
        count = 0;
        array = new String[capacity];
    }

    public ArrayVector(int amount, String element) {
        capacity = Math.max(amount, INITIAL_CAPACITY);
        array = new String[capacity];
        count = amount;

        for (int i = 0; i < amount; i++) {
            array[i] = element;
        }
    }

    public int size() {
        return count;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public void clear() {
        count = 0;
    }

    public String get(int index) {
        if (index < 0 || index >= count) {
            throw new IllegalArgumentException("get: index out of range");
        }

        return array[index];
    }

    public void set(int index, String element) {
        if (index < 0 || index >= count) {
            throw new IllegalArgumentException("set: index out of range");
        }

        array[index] = element;
    }

    public void add(String element) {
        insertAt(count, element);
    }

    public void insertAt(int index, String element) {
        if (count == capacity) {
            extendCapacity();
        }

        if (index < 0 || index > count) {
            throw new IllegalArgumentException("insertAt: index out of range");
        }

        for (int i = count; i > index; i--) {
            array[i] = array[i - 1];
        }

        array[index] = element;
        count++;
    }

    public void removeAt(int index) {
        if (index < 0 || index > count) {
            throw new IllegalArgumentException("insertAt: index out of range");
        }

        for (int i = index; i < count - 1; i++) {
            array[i] = array[i + 1];
        }
        count--;
    }

    private void extendCapacity() {
        String[] oldArray = array;
        capacity *= 2;
        array = new String[capacity];

        if (count >= 0) System.arraycopy(oldArray, 0, array, 0, count);
    }
}
