package org.gpavl.datastructuresvisualizationbackend.model.stack;

import org.gpavl.datastructuresvisualizationbackend.model.DataStructure;
import org.gpavl.datastructuresvisualizationbackend.model.MemoryHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.model.Node;
import org.gpavl.datastructuresvisualizationbackend.model.OperationHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils;

import java.util.Collections;

import static org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils.convertToNode;
import static org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils.getCount;

public class LinkedListStack extends DataStructure {

    public LinkedListStack() {
        memoryHistory = new MemoryHistoryDto();
        OperationHistoryDto operationHistory = new OperationHistoryDto("LinkedListStack", Collections.emptyMap());

        operationHistory.addInstanceVariable("stack", null);
        operationHistory.addInstanceVariable("count", 0);

        memoryHistory.addOperationHistory(operationHistory);
    }

    public void size() {
        MemoryUtils.size(memoryHistory);
    }

    public void isEmpty() {
        MemoryUtils.isEmpty(memoryHistory);
    }

    public void clear() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("clear", memoryHistory);

        int count = getCount(operationHistory);

        while (count > 0) {
            popWrapper(operationHistory);
            count = getCount(operationHistory);
        }

        memoryHistory.addOperationHistory(operationHistory);
    }

    public void push(String element) {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("push", memoryHistory, "element", element);
        operationHistory.addLocalVariable("element", element);

        String stackAddress = (String) operationHistory.getInstanceVariableValue("stack");

        Node node = new Node();
        node.setValue(element);
        node.setNextAddress(stackAddress);

        String address = operationHistory.addNewObject(node);
        operationHistory.addInstanceVariable("stack", address);

        int count = getCount(operationHistory);
        count++;
        operationHistory.addInstanceVariable("count", count);

        operationHistory.removeLocalVariable("element");

        memoryHistory.addOperationHistory(operationHistory);
    }

    public void pop() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("pop", memoryHistory);

        popWrapper(operationHistory);

        memoryHistory.addOperationHistory(operationHistory);
    }

    private void popWrapper(OperationHistoryDto operationHistory) {
        int count = getCount(operationHistory);

        if (count == 0) {
            throw new IllegalArgumentException("Attempting to pop an empty stack");
        }

        String stackAddress = (String) operationHistory.getInstanceVariableValue("stack");
        operationHistory.addLocalVariable("node", stackAddress);
        Node resultNode = convertToNode(operationHistory.getObject(stackAddress));
        String result = resultNode.getValue();
        operationHistory.addLocalVariable("result", result);

        operationHistory.addInstanceVariable("stack", resultNode.getNextAddress());

        count--;
        operationHistory.addInstanceVariable("count", count);

        operationHistory.freeLocalVariable("node", "Node was freed");

        operationHistory.addResult(result);

        operationHistory.removeLocalVariable("result");
    }

    public void peek() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("peek", memoryHistory);

        int count = getCount(operationHistory);

        if (count == 0) {
            throw new IllegalArgumentException("Attempting to peek an empty stack");
        }

        String stackAddress = (String) operationHistory.getInstanceVariableValue("stack");
        Node resultNode = convertToNode(operationHistory.getObject(stackAddress));

        operationHistory.addResult(resultNode.getValue());

        memoryHistory.addOperationHistory(operationHistory);
    }
}
