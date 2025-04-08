package org.gpavl.datastructuresvisualizationbackend.model.vector;

import lombok.Getter;
import lombok.Setter;
import org.gpavl.datastructuresvisualizationbackend.model.MemoryHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.model.OperationHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils.*;

@Getter
@Setter
public class ArrayVector extends Vector {

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

    @Override
    public void size() {
        MemoryUtils.size(memoryHistory);
    }

    @Override
    public void isEmpty() {
        MemoryUtils.isEmpty(memoryHistory);
    }

    @Override
    public void clear() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("clear", memoryHistory);
        operationHistory.addInstanceVariable("count", 0);
        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void get(int index) {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("get", memoryHistory, "index", index);
        int count = getCount(operationHistory);
        if (index < 0 || index >= count) {
            throw new IllegalArgumentException("index out of range");
        }

        operationHistory.addLocalVariable("index", index);
        List<String> array = getArray(operationHistory, "array");
        operationHistory.addResult(array.get(index));
        operationHistory.removeLocalVariable("index");

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
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

        List<String> array = getArray(operationHistory, "array");

        array = new ArrayList<>(array);
        array.set(index, element);
        updateArray(array, operationHistory, "array");
        operationHistory.removeLocalVariable("element");
        operationHistory.removeLocalVariable("index");

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void add(String element) {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("add", memoryHistory, "element", element);
        int count = getCount(operationHistory);

        operationHistory.addLocalVariable("element", element);
        insertAtWrapper(operationHistory, count, element);

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void insertAt(int index, String element) {
        OperationHistoryDto operationHistory =
                MemoryUtils.getLastMemorySnapshot(
                "insertAt",
                memoryHistory,
                "index",
                index,
                "element",
                element
        );

        insertAtWrapper(operationHistory, index, element);

        memoryHistory.addOperationHistory(operationHistory);
    }

    private void insertAtWrapper(OperationHistoryDto operationHistory, int index, String element) {
        int count = getCount(operationHistory);
        int capacity = getCapacity(operationHistory);

        if (index < 0 || index > count) {
            throw new IllegalArgumentException("index out of range");
        }

        if (count == capacity) {
            extendCapacity(operationHistory);
        }

        List<String> array = getArray(operationHistory, "array");

        for (int i = count; i > index; i--) {
            array = new ArrayList<>(array);
            array.set(i, array.get(i - 1));
            updateArray(array, operationHistory, "array");
        }

        array = new ArrayList<>(array);
        array.set(index, element);
        updateArray(array, operationHistory, "array");
        count++;
        operationHistory.addInstanceVariable("count", count);

        operationHistory.removeLocalVariable("element");
        operationHistory.removeLocalVariable("index");
    }

    @Override
    public void removeAt(int index) {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("removeAt", memoryHistory, "index", index);
        operationHistory.addLocalVariable("index", index);

        int count = getCount(operationHistory);
        if (index < 0 || index >= count) {
            throw new IllegalArgumentException("index out of range");
        }

        List<String> array = getArray(operationHistory, "array");

        for (int i = index; i < count - 1; i++) {
            array = new ArrayList<>(array);
            array.set(i, array.get(i + 1));
            updateArray(array, operationHistory, "array");
        }
        count--;
        operationHistory.addInstanceVariable("count", count);
        operationHistory.removeLocalVariable("index");

        memoryHistory.addOperationHistory(operationHistory);
    }

    private void extendCapacity(OperationHistoryDto operationHistory) {
        List<String> oldArray = getArray(operationHistory, "array");
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
            updateArray(array, operationHistory, "array");
            if (i == count - 1) {
                operationHistory.addMessage("Extending the capacity is completed");
            }
        }

        operationHistory.freeLocalVariable("oldArray", "Deleted old array to avoid memory leaks");
    }
}
