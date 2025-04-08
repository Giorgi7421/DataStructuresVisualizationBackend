package org.gpavl.datastructuresvisualizationbackend.model.stack;

import org.gpavl.datastructuresvisualizationbackend.model.MemoryHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.model.OperationHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils.*;

public class ArrayStack extends Stack {

    private static final int INITIAL_CAPACITY = 10;

    public ArrayStack() {
        memoryHistory = new MemoryHistoryDto();
        OperationHistoryDto operationHistory = new OperationHistoryDto("ArrayStack", Collections.emptyMap());

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
    public void push(String element) {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("push", memoryHistory, "element", element);
        operationHistory.addLocalVariable("element", element);

        int count = getCount(operationHistory);
        int capacity = getCapacity(operationHistory);

        if (count == capacity) {
            extendCapacity(operationHistory);
        }

        List<String> array = getArray(operationHistory, "array");
        array = new ArrayList<>(array);
        array.set(count, element);
        updateArray(array, operationHistory, "array");
        count++;
        operationHistory.addInstanceVariable("count", count);

        operationHistory.removeLocalVariable("element");

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void pop() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("pop", memoryHistory);

        int count = getCount(operationHistory);

        if (count == 0) {
            throw new IllegalArgumentException("Attempting to pop an empty stack");
        }

        count--;
        operationHistory.addInstanceVariable("count", count);

        List<String> array = getArray(operationHistory, "array");
        operationHistory.addResult(array.get(count));

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void peek() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("peek", memoryHistory);

        int count = getCount(operationHistory);

        if (count == 0) {
            throw new IllegalArgumentException("Attempting to peek at an empty stack");
        }

        List<String> array = getArray(operationHistory, "array");
        operationHistory.addResult(array.get(count - 1));

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
