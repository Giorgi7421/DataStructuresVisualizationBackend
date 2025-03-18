package org.gpavl.datastructuresvisualizationbackend.model.arrayvector;

import lombok.Getter;
import lombok.Setter;
import org.gpavl.datastructuresvisualizationbackend.model.MemorySnapshotDto;

import java.util.*;

@Getter
@Setter
public class ArrayVector {

    private static final int INITIAL_CAPACITY = 10;

    private List<MemorySnapshotDto> steps;

    private String[] array;
    private int capacity;
    private int count;

    public ArrayVector() {
        capacity = INITIAL_CAPACITY;
        count = 0;
        array = new String[capacity];
        steps = new ArrayList<>();
    }

    public ArrayVector(int amount, String element) {
        steps = new ArrayList<>();

        capacity = Math.max(amount, INITIAL_CAPACITY);
        array = new String[capacity];
        count = amount;

        updateMemory("capacity", capacity, true);
        updateMemory("count", count, true);
        updateMemory("array", toList(), true);

        for (int i = 0; i < amount; i++) {
            array[i] = element;
            updateMemory("array", toList(), true);
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
            updateMemory("array", toList(), true);
        }

        array[index] = element;
        updateMemory("array", toList(), true);
        count++;
        updateMemory("count", count, true);
    }

    public void removeAt(int index) {
        if (index < 0 || index > count) {
            throw new IllegalArgumentException("insertAt: index out of range");
        }

        for (int i = index; i < count - 1; i++) {
            array[i] = array[i + 1];
            updateMemory("array", toList(), false);
        }
        count--;
        updateMemory("count", count, true);
    }

    private void extendCapacity() {
        String[] oldArray = array;
        updateMemory("oldArray", toList(), false);

        capacity *= 2;
        updateMemory("capacity", capacity, true);

        array = new String[capacity];
        updateMemory("array", toList(), true);

        for (int i = 0; i < count; i++) {
            array[i] = oldArray[i];
            updateMemory("array", toList(), true);
            if (i == count - 1) {
                steps.getLast().setMessage("Extending the capacity is completed");
            }
        }
    }

    private List<String> toList() {
        return Arrays.stream(array).filter(Objects::nonNull).toList();
    }

    private void updateMemory(String variableName, Object variableValue, boolean isInstanceVariable) {
        MemorySnapshotDto currentMemorySnapshot;

        if (!steps.isEmpty()) {
            currentMemorySnapshot = new MemorySnapshotDto(steps.getLast());
        }else {
            currentMemorySnapshot = new MemorySnapshotDto();
        }

        if (isInstanceVariable) {
            currentMemorySnapshot.getInstanceVariables().put(variableName, variableValue);
        }else {
            currentMemorySnapshot.getLocalVariables().put(variableName, variableValue);
        }

        steps.add(currentMemorySnapshot);
    }
}
