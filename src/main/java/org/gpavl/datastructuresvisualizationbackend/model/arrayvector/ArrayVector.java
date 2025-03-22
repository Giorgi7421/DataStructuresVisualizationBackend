package org.gpavl.datastructuresvisualizationbackend.model.arrayvector;

import lombok.Getter;
import lombok.Setter;
import org.gpavl.datastructuresvisualizationbackend.model.MemoryHistoryDto;

import java.util.*;

@Getter
@Setter
public class ArrayVector {

    private static final int INITIAL_CAPACITY = 10;

    private MemoryHistoryDto memoryHistory;

    private String[] array;
    private int capacity;
    private int count;

    public ArrayVector() {
        memoryHistory = new MemoryHistoryDto();

        capacity = INITIAL_CAPACITY;
        memoryHistory.addInstanceVariableToMemory("capacity", capacity);

        count = 0;
        memoryHistory.addInstanceVariableToMemory("count", count);

        array = new String[capacity];
        String newAddress = memoryHistory.addNewObjectInAddressMemoryMap(toList());
        memoryHistory.addInstanceVariableToMemory("array", newAddress);
    }

    public ArrayVector(int amount, String element) {
        memoryHistory = new MemoryHistoryDto();

        capacity = Math.max(amount, INITIAL_CAPACITY);
        memoryHistory.addInstanceVariableToMemory("capacity", capacity);

        count = amount;
        memoryHistory.addInstanceVariableToMemory("count", count);

        array = new String[capacity];
        String newAddress = memoryHistory.addNewObjectInAddressMemoryMap(toList());
        memoryHistory.addInstanceVariableToMemory("array", newAddress);

        for (int i = 0; i < amount; i++) {
            array[i] = element;
            updateArrayInMemory();
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
        memoryHistory.addInstanceVariableToMemory("count", count);
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
        updateArrayInMemory();
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
            updateArrayInMemory();
        }

        array[index] = element;
        updateArrayInMemory();
        count++;
        memoryHistory.addInstanceVariableToMemory("count", count);
    }

    public void removeAt(int index) {
        if (index < 0 || index > count) {
            throw new IllegalArgumentException("insertAt: index out of range");
        }

        for (int i = index; i < count - 1; i++) {
            array[i] = array[i + 1];
            updateArrayInMemory();
        }
        count--;
        memoryHistory.addInstanceVariableToMemory("count", count);
    }

    private void extendCapacity() {
        String[] oldArray = array;
        String arrayAddress = memoryHistory.getInstanceVariableAddress("array");
        memoryHistory.addLocalVariableToMemory("oldArray", arrayAddress);

        capacity *= 2;
        memoryHistory.addInstanceVariableToMemory("capacity", capacity);

        array = new String[capacity];
        String newAddress = memoryHistory.addNewObjectInAddressMemoryMap(toList());
        memoryHistory.addInstanceVariableToMemory("array", newAddress);

        for (int i = 0; i < count; i++) {
            array[i] = oldArray[i];
            updateArrayInMemory();
            if (i == count - 1) {
                memoryHistory.getMemorySnapshots().getLast().setMessage("Extending the capacity is completed");
            }
        }

        memoryHistory.freeLocalVariable("oldArray", "Deleted old array to avoid memory leaks");
    }

    private List<String> toList() {
        return Arrays.stream(array).filter(Objects::nonNull).toList();
    }

    private void updateArrayInMemory() {
        String arrayAddress = memoryHistory.getInstanceVariableAddress("array");
        memoryHistory.updateObjectInAddressMemoryMap(arrayAddress, toList());
    }
}
