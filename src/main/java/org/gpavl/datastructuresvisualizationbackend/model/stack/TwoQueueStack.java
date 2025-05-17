package org.gpavl.datastructuresvisualizationbackend.model.stack;

import org.gpavl.datastructuresvisualizationbackend.model.MemoryHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.model.OperationHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils;

import java.util.*;

import static org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils.getArray;
import static org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils.updateArray;

public class TwoQueueStack extends Stack {

    public TwoQueueStack() {
        memoryHistory = new MemoryHistoryDto();
        OperationHistoryDto operationHistory = new OperationHistoryDto("TwoQueueStack", Collections.emptyMap());

        Queue<String> first = new LinkedList<>();
        Queue<String> second = new LinkedList<>();

        List<String> firstList = new ArrayList<>(first);
        List<String> secondList = new ArrayList<>(second);

        String firstAddress = operationHistory.addNewObject(firstList);
        operationHistory.addInstanceVariable("first", firstAddress);

        String secondAddress = operationHistory.addNewObject(secondList);
        operationHistory.addInstanceVariable("second", secondAddress);

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void size() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("size", memoryHistory);

        Queue<String> first = getFirst(operationHistory);
        operationHistory.addResult(first.size());

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void isEmpty() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("isEmpty", memoryHistory);

        Queue<String> first = getFirst(operationHistory);
        operationHistory.addResult(first.isEmpty());

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void clear() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("clear", memoryHistory);

        Queue<String> first = getFirst(operationHistory);
        Queue<String> second = getSecond(operationHistory);

        while (!first.isEmpty()) {
            first.remove();
            updateArray(new ArrayList<>(first), operationHistory, "first");
        }

        while (!second.isEmpty()) {
            second.remove();
            updateArray(new ArrayList<>(second), operationHistory, "second");
        }

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void push(String element) {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("push", memoryHistory, "element", element);
        operationHistory.addLocalVariable("element", element);

        Queue<String> first = getFirst(operationHistory);
        first.offer(element);

        updateArray(new ArrayList<>(first), operationHistory, "first");
        operationHistory.removeLocalVariable("element");

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void pop() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("pop", memoryHistory);

        Queue<String> first = getFirst(operationHistory);
        Queue<String> second = getSecond(operationHistory);

        if (first.isEmpty()) {
            throw new IllegalArgumentException("Attempting to pop an empty stack");
        }

        while (first.size() > 1) {
            String top = first.element();
            operationHistory.addLocalVariable("top", top);

            second.offer(top);
            updateArray(new ArrayList<>(second), operationHistory, "second");

            operationHistory.removeLocalVariable("top");

            first.remove();
            updateArray(new ArrayList<>(first), operationHistory, "first");
        }

        String top = first.element();
        operationHistory.addLocalVariable("top", top);

        first.remove();
        updateArray(new ArrayList<>(first), operationHistory, "first");

        swap(operationHistory);

        operationHistory.addResult(top);

        operationHistory.removeLocalVariable("top");
        operationHistory.removeLocalVariable("element");

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void peek() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("top", memoryHistory);

        Queue<String> first = getFirst(operationHistory);
        Queue<String> second = getSecond(operationHistory);

        if (first.isEmpty()) {
            throw new IllegalArgumentException("Attempting to peek an empty stack");
        }

        String top = first.element();
        operationHistory.addLocalVariable("top", top);

        second.offer(top);
        updateArray(new ArrayList<>(second), operationHistory, "second");

        first.remove();
        updateArray(new ArrayList<>(first), operationHistory, "first");

        swap(operationHistory);

        operationHistory.addResult(top);
        operationHistory.removeLocalVariable("top");

        memoryHistory.addOperationHistory(operationHistory);
    }

    private Queue<String> getFirst(OperationHistoryDto operationHistory) {
        List<String> beforeList = getArray(operationHistory, "first");
        return new LinkedList<>(beforeList);
    }

    private Queue<String> getSecond(OperationHistoryDto operationHistory) {
        List<String> beforeList = getArray(operationHistory, "second");
        return new LinkedList<>(beforeList);
    }

    private void swap(OperationHistoryDto operationHistory) {
        String firstAddress = (String) operationHistory.getInstanceVariableValue("first");
        operationHistory.addLocalVariable("firstAddress", firstAddress);

        String secondAddress = (String) operationHistory.getInstanceVariableValue("second");

        operationHistory.addInstanceVariable("first", secondAddress);
        operationHistory.addInstanceVariable("second", firstAddress);
        operationHistory.removeLocalVariable("firstAddress");
    }
}
