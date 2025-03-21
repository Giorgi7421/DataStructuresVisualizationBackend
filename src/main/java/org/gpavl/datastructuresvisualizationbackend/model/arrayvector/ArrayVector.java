package org.gpavl.datastructuresvisualizationbackend.model.arrayvector;

import lombok.Getter;
import lombok.Setter;
import org.gpavl.datastructuresvisualizationbackend.model.MemorySnapshotDto;

import java.util.*;

import static org.gpavl.datastructuresvisualizationbackend.util.AddressUtils.generateNewAddress;

@Getter
@Setter
public class ArrayVector {

    private static final int INITIAL_CAPACITY = 10;

    private List<MemorySnapshotDto> steps;

    private String[] array;
    private int capacity;
    private int count;

    public ArrayVector() {
        steps = new ArrayList<>();

        capacity = INITIAL_CAPACITY;
        addInstanceVariableToMemory("capacity", capacity);

        count = 0;
        addInstanceVariableToMemory("count", count);

        array = new String[capacity];
        String newAddress = addNewArrayInAddressMemoryMap(toList());
        addInstanceVariableToMemory("array", newAddress);
    }

    public ArrayVector(int amount, String element) {
        steps = new ArrayList<>();

        capacity = Math.max(amount, INITIAL_CAPACITY);
        addInstanceVariableToMemory("capacity", capacity);

        count = amount;
        addInstanceVariableToMemory("count", count);

        array = new String[capacity];
        String newAddress = addNewArrayInAddressMemoryMap(toList());
        addInstanceVariableToMemory("array", newAddress);

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
            updateArrayInMemory();
        }

        array[index] = element;
        updateArrayInMemory();
        count++;
        addInstanceVariableToMemory("count", count);
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
        addInstanceVariableToMemory("count", count);
    }

    private void extendCapacity() {
        String[] oldArray = array;
        String arrayAddress = getArrayAddress();
        addLocalVariableToMemory("oldArray", arrayAddress);

        capacity *= 2;
        addInstanceVariableToMemory("capacity", capacity);

        array = new String[capacity];
        String newAddress = addNewArrayInAddressMemoryMap(toList());
        addInstanceVariableToMemory("array", newAddress);

        for (int i = 0; i < count; i++) {
            array[i] = oldArray[i];
            updateArrayInMemory();
            if (i == count - 1) {
                steps.getLast().setMessage("Extending the capacity is completed");
            }
        }

        freeOldArray();
    }

    private List<String> toList() {
        return Arrays.stream(array).filter(Objects::nonNull).toList();
    }

    private void addLocalVariableToMemory(String variableName, Object variableValue) {
        MemorySnapshotDto currentMemorySnapshot = getCurrentMemorySnapshot();
        currentMemorySnapshot.updateLocalVariable(variableName, variableValue);
        steps.add(currentMemorySnapshot);
    }

    private void addInstanceVariableToMemory(String variableName, Object variableValue) {
        MemorySnapshotDto currentMemorySnapshot = getCurrentMemorySnapshot();
        currentMemorySnapshot.updateInstanceVariable(variableName, variableValue);
        steps.add(currentMemorySnapshot);
    }

    private void freeOldArray() {
        String oldArrayAddress = getOldArrayAddress();

        MemorySnapshotDto memorySnapshotDto = getCurrentMemorySnapshot();
        memorySnapshotDto.removeLocalVariable("oldArray");

        memorySnapshotDto.freeAddress(oldArrayAddress);
        memorySnapshotDto.setMessage("Deleted old array to avoid memory leaks");
        steps.add(memorySnapshotDto);
    }

    private MemorySnapshotDto getCurrentMemorySnapshot() {
        return !steps.isEmpty()
                ? new MemorySnapshotDto(steps.getLast())
                : new MemorySnapshotDto();
    }

    private String addNewArrayInAddressMemoryMap(List<String> array) {
        String newAddress = generateNewAddress();
        MemorySnapshotDto currentMemorySnapshot = getCurrentMemorySnapshot();
        currentMemorySnapshot.updateAddressObjectMap(newAddress, array);
        steps.add(currentMemorySnapshot);
        return newAddress;
    }

    private void updateArrayInMemory() {
        MemorySnapshotDto currentMemorySnapshot = getCurrentMemorySnapshot();
        String arrayAddress = getArrayAddress();
        currentMemorySnapshot.updateAddressObjectMap(arrayAddress, toList());
        steps.add(currentMemorySnapshot);
    }

    private String getArrayAddress() {
        return (String) getCurrentMemorySnapshot().getInstanceVariables().get("array");
    }

    private String getOldArrayAddress() {
        return (String) getCurrentMemorySnapshot().getLocalVariables().get("oldArray");
    }
}
