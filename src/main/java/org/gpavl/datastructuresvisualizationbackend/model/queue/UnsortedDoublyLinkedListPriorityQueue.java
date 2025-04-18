package org.gpavl.datastructuresvisualizationbackend.model.queue;

import org.gpavl.datastructuresvisualizationbackend.model.DoublyLinkedNode;
import org.gpavl.datastructuresvisualizationbackend.model.MemoryHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.model.OperationHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils;

import java.util.Collections;

import static org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils.convertToDoublyLinkedNode;
import static org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils.getCount;

public class UnsortedDoublyLinkedListPriorityQueue extends Queue {

    public UnsortedDoublyLinkedListPriorityQueue() {
        memoryHistory = new MemoryHistoryDto();
        OperationHistoryDto operationHistory = new OperationHistoryDto("UnsortedDoublyLinkedListPriorityQueue", Collections.emptyMap());

        operationHistory.addInstanceVariable("head", null);
        operationHistory.addInstanceVariable("tail", null);
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

        DoublyLinkedNode doublyLinkedNode = new DoublyLinkedNode();
        doublyLinkedNode.setValue(element);
        doublyLinkedNode.setNextAddress(headAddress);

        String newNodeAddress = operationHistory.addNewObject(doublyLinkedNode);
        operationHistory.addLocalVariable("newNode", newNodeAddress);

        if (headAddress != null) {
            DoublyLinkedNode headNode = convertToDoublyLinkedNode(operationHistory.getObject(headAddress));
            headNode.setPreviousAddress(newNodeAddress);

            operationHistory.updateObject(headAddress, headNode);
        }else {
            operationHistory.addInstanceVariable("tail", newNodeAddress);
        }

        operationHistory.addInstanceVariable("head", newNodeAddress);

        int count = getCount(operationHistory);
        count++;
        operationHistory.addInstanceVariable("count", count);

        operationHistory.removeLocalVariable("newNode");
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

        String minNode = (String) operationHistory.getInstanceVariableValue("head");
        DoublyLinkedNode minn = convertToDoublyLinkedNode(operationHistory.getObject(minNode));
        operationHistory.addLocalVariable("minNode", minNode);

        String current = minn.getNextAddress();
        DoublyLinkedNode currentNode = convertToDoublyLinkedNode(operationHistory.getObject(current));
        operationHistory.addLocalVariable("current", current);

        while (current != null) {
            if (currentNode.getValue().compareTo(minn.getValue()) < 0) {
                minNode = current;
                operationHistory.addLocalVariable("minNode", minNode);
                minn = convertToDoublyLinkedNode(operationHistory.getObject(minNode));
            }
            current = currentNode.getNextAddress();
            operationHistory.addLocalVariable("current", current);
            currentNode = current != null ? convertToDoublyLinkedNode(operationHistory.getObject(current)) : null;
        }

        String result = minn.getValue();
        operationHistory.addLocalVariable("result", result);

        if (minn.getPreviousAddress() != null) {
            DoublyLinkedNode prv = convertToDoublyLinkedNode(operationHistory.getObject(minn.getPreviousAddress()));
            prv.setNextAddress(minn.getNextAddress());

            operationHistory.updateObject(minn.getPreviousAddress(), prv);
        }else {
            operationHistory.addInstanceVariable("head", minn.getNextAddress());
        }

        if (minn.getNextAddress() != null) {
            DoublyLinkedNode prv = convertToDoublyLinkedNode(operationHistory.getObject(minn.getNextAddress()));
            prv.setPreviousAddress(minn.getPreviousAddress());

            operationHistory.updateObject(minn.getNextAddress(), prv);
        }else {
            operationHistory.addInstanceVariable("tail", minn.getPreviousAddress());
        }

        operationHistory.freeLocalVariable("minNode", "minNode freed");

        count--;
        operationHistory.addInstanceVariable("count", count);

        operationHistory.addResult(result);

        operationHistory.removeLocalVariable("result");
        operationHistory.removeLocalVariable("current");

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void peek() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("peek", memoryHistory);

        int count = getCount(operationHistory);

        if (count == 0) {
            throw new IllegalArgumentException("Attempting to peek an empty queue");
        }

        String current = (String) operationHistory.getInstanceVariableValue("head");
        operationHistory.addLocalVariable("current", current);

        DoublyLinkedNode currentNode = convertToDoublyLinkedNode(operationHistory.getObject(current));
        String minValue = currentNode.getValue();
        operationHistory.addLocalVariable("minValue", minValue);

        current = currentNode.getNextAddress();
        operationHistory.addLocalVariable("current", current);

        while (current != null) {
            if (currentNode.getValue().compareTo(minValue) < 0) {
                minValue = currentNode.getValue();
                operationHistory.addLocalVariable("minValue", minValue);
            }
            current = currentNode.getNextAddress();
            operationHistory.addLocalVariable("current", current);
            currentNode = current != null ? convertToDoublyLinkedNode(operationHistory.getObject(current)) : null;
        }

        operationHistory.addResult(minValue);
        operationHistory.removeLocalVariable("minValue");
        operationHistory.removeLocalVariable("current");

        memoryHistory.addOperationHistory(operationHistory);
    }
}
