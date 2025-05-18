package org.gpavl.datastructuresvisualizationbackend.model.queue;

import org.gpavl.datastructuresvisualizationbackend.model.MemoryHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.model.Node;
import org.gpavl.datastructuresvisualizationbackend.model.OperationHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils;

import java.util.Collections;

import static org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils.convertToNode;
import static org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils.getCount;

public class SortedLinkedListPriorityQueue extends Queue {

    public SortedLinkedListPriorityQueue() {
        memoryHistory = new MemoryHistoryDto();
        OperationHistoryDto operationHistory = new OperationHistoryDto("SortedLinkedListPriorityQueue", Collections.emptyMap());

        operationHistory.addInstanceVariable("head", null);
        operationHistory.addInstanceVariable("count", 0);

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

        String headAddress = (String) operationHistory.getInstanceVariableValue("head");
        Node head = headAddress != null ? convertToNode(operationHistory.getObject(headAddress)) : null;

        if (head == null || element.compareTo(head.getValue()) < 0) {
            Node newNode = new Node();
            newNode.setValue(element);
            newNode.setNextAddress(headAddress);

            String addrs = operationHistory.addNewObject(newNode);
            operationHistory.addInstanceVariable("head", addrs);
        }else {
            String current = headAddress;
            operationHistory.addLocalVariable("current", current);

            Node currentNode = convertToNode(operationHistory.getObject(current));

            String prev = null;
            operationHistory.addLocalVariable("prev", prev);

            while (current != null && currentNode.getValue().compareTo(element) <= 0) {
                prev = current;
                operationHistory.addLocalVariable("prev", prev);

                current = currentNode.getNextAddress();
                operationHistory.addLocalVariable("current", current);

                currentNode = current != null ? convertToNode(operationHistory.getObject(current)) : null;
            }

            Node newPrev = new Node();
            newPrev.setValue(element);
            newPrev.setNextAddress(current);

            String address = operationHistory.addNewObject(newPrev);
            Node prevNode = convertToNode(operationHistory.getObject(prev));
            prevNode.setNextAddress(address);

            operationHistory.updateObject(prev, prevNode);

            operationHistory.removeLocalVariable("prev");
            operationHistory.removeLocalVariable("current");
        }

        int count = getCount(operationHistory);
        count++;
        operationHistory.addInstanceVariable("count", count);

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

        String headAddress = (String) operationHistory.getInstanceVariableValue("head");
        Node headNode = convertToNode(operationHistory.getObject(headAddress));

        String result = headNode.getValue();
        operationHistory.addLocalVariable("result", result);

        String oldHead = headAddress;
        operationHistory.addLocalVariable("oldHead", oldHead);

        operationHistory.addInstanceVariable("head", headNode.getNextAddress());
        operationHistory.freeLocalVariable("oldHead", "oldHead freed");

        count--;
        operationHistory.addInstanceVariable("count", count);

        operationHistory.addResult(result);
        operationHistory.removeLocalVariable("result");

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void peek() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("peek", memoryHistory);

        int count = getCount(operationHistory);

        if (count == 0) {
            throw new IllegalArgumentException("Attempting to peek an empty queue");
        }

        Node headNode = convertToNode(operationHistory.getObject((String) operationHistory.getInstanceVariableValue("head")));
        operationHistory.addResult(headNode.getValue());

        memoryHistory.addOperationHistory(operationHistory);
    }
}
