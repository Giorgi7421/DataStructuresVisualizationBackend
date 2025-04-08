package org.gpavl.datastructuresvisualizationbackend.model.queue;

import org.gpavl.datastructuresvisualizationbackend.model.MemoryHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.model.OperationHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils.*;

public class ArrayQueue extends Queue {

    private static final int INITIAL_CAPACITY = 10;

    public ArrayQueue() {
        memoryHistory = new MemoryHistoryDto();
        OperationHistoryDto operationHistory = new OperationHistoryDto("ArrayQueue", Collections.emptyMap());

        operationHistory.addInstanceVariable("capacity", INITIAL_CAPACITY);
        List<String> array = Collections.nCopies(INITIAL_CAPACITY, null);
        String newAddress = operationHistory.addNewObject(array);
        operationHistory.addInstanceVariable("array", newAddress);
        operationHistory.addInstanceVariable("head", 0);
        operationHistory.addInstanceVariable("tail", 0);

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void size() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("size", memoryHistory);

        int size = getSize(operationHistory);
        operationHistory.addResult(size);

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void isEmpty() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("isEmpty", memoryHistory);

        int head = (int) operationHistory.getInstanceVariableValue("head");
        int tail = (int) operationHistory.getInstanceVariableValue("tail");

        operationHistory.addResult(head == tail);

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void clear() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("clear", memoryHistory);

        operationHistory.addInstanceVariable("head", 0);
        operationHistory.addInstanceVariable("tail", 0);

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void enqueue(String element) {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("enqueue", memoryHistory, "element", element);

        operationHistory.addLocalVariable("element", element);

        int size = getSize(operationHistory);
        int capacity = getCapacity(operationHistory);
        int tail = (int) operationHistory.getInstanceVariableValue("tail");

        if (size == capacity - 1) {
            extendCapacity(operationHistory);
        }

        capacity = getCapacity(operationHistory);
        tail = (int) operationHistory.getInstanceVariableValue("tail");

        List<String> array = getArray(operationHistory, "array");
        List<String> newArray = new ArrayList<>(array);
        newArray.set(tail, element);
        updateArray(newArray, operationHistory, "array");

        int newTail = (tail + 1) % capacity;
        operationHistory.addInstanceVariable("tail", newTail);

        operationHistory.removeLocalVariable("element");
        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void dequeue() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("dequeue", memoryHistory);

        int head = (int) operationHistory.getInstanceVariableValue("head");
        int tail = (int) operationHistory.getInstanceVariableValue("tail");

        if (head == tail) {
            throw new IllegalArgumentException("Attempting to dequeue an empty queue");
        }

        List<String> array = getArray(operationHistory, "array");
        String result = array.get(head);
        operationHistory.addLocalVariable("result", result);

        int capacity = getCapacity(operationHistory);
        operationHistory.addInstanceVariable("head", (head + 1) % capacity);

        operationHistory.addResult(result);

        operationHistory.removeLocalVariable("result");

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void peek() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("peek", memoryHistory);

        int head = (int) operationHistory.getInstanceVariableValue("head");
        int tail = (int) operationHistory.getInstanceVariableValue("tail");

        if (head == tail) {
            throw new IllegalArgumentException("Attempting to peek an empty queue");
        }

        List<String> array = getArray(operationHistory, "array");
        String result = array.get(head);

        operationHistory.addResult(result);

        memoryHistory.addOperationHistory(operationHistory);
    }

    private void extendCapacity(OperationHistoryDto operationHistory) {
        int capacity = getCapacity(operationHistory);
        int head = (int) operationHistory.getInstanceVariableValue("head");
        int tail = (int) operationHistory.getInstanceVariableValue("tail");

        int count = (tail + capacity - head) % capacity;
        operationHistory.addLocalVariable("count", count);
        capacity *= 2;

        operationHistory.addInstanceVariable("capacity", capacity);

        List<String> oldArray = getArray(operationHistory, "array");
        String arrayAddress = (String) operationHistory.getInstanceVariableValue("array");
        operationHistory.addLocalVariable("oldArray", arrayAddress);

        List<String> array = Collections.nCopies(capacity, null);
        String newAddress = operationHistory.addNewObject(array);
        operationHistory.addInstanceVariable("array", newAddress);

        for (int i = 0; i < count; i++) {
            array = new ArrayList<>(array);
            array.set(i, oldArray.get((head + i) & capacity));
            updateArray(array, operationHistory, "array");
            if (i == count - 1) {
                operationHistory.addMessage("Extending the capacity is completed");
            }
        }

        operationHistory.addInstanceVariable("head", 0);
        operationHistory.addInstanceVariable("tail", count);

        operationHistory.removeLocalVariable("count");

        operationHistory.freeLocalVariable("oldArray", "Deleted old array to avoid memory leaks");
    }

    private int getSize(OperationHistoryDto operationHistory) {
        int capacity = getCapacity(operationHistory);
        int head = (int) operationHistory.getInstanceVariableValue("head");
        int tail = (int) operationHistory.getInstanceVariableValue("tail");

        return (tail + capacity - head) % capacity;
    }
}
