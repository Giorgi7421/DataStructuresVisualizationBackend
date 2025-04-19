package org.gpavl.datastructuresvisualizationbackend.model.queue;

import org.gpavl.datastructuresvisualizationbackend.model.MemoryHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.model.OperationHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils.*;

public class BinaryHeapPriorityQueue extends Queue {

    private static final int INITIAL_CAPACITY = 10;

    public BinaryHeapPriorityQueue() {
        memoryHistory = new MemoryHistoryDto();
        OperationHistoryDto operationHistory = new OperationHistoryDto("BinaryHeapPriorityQueue", Collections.emptyMap());

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

    }

    @Override
    public void enqueue(String element) {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("enqueue", memoryHistory, "element", element);
        operationHistory.addLocalVariable("element", element);

        int count = getCount(operationHistory);
        int capacity = getCapacity(operationHistory);

        if (count >= capacity) {
            extendCapacity(operationHistory);
        }

        List<String> array = getArray(operationHistory, "array");
        array = new ArrayList<>(array);
        array.set(count, element);
        updateArray(array, operationHistory, "array");

        count++;
        operationHistory.addInstanceVariable("count", count);

        bubbleUp(operationHistory, count - 1);

        operationHistory.removeLocalVariable("element");
        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void dequeue() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("dequeue", memoryHistory);

        int count = getCount(operationHistory);
        if (count == 0) {
            throw new IllegalArgumentException("Attempting to dequeue an empty queue");
        }

        List<String> array = getArray(operationHistory, "array");
        String result = array.getFirst();
        operationHistory.addLocalVariable("result", result);

        array = new ArrayList<>(array);
        array.set(0, array.get(count - 1));
        updateArray(array, operationHistory, "array");

        count--;
        operationHistory.addInstanceVariable("count", count);

        if (count > 0) {
            bubbleDown(operationHistory, 0);
        }

        operationHistory.addResult(result);

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void peek() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("peek", memoryHistory);

        int count = getCount(operationHistory);
        if (count == 0) {
            throw new IllegalArgumentException("Attempting to peek an empty queue");
        }

        List<String> array = getArray(operationHistory, "array");
        operationHistory.addResult(array.getFirst());

        memoryHistory.addOperationHistory(operationHistory);
    }

    private void bubbleUp(OperationHistoryDto operationHistory, int index) {
        operationHistory.addLocalVariable("index", index);
        List<String> array = getArray(operationHistory, "array");
        while (hasParent(index) && array.get(index).compareTo(array.get(getParentIndex(index))) < 0) {
            array = new ArrayList<>(array);
            Collections.swap(array, index, getParentIndex(index));
            updateArray(array, operationHistory, "array");
            index = getParentIndex(index);
            operationHistory.addLocalVariable("index", index);
        }
        operationHistory.removeLocalVariable("index");
    }

    private void bubbleDown(OperationHistoryDto operationHistory, int index) {
        operationHistory.addLocalVariable("index", index);
        List<String> array = getArray(operationHistory, "array");
        while (hasLeftChild(operationHistory, index)) {
            int smallerChildIndex = getLeftChildIndex(index);
            operationHistory.addLocalVariable("smallerChildIndex", smallerChildIndex);

            if (hasRightChild(operationHistory, index) && array.get(getRightChildIndex(index)).compareTo(array.get(getLeftChildIndex(index))) < 0) {
                smallerChildIndex = getRightChildIndex(index);
                operationHistory.addLocalVariable("smallerChildIndex", smallerChildIndex);
            }

            if (array.get(index).compareTo(array.get(smallerChildIndex)) <= 0) {
                break;
            }

            Collections.swap(array, index, smallerChildIndex);
            updateArray(array, operationHistory, "array");

            index = smallerChildIndex;
            operationHistory.addLocalVariable("index", index);
        }

        operationHistory.removeLocalVariable("smallerChildIndex");
        operationHistory.removeLocalVariable("index");
    }

    private int getParentIndex(int index) {
        return (index - 1) / 2;
    }

    private int getLeftChildIndex(int index) {
        return 2 * index + 1;
    }

    private int getRightChildIndex(int index) {
        return 2 * index + 2;
    }

    private boolean hasParent(int index) {
        return index > 0;
    }

    private boolean hasLeftChild(OperationHistoryDto operationHistory, int index) {
        int count = getCount(operationHistory);
        return getLeftChildIndex(index) < count;
    }
    private boolean hasRightChild(OperationHistoryDto operationHistory, int index) {
        int count = getCount(operationHistory);
        return getRightChildIndex(index) < count;
    }
}
