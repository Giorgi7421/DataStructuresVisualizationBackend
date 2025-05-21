package org.gpavl.datastructuresvisualizationbackend.model;

import org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils;

import java.util.Collections;

import static org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils.convertToDoublyLinkedNode;

public class Deque extends DataStructure {

    public Deque() {
        memoryHistory = new MemoryHistoryDto();
        OperationHistoryDto operationHistory = new OperationHistoryDto("Deque", Collections.emptyMap());

        operationHistory.addInstanceVariable("front", null);
        operationHistory.addInstanceVariable("rear", null);
        operationHistory.addInstanceVariable("size", 0);

        memoryHistory.addOperationHistory(operationHistory);
    }

    public void size() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("size", memoryHistory);
        int size = (int) operationHistory.getInstanceVariableValue("size");
        operationHistory.addResult(size);

        memoryHistory.addOperationHistory(operationHistory);
    }

    public void isEmpty() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("isEmpty", memoryHistory);
        String frontAddress = (String) operationHistory.getInstanceVariableValue("front");
        operationHistory.addResult(frontAddress == null);

        memoryHistory.addOperationHistory(operationHistory);
    }

    public void clear() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("clear", memoryHistory);

        int size = (int) operationHistory.getInstanceVariableValue("size");
        while (size != 0) {
            popBackWrapper(operationHistory);
            size = (int) operationHistory.getInstanceVariableValue("size");
        }

        memoryHistory.addOperationHistory(operationHistory);
    }

    public void pushFront(String element) {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("pushFront", memoryHistory, "element", element);
        operationHistory.addLocalVariable("element", element);

        DoublyLinkedNode newNode = new DoublyLinkedNode(element);
        String newAddress = operationHistory.addNewObject(newNode);
        operationHistory.addLocalVariable("newNode", newAddress);

        String frontAddress = (String) operationHistory.getInstanceVariableValue("front");
        if (frontAddress == null) {
            operationHistory.addInstanceVariable("front", newAddress);
            operationHistory.addInstanceVariable("rear", newAddress);
        }else {
            DoublyLinkedNode newNode1 = new DoublyLinkedNode(element);
            newNode1.setNextAddress(frontAddress);
            operationHistory.updateObject(newAddress, newNode1);

            DoublyLinkedNode front = convertToDoublyLinkedNode(operationHistory.getObject(frontAddress));
            front.setPreviousAddress(newAddress);
            operationHistory.updateObject(frontAddress, front);

            operationHistory.addInstanceVariable("front", newAddress);
        }

        int size = (int) operationHistory.getInstanceVariableValue("size");
        size++;
        operationHistory.addInstanceVariable("size", size);

        operationHistory.removeLocalVariable("newNode");
        operationHistory.removeLocalVariable("element");

        memoryHistory.addOperationHistory(operationHistory);
    }

    public void pushBack(String element) {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("pushBack", memoryHistory, "element", element);
        operationHistory.addLocalVariable("element", element);

        DoublyLinkedNode newNode = new DoublyLinkedNode(element);
        String newAddress = operationHistory.addNewObject(newNode);
        operationHistory.addLocalVariable("newNode", newAddress);

        String frontAddress = (String) operationHistory.getInstanceVariableValue("front");
        String rearAddress = (String) operationHistory.getInstanceVariableValue("rear");
        if (frontAddress == null) {
            operationHistory.addInstanceVariable("front", newAddress);
            operationHistory.addInstanceVariable("rear", newAddress);
        }else {
            DoublyLinkedNode newNode1 = new DoublyLinkedNode(element);
            newNode1.setPreviousAddress(rearAddress);
            operationHistory.updateObject(newAddress, newNode1);

            DoublyLinkedNode rear = convertToDoublyLinkedNode(operationHistory.getObject(rearAddress));
            rear.setNextAddress(newAddress);
            operationHistory.updateObject(rearAddress, rear);

            operationHistory.addInstanceVariable("rear", newAddress);
        }

        int size = (int) operationHistory.getInstanceVariableValue("size");
        size++;
        operationHistory.addInstanceVariable("size", size);

        operationHistory.removeLocalVariable("newNode");

        operationHistory.removeLocalVariable("element");
        memoryHistory.addOperationHistory(operationHistory);
    }

    public void popFront() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("popFront", memoryHistory);

        String frontAddress = (String) operationHistory.getInstanceVariableValue("front");
        String rearAddress = (String) operationHistory.getInstanceVariableValue("rear");

        if (frontAddress == null) {
            throw new IllegalArgumentException("Attempting to popFront an empty deque");
        }

        String temp = frontAddress;
        operationHistory.addLocalVariable("temp", temp);

        DoublyLinkedNode front = convertToDoublyLinkedNode(operationHistory.getObject(frontAddress));
        String element = front.getValue();
        operationHistory.addLocalVariable("element", element);

        if (frontAddress.equals(rearAddress)) {
            operationHistory.addInstanceVariable("front", null);
            operationHistory.addInstanceVariable("rear", null);
        }else {
            String next = front.getNextAddress();
            operationHistory.addInstanceVariable("front", next);

            front = convertToDoublyLinkedNode(operationHistory.getObject((String) operationHistory.getInstanceVariableValue("front")));
            front.setPreviousAddress(null);

            operationHistory.updateObject((String) operationHistory.getInstanceVariableValue("front"), front);
        }

        operationHistory.freeLocalVariable("temp", "temp freed");
        operationHistory.addResult(element);

        operationHistory.removeLocalVariable("temp");

        int size = (int) operationHistory.getInstanceVariableValue("size");
        size--;
        operationHistory.addInstanceVariable("size", size);

        operationHistory.removeLocalVariable("element");

        memoryHistory.addOperationHistory(operationHistory);
    }

    public void popBack() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("popBack", memoryHistory);
        popBackWrapper(operationHistory);
        memoryHistory.addOperationHistory(operationHistory);
    }

    public void getFront() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("getFront", memoryHistory);

        String frontAddress = (String) operationHistory.getInstanceVariableValue("front");

        if (frontAddress == null) {
            throw new IllegalArgumentException("Attempting to getFront an empty deque");
        }

        DoublyLinkedNode front = convertToDoublyLinkedNode(operationHistory.getObject(frontAddress));
        String element = front.getValue();
        operationHistory.addResult(element);

        memoryHistory.addOperationHistory(operationHistory);
    }

    public void getBack() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("getBack", memoryHistory);

        String rearAddress = (String) operationHistory.getInstanceVariableValue("rear");

        if (rearAddress == null) {
            throw new IllegalArgumentException("Attempting to getBack an empty deque");
        }

        DoublyLinkedNode rear = convertToDoublyLinkedNode(operationHistory.getObject(rearAddress));
        String element = rear.getValue();
        operationHistory.addResult(element);

        memoryHistory.addOperationHistory(operationHistory);
    }

    private void popBackWrapper(OperationHistoryDto operationHistory) {
        String frontAddress = (String) operationHistory.getInstanceVariableValue("front");
        String rearAddress = (String) operationHistory.getInstanceVariableValue("rear");

        if (frontAddress == null) {
            throw new IllegalArgumentException("Attempting to popBack an empty deque");
        }

        String temp = rearAddress;
        operationHistory.addLocalVariable("temp", temp);

        DoublyLinkedNode rear = convertToDoublyLinkedNode(operationHistory.getObject(rearAddress));
        String element = rear.getValue();
        operationHistory.addLocalVariable("element", element);

        if (frontAddress.equals(rearAddress)) {
            operationHistory.addInstanceVariable("front", null);
            operationHistory.addInstanceVariable("rear", null);
        }else {
            String prev = rear.getPreviousAddress();
            operationHistory.addInstanceVariable("rear", prev);

            rear = convertToDoublyLinkedNode(operationHistory.getObject((String) operationHistory.getInstanceVariableValue("rear")));
            rear.setNextAddress(null);

            operationHistory.updateObject((String) operationHistory.getInstanceVariableValue("rear"), rear);
        }

        operationHistory.freeLocalVariable("temp", "temp freed");
        operationHistory.addResult(element);

        operationHistory.removeLocalVariable("temp");

        int size = (int) operationHistory.getInstanceVariableValue("size");
        size--;
        operationHistory.addInstanceVariable("size", size);

        operationHistory.removeLocalVariable("element");
    }
}
