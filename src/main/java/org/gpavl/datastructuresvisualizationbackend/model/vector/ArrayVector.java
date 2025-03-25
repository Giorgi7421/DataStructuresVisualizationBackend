package org.gpavl.datastructuresvisualizationbackend.model.vector;

import lombok.Getter;
import lombok.Setter;
import org.gpavl.datastructuresvisualizationbackend.model.DataStructure;
import org.gpavl.datastructuresvisualizationbackend.model.MemoryHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.model.OperationHistoryDto;

import java.util.*;

@Getter
@Setter
public class ArrayVector extends DataStructure {

    private static final int INITIAL_CAPACITY = 10;

    private MemoryHistoryDto memoryHistory;

    public ArrayVector() {
        memoryHistory = new MemoryHistoryDto();
        OperationHistoryDto operationHistory = new OperationHistoryDto("ArrayVector", Collections.emptyMap());

        operationHistory.addInstanceVariable("capacity", INITIAL_CAPACITY);
        operationHistory.addInstanceVariable("count", 0);
        String newAddress = operationHistory.addNewObject(toList(new String[INITIAL_CAPACITY]));
        operationHistory.addInstanceVariable("array", newAddress);

        memoryHistory.addOperationHistory(operationHistory);
    }

    public ArrayVector(int amount, String element) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Illegal amount of elements");
        }

        memoryHistory = new MemoryHistoryDto();
        OperationHistoryDto operationHistory = new OperationHistoryDto("ArrayVector", Map.of("amount", amount, "element", element));

        operationHistory.addLocalVariable("amount", amount);
        operationHistory.addLocalVariable("element", element);

        int capacity = Math.max(amount, INITIAL_CAPACITY);
        operationHistory.addInstanceVariable("capacity", capacity);

        operationHistory.addInstanceVariable("count", amount);

        String[] array = new String[capacity];
        String newAddress = operationHistory.addNewObject(toList(array));
        operationHistory.addInstanceVariable("array", newAddress);

        for (int i = 0; i < amount; i++) {
            array[i] = element;
            updateArray(array);
        }

        operationHistory.removeLocalVariable("amount");
        operationHistory.removeLocalVariable("element");

        memoryHistory.addOperationHistory(operationHistory);
    }

    public void size() {
        OperationHistoryDto operationHistory = getLastMemorySnapshot("size");
        int size = getCount(operationHistory);
        operationHistory.addResult(size);

        memoryHistory.addOperationHistory(operationHistory);
    }

    public void isEmpty() {
        OperationHistoryDto operationHistory = getLastMemorySnapshot("isEmpty");
        int size = getCount(operationHistory);
        boolean isEmpty = size == 0;
        operationHistory.addResult(isEmpty);

        memoryHistory.addOperationHistory(operationHistory);
    }

    public void clear() {
        OperationHistoryDto operationHistory = getLastMemorySnapshot("clear");
        operationHistory.addInstanceVariable("count", 0);
        memoryHistory.addOperationHistory(operationHistory);
    }

    public void get(int index) {
        OperationHistoryDto operationHistory = getLastMemorySnapshot("get", "index", index);
        int count = getCount(operationHistory);
        if (index < 0 || index >= count) {
            throw new IllegalArgumentException("index out of range");
        }

        operationHistory.addLocalVariable("index", index);
        String[] array = getArray(operationHistory);
        operationHistory.addResult(array[index]);
        operationHistory.removeLocalVariable("index");

        memoryHistory.addOperationHistory(operationHistory);
    }

    public void set(int index, String element) {
        OperationHistoryDto operationHistory = getLastMemorySnapshot(
                "set",
                "index",
                index,
                "element",
                element
        );

        int count = getCount(operationHistory);

        if (index < 0 || index >= count) {
            throw new IllegalArgumentException("index out of range");
        }

        operationHistory.addLocalVariable("index", index);
        operationHistory.addLocalVariable("element", element);

        String[] array = getArray(operationHistory);

        array[index] = element;
        updateArray(array);
        operationHistory.removeLocalVariable("index");
        operationHistory.removeLocalVariable("element");

        memoryHistory.addOperationHistory(operationHistory);
    }

    public void add(String element) {
        OperationHistoryDto operationHistory = getLastMemorySnapshot("add", "element", element);
        int count = getCount(operationHistory);

        operationHistory.addLocalVariable("element", element);
        insertAt(count, element, operationHistory);
    }

    public void insertAt(int index, String element, OperationHistoryDto caller) {
        OperationHistoryDto operationHistory = caller != null
                ? caller
                : getLastMemorySnapshot(
                "insertAt",
                "index",
                index,
                "element",
                element
        );

        operationHistory.addLocalVariable("index", index);
        operationHistory.addLocalVariable("element", element);

        int count = getCount(operationHistory);
        int capacity = getCapacity(operationHistory);

        if (index < 0 || index > count) {
            throw new IllegalArgumentException("index out of range");
        }

        if (count == capacity) {
            extendCapacity(operationHistory);
        }

        String[] array = getArray(operationHistory);

        for (int i = count; i > index; i--) {
            array[i] = array[i - 1];
            updateArray(array);
        }

        array[index] = element;
        updateArray(array);
        count++;
        operationHistory.addInstanceVariable("count", count);

        operationHistory.removeLocalVariable("index");
        operationHistory.removeLocalVariable("element");

        memoryHistory.addOperationHistory(operationHistory);
    }

    public void removeAt(int index) {
        OperationHistoryDto operationHistory = getLastMemorySnapshot("removeAt", "index", index);
        operationHistory.addLocalVariable("index", index);

        int count = getCount(operationHistory);
        if (index < 0 || index > count) {
            throw new IllegalArgumentException("index out of range");
        }

        String[] array = getArray(operationHistory);

        for (int i = index; i < count - 1; i++) {
            array[i] = array[i + 1];
            updateArray(array);
        }
        count--;
        operationHistory.addInstanceVariable("count", count);
        operationHistory.removeLocalVariable("index");
    }

    private void extendCapacity(OperationHistoryDto operationHistory) {
        String[] oldArray = getArray(operationHistory);
        String arrayAddress = (String) operationHistory.getInstanceVariableValue("array");
        operationHistory.addLocalVariable("oldArray", arrayAddress);

        int capacity = getCapacity(operationHistory);
        capacity *= 2;
        operationHistory.addInstanceVariable("capacity", capacity);

        String[] array = new String[capacity];
        String newAddress = operationHistory.addNewObject(toList(array));
        operationHistory.addInstanceVariable("array", newAddress);

        int count = getCount(operationHistory);

        for (int i = 0; i < count; i++) {
            array[i] = oldArray[i];
            updateArray(array);
            if (i == count - 1) {
                operationHistory.addMessage("Extending the capacity is completed");
            }
        }

        operationHistory.freeLocalVariable("oldArray", "Deleted old array to avoid memory leaks");
    }

    private List<String> toList(String[] array) {
        return Arrays.stream(array).filter(Objects::nonNull).toList();
    }

    private void updateArray(String[] array) {
        OperationHistoryDto lastOperationHistory = memoryHistory.getLastOperationHistory();
        String arrayAddress = (String) lastOperationHistory.getInstanceVariableValue("array");
        lastOperationHistory.updateObject(arrayAddress, toList(array));
    }

    private OperationHistoryDto getLastMemorySnapshot(String operationName) {
        return new OperationHistoryDto(
                "clear",
                Collections.emptyMap(),
                memoryHistory.getLastMemorySnapshot()
        );
    }

    private OperationHistoryDto getLastMemorySnapshot(String operationName, String parameterName, Object parameterValue) {
        return new OperationHistoryDto(
                operationName,
                Map.of(parameterName, parameterValue),
                memoryHistory.getLastMemorySnapshot()
        );
    }

    private OperationHistoryDto getLastMemorySnapshot(String operationName,
                                                      String firstParameterName,
                                                      Object firstParameterValue,
                                                      String secondParameterName,
                                                      Object secondParameterValue) {
        return new OperationHistoryDto(
                operationName,
                Map.of(firstParameterName,
                        firstParameterValue,
                        secondParameterName,
                        secondParameterValue
                ),
                memoryHistory.getLastMemorySnapshot()
        );
    }

    private int getCount(OperationHistoryDto operationHistory) {
       return (int) operationHistory.getInstanceVariableValue("count");
    }

    private int getCapacity(OperationHistoryDto operationHistory) {
        return (int) operationHistory.getInstanceVariableValue("capacity");
    }

    private String[] getArray(OperationHistoryDto operationHistory) {
        String arrayAddress = (String) operationHistory.getInstanceVariableValue("array");
        return  (String[]) operationHistory.getObject(arrayAddress);
    }
}
