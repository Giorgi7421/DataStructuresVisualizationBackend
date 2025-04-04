package org.gpavl.datastructuresvisualizationbackend.model.vector;

import lombok.Getter;
import lombok.Setter;
import org.gpavl.datastructuresvisualizationbackend.model.DataStructure;
import org.gpavl.datastructuresvisualizationbackend.model.MemoryHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.model.OperationHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils;

import java.util.*;

@Getter
@Setter
public class ArrayVector extends DataStructure {

    private static final int INITIAL_CAPACITY = 10;

    public ArrayVector() {
        memoryHistory = new MemoryHistoryDto();
        OperationHistoryDto operationHistory = new OperationHistoryDto("ArrayVector", Collections.emptyMap());

        operationHistory.addInstanceVariable("capacity", INITIAL_CAPACITY);
        operationHistory.addInstanceVariable("count", 0);
        List<String> array = Collections.nCopies(INITIAL_CAPACITY, null);
        String newAddress = operationHistory.addNewObject(array);
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

        List<String> array = Collections.nCopies(INITIAL_CAPACITY, null);
        String newAddress = operationHistory.addNewObject(array);
        operationHistory.addInstanceVariable("array", newAddress);

        for (int i = 0; i < amount; i++) {
            array = new ArrayList<>(array);
            array.set(i, element);
            updateArray(array, operationHistory);
        }

        operationHistory.removeLocalVariable("element");
        operationHistory.removeLocalVariable("amount");

        memoryHistory.addOperationHistory(operationHistory);
    }

    public void size() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("size", memoryHistory);
        int size = getCount(operationHistory);
        operationHistory.addResult(size);

        memoryHistory.addOperationHistory(operationHistory);
    }

    public void isEmpty() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("isEmpty", memoryHistory);
        int size = getCount(operationHistory);
        boolean isEmpty = size == 0;
        operationHistory.addResult(isEmpty);

        memoryHistory.addOperationHistory(operationHistory);
    }

    public void clear() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("clear", memoryHistory);
        operationHistory.addInstanceVariable("count", 0);
        memoryHistory.addOperationHistory(operationHistory);
    }

    public void get(int index) {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("get", memoryHistory, "index", index);
        int count = getCount(operationHistory);
        if (index < 0 || index >= count) {
            throw new IllegalArgumentException("index out of range");
        }

        operationHistory.addLocalVariable("index", index);
        List<String> array = getArray(operationHistory);
        operationHistory.addResult(array.get(index));
        operationHistory.removeLocalVariable("index");

        memoryHistory.addOperationHistory(operationHistory);
    }

    public void set(int index, String element) {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot(
                "set",
                memoryHistory,
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

        List<String> array = getArray(operationHistory);

        array = new ArrayList<>(array);
        array.set(index, element);
        updateArray(array, operationHistory);
        operationHistory.removeLocalVariable("element");
        operationHistory.removeLocalVariable("index");

        memoryHistory.addOperationHistory(operationHistory);
    }

    public void add(String element) {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("add", memoryHistory, "element", element);
        int count = getCount(operationHistory);

        operationHistory.addLocalVariable("element", element);
        insertAt(count, element, operationHistory);
    }

    public void insertAt(int index, String element, OperationHistoryDto caller) {
        OperationHistoryDto operationHistory = caller != null
                ? caller
                : MemoryUtils.getLastMemorySnapshot(
                "insertAt",
                memoryHistory,
                "index",
                index,
                "element",
                element
        );

        operationHistory.addLocalVariable("index", index);
        if (caller == null) {
            operationHistory.addLocalVariable("element", element);
        }

        int count = getCount(operationHistory);
        int capacity = getCapacity(operationHistory);

        if (index < 0 || index > count) {
            throw new IllegalArgumentException("index out of range");
        }

        if (count == capacity) {
            extendCapacity(operationHistory);
        }

        List<String> array = getArray(operationHistory);

        for (int i = count; i > index; i--) {
            array = new ArrayList<>(array);
            array.set(i, array.get(i - 1));
            updateArray(array, operationHistory);
        }

        array = new ArrayList<>(array);
        array.set(index, element);
        updateArray(array, operationHistory);
        count++;
        operationHistory.addInstanceVariable("count", count);

        operationHistory.removeLocalVariable("element");
        operationHistory.removeLocalVariable("index");

        memoryHistory.addOperationHistory(operationHistory);
    }

    public void removeAt(int index) {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("removeAt", memoryHistory, "index", index);
        operationHistory.addLocalVariable("index", index);

        int count = getCount(operationHistory);
        if (index < 0 || index >= count) {
            throw new IllegalArgumentException("index out of range");
        }

        List<String> array = getArray(operationHistory);

        for (int i = index; i < count - 1; i++) {
            array = new ArrayList<>(array);
            array.set(i, array.get(i + 1));
            updateArray(array, operationHistory);
        }
        count--;
        operationHistory.addInstanceVariable("count", count);
        operationHistory.removeLocalVariable("index");

        memoryHistory.addOperationHistory(operationHistory);
    }

    private void extendCapacity(OperationHistoryDto operationHistory) {
        List<String> oldArray = getArray(operationHistory);
        String arrayAddress = (String) operationHistory.getInstanceVariableValue("array");
        operationHistory.addLocalVariable("oldArray", arrayAddress);

        int capacity = getCapacity(operationHistory);
        capacity *= 2;
        operationHistory.addInstanceVariable("capacity", capacity);

        List<String> array = Collections.nCopies(capacity, null);
        String newAddress = operationHistory.addNewObject(array);
        operationHistory.addInstanceVariable("array", newAddress);

        int count = getCount(operationHistory);

        for (int i = 0; i < count; i++) {
            array = new ArrayList<>(array);
            array.set(i, oldArray.get(i));
            updateArray(array, operationHistory);
            if (i == count - 1) {
                operationHistory.addMessage("Extending the capacity is completed");
            }
        }

        operationHistory.freeLocalVariable("oldArray", "Deleted old array to avoid memory leaks");
    }

    private void updateArray(List<String> array, OperationHistoryDto operationHistoryDto) {
        String arrayAddress = (String) operationHistoryDto.getInstanceVariableValue("array");
        operationHistoryDto.updateObject(arrayAddress, array);
    }

    private int getCount(OperationHistoryDto operationHistory) {
       return (int) operationHistory.getInstanceVariableValue("count");
    }

    private int getCapacity(OperationHistoryDto operationHistory) {
        return (int) operationHistory.getInstanceVariableValue("capacity");
    }

    private List<String> getArray(OperationHistoryDto operationHistory) {
        String arrayAddress = (String) operationHistory.getInstanceVariableValue("array");
        return operationHistory.getArray(arrayAddress);
    }
}
