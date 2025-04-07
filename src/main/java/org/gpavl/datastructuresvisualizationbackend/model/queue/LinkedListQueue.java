package org.gpavl.datastructuresvisualizationbackend.model.queue;

import org.gpavl.datastructuresvisualizationbackend.model.DataStructure;
import org.gpavl.datastructuresvisualizationbackend.model.MemoryHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.model.Node;
import org.gpavl.datastructuresvisualizationbackend.model.OperationHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils;

import java.util.Collections;

import static org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils.convertToNode;
import static org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils.getCount;

public class LinkedListQueue extends DataStructure {

    public LinkedListQueue() {
        memoryHistory = new MemoryHistoryDto();
        OperationHistoryDto operationHistory = new OperationHistoryDto("LinkedListQueue", Collections.emptyMap());

        operationHistory.addInstanceVariable("head", null);
        operationHistory.addInstanceVariable("tail", null);
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
            dequeueWrapper(operationHistory);
            count = getCount(operationHistory);
        }

        memoryHistory.addOperationHistory(operationHistory);
    }

    public void enqueue(String element) {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("enqueue", memoryHistory, "element", element);
        operationHistory.addLocalVariable("element", element);

        Node node = new Node();
        node.setValue(element);

        String newAddress = operationHistory.addNewObject(node);

        operationHistory.addLocalVariable("node", newAddress);

        String headAddress = (String) operationHistory.getInstanceVariableValue("head");

        if (headAddress == null) {
            operationHistory.addInstanceVariable("head", newAddress);
        }else {
            String tailAddress = (String) operationHistory.getInstanceVariableValue("tail");
            Node tailNode = convertToNode(operationHistory.getObject(tailAddress));

            Node newTailNode = new Node();
            newTailNode.setValue(tailNode.getValue());
            newTailNode.setNextAddress(newAddress);

            operationHistory.updateObject(tailAddress, newTailNode);
        }

        operationHistory.addInstanceVariable("tail", newAddress);

        int count = getCount(operationHistory);
        count++;
        operationHistory.addInstanceVariable("count", count);

        operationHistory.removeLocalVariable("node");
        operationHistory.removeLocalVariable("element");

        memoryHistory.addOperationHistory(operationHistory);
    }

    public void dequeue() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("dequeue", memoryHistory);

        dequeueWrapper(operationHistory);

        memoryHistory.addOperationHistory(operationHistory);
    }

    public void peek() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("peek", memoryHistory);

        int count = getCount(operationHistory);

        if (count == 0) {
            throw new IllegalArgumentException("Attempting to peek an empty queue");
        }

        String headAddress = (String) operationHistory.getInstanceVariableValue("head");
        Node headNode = convertToNode(operationHistory.getObject(headAddress));

        operationHistory.addResult(headNode.getValue());

        memoryHistory.addOperationHistory(operationHistory);
    }

    private void dequeueWrapper(OperationHistoryDto operationHistory) {
        int count = getCount(operationHistory);

        if (count == 0) {
            throw new IllegalArgumentException("Attempting to dequeue an empty queue");
        }

        String headAddress = (String) operationHistory.getInstanceVariableValue("head");
        operationHistory.addLocalVariable("node", headAddress);

        Node node = convertToNode(operationHistory.getObject(headAddress));
        String result = node.getValue();

        operationHistory.addLocalVariable("result", result);
        operationHistory.addInstanceVariable("head", node.getNextAddress());

        if (node.getNextAddress() == null) {
            operationHistory.addInstanceVariable("tail", null);
        }

        count--;
        operationHistory.addInstanceVariable("count", count);

        operationHistory.addResult(result);
        operationHistory.removeLocalVariable("result");
        operationHistory.freeLocalVariable("node", "Node was freed");
    }
}
