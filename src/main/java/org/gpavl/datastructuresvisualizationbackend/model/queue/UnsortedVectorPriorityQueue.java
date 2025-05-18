package org.gpavl.datastructuresvisualizationbackend.model.queue;

import org.gpavl.datastructuresvisualizationbackend.model.MemoryHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.model.OperationHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils.getArray;

public class UnsortedVectorPriorityQueue extends Queue {

    public UnsortedVectorPriorityQueue() {
        memoryHistory = new MemoryHistoryDto();
        OperationHistoryDto operationHistory = new OperationHistoryDto("UnsortedVectorPriorityQueue", Collections.emptyMap());

        List<String> vector = new ArrayList<>();

        String vectorAddress = operationHistory.addNewObject(vector);
        operationHistory.addInstanceVariable("vector", vectorAddress);

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void size() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("size", memoryHistory);

        List<String> vector = getArray(operationHistory, "vector");
        operationHistory.addResult(vector.size());

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void isEmpty() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("isEmpty", memoryHistory);

        List<String> vector = getArray(operationHistory, "vector");
        operationHistory.addResult(vector.isEmpty());

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void clear() {

    }

    @Override
    public void enqueue(String element) {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("enqueue", memoryHistory, "element", element);
        operationHistory.addLocalVariable("element", element);

        List<String> vector = getArray(operationHistory, "vector");
        List<String> newVector = new ArrayList<>(vector);
        newVector.add(element);

        operationHistory.updateObject((String) operationHistory.getInstanceVariableValue("vector"), newVector);
        operationHistory.removeLocalVariable("element");

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void dequeue() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("dequeue", memoryHistory);

        List<String> vector = getArray(operationHistory, "vector");

        if (vector.isEmpty()) {
            throw new IllegalArgumentException("Attempting to dequeue an empty queue");
        }

        int loc = 0;
        operationHistory.addLocalVariable("loc", loc);

        String first = vector.get(loc);
        operationHistory.addLocalVariable("first", first);

        for (int i = 1; i < vector.size(); i++) {
            operationHistory.addLocalVariable("currentIndex", i);
            if (vector.get(i).compareTo(first) < 0) {
                loc = i;
                operationHistory.addLocalVariable("loc", i);

                first = vector.get(i);
                operationHistory.addLocalVariable("first", first);
            }
        }

        operationHistory.removeLocalVariable("currentIndex");

        List<String> newVector = new ArrayList<>(vector);
        newVector.remove(loc);
        operationHistory.updateObject((String) operationHistory.getInstanceVariableValue("vector"), newVector);


        operationHistory.addResult(first);
        operationHistory.removeLocalVariable("first");
        operationHistory.removeLocalVariable("loc");

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void peek() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("peek", memoryHistory);

        List<String> vector = getArray(operationHistory, "vector");

        if (vector.isEmpty()) {
            throw new IllegalArgumentException("Attempting to dequeue an empty queue");
        }

        String result = vector.getFirst();
        operationHistory.addLocalVariable("result", result);

        for (int i = 1; i < vector.size(); i++) {
            if (vector.get(i).compareTo(result) < 0) {
                result = vector.get(i);
                operationHistory.addLocalVariable("result", result);
            }
        }

        operationHistory.addResult(result);
        operationHistory.removeLocalVariable("result");

        memoryHistory.addOperationHistory(operationHistory);
    }
}
