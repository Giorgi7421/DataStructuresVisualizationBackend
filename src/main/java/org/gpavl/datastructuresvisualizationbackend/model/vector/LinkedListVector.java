package org.gpavl.datastructuresvisualizationbackend.model.vector;

import lombok.Getter;
import lombok.Setter;
import org.gpavl.datastructuresvisualizationbackend.model.DataStructure;
import org.gpavl.datastructuresvisualizationbackend.model.MemoryHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.model.Node;
import org.gpavl.datastructuresvisualizationbackend.model.OperationHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils;

import java.util.Collections;
import java.util.Map;

@Getter
@Setter
public class LinkedListVector extends DataStructure {

    public LinkedListVector() {
        memoryHistory = new MemoryHistoryDto();
        OperationHistoryDto operationHistory = new OperationHistoryDto("LinkedListVector", Collections.emptyMap());

        operationHistory.addInstanceVariable("count", 0);

        operationHistory.addInstanceVariable("start", null);
        operationHistory.addInstanceVariable("end", null);

        memoryHistory.addOperationHistory(operationHistory);
    }

    public LinkedListVector(int amount, String element) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Illegal amount of elements");
        }

        memoryHistory = new MemoryHistoryDto();
        OperationHistoryDto operationHistory = new OperationHistoryDto(
                "LinkedListVector",
                Map.of("amount", amount,
                        "element", element
                )
        );

        operationHistory.addLocalVariable("amount", amount);
        operationHistory.addLocalVariable("element", element);

        String start = createLinkedNodes(amount, element, operationHistory);

        operationHistory.addInstanceVariable("start", start);
        operationHistory.addInstanceVariable("count", amount);

        operationHistory.removeLocalVariable("firstNode");
        operationHistory.removeLocalVariable("element");
        operationHistory.removeLocalVariable("amount");

        memoryHistory.addOperationHistory(operationHistory);
    }

    private String createLinkedNodes(int amount, String element, OperationHistoryDto operationHistory) {
        if (amount == 1) {
            Node newNode = new Node(element);
            String address = operationHistory.addNewObject(newNode);
            operationHistory.addLocalVariable("newNode", address);

            operationHistory.addInstanceVariable("end", address);
            operationHistory.removeLocalVariable("newNode");
            return address;
        }
        String nextNodeAddress = createLinkedNodes(amount - 1, element, operationHistory);
        operationHistory.addLocalVariable("nextNode", nextNodeAddress);

        Node currentNode = new Node();
        currentNode.setValue(element);
        currentNode.setNextAddress(nextNodeAddress);

        String resultAddress = operationHistory.addNewObject(currentNode);
        operationHistory.addLocalVariable("firstNode", resultAddress);

        operationHistory.removeLocalVariable("nextNode");

        return resultAddress;
    }

    public void size() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("size", memoryHistory);
        int size = getCount(operationHistory);
        operationHistory.addResult(size);

        memoryHistory.addOperationHistory(operationHistory);
    }

    public void isEmpty() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("isEmpty", memoryHistory);
        int size = getCount(operationHistory);
        boolean isEmpty = size == 0;
        operationHistory.addResult(isEmpty);

        memoryHistory.addOperationHistory(operationHistory);
    }

    public void clear() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("clear", memoryHistory);
        operationHistory.addInstanceVariable("count", 0);

        String currentNodeAddress = (String) operationHistory.getInstanceVariableValue("start");
        operationHistory.addLocalVariable(
                "current",
                currentNodeAddress
        );


        operationHistory.addInstanceVariable("start", null);
        operationHistory.addInstanceVariable("end", null);

        while (currentNodeAddress != null) {
            String nextNodeAddress = operationHistory.getNextNodeAddress(currentNodeAddress);
            operationHistory.addLocalVariable("next", nextNodeAddress);
            operationHistory.freeObject(currentNodeAddress, null);
            currentNodeAddress = nextNodeAddress;
            operationHistory.addLocalVariable("current", nextNodeAddress);
            operationHistory.removeLocalVariable("next");
        }

        operationHistory.removeLocalVariable("current");
        operationHistory.addMessage("Freeing the memory is finished");

        memoryHistory.addOperationHistory(operationHistory);
    }

    public void get(int index) {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("get", memoryHistory, "index", index);
        int count = getCount(operationHistory);

        if (index < 0 || index >= count) {
            throw new IllegalArgumentException("index out of range");
        }

        operationHistory.addLocalVariable("index", index);

        String targetNodeAddress = getNodeAtIndex(index, operationHistory);

        operationHistory.addLocalVariable("targetNode", targetNodeAddress);
        operationHistory.removeLocalVariable("current");
        Node targetNode = MemoryUtils.convertToNode(operationHistory.getObject(targetNodeAddress));
        String value = targetNode.getValue();

        operationHistory.addResult(value);
        operationHistory.removeLocalVariable("targetNode");
        operationHistory.removeLocalVariable("index");

        memoryHistory.addOperationHistory(operationHistory);
    }

    public void set(int index, String element) {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot(
                "set",
                memoryHistory,
                "index",
                index,
                "element",
                element
        );

        int count = getCount(operationHistory);

        if (index < 0 || index >= count) {
            throw new IllegalArgumentException("index out of range");
        }

        operationHistory.addLocalVariable("index", index);
        operationHistory.addLocalVariable("element", element);

        String targetNodeAddress = getNodeAtIndex(index, operationHistory);
        operationHistory.addLocalVariable("targetNode", targetNodeAddress);
        operationHistory.removeLocalVariable("current");

        Node targetNode = MemoryUtils.convertToNode(operationHistory.getObject(targetNodeAddress));
        targetNode.setValue(element);

        operationHistory.updateObject(targetNodeAddress, targetNode);
        operationHistory.removeLocalVariable("targetNode");
        operationHistory.removeLocalVariable("element");
        operationHistory.removeLocalVariable("index");

        memoryHistory.addOperationHistory(operationHistory);
    }

    public void add(String element) {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("add", memoryHistory, "element", element);

        operationHistory.addLocalVariable("element", element);
        Node newNode = new Node(element);

        String address = operationHistory.addNewObject(newNode);
        operationHistory.addLocalVariable("newNode", address);

        int count = getCount(operationHistory);
        String end = getEnd(operationHistory);

        if (count != 0) {
            Node endNode = MemoryUtils.convertToNode(operationHistory.getObject(end));

            endNode.setNextAddress(address);
            operationHistory.updateObject(end, endNode);
        }else {
            operationHistory.addInstanceVariable("start", address);
        }

        operationHistory.addInstanceVariable("end", address);
        count++;
        operationHistory.addInstanceVariable("count", count);
        operationHistory.removeLocalVariable("newNode");
        operationHistory.removeLocalVariable("element");

        memoryHistory.addOperationHistory(operationHistory);
    }

    public void insertAt(int index, String element) {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot(
                "insertAt",
                memoryHistory,
                "index",
                index,
                "element",
                element
        );
        int count = getCount(operationHistory);

        if (index < 0 || index > count) {
            throw new IllegalArgumentException("index out of range");
        }

        operationHistory.addLocalVariable("index", index);
        operationHistory.addLocalVariable("element", element);
        Node newNode = new Node(element);
        String address = operationHistory.addNewObject(newNode);
        operationHistory.addLocalVariable("newNode", address);

        String targetNode;

        String start = getStart(operationHistory);

        if (index == 0) {
            if (count != 0) {
                operationHistory.addLocalVariable("oldStart", start);
                operationHistory.addInstanceVariable("start", address);
                newNode = new Node(element);
                newNode.setNextAddress(start);
                operationHistory.updateObject(address, newNode);
                count++;
                operationHistory.addInstanceVariable("count", count);
                operationHistory.removeLocalVariable("oldStart");
            }else {
                operationHistory.addInstanceVariable("start", address);
                operationHistory.addInstanceVariable("end", address);
                count++;
                operationHistory.addInstanceVariable("count", count);
            }

            operationHistory.removeLocalVariable("newNode");
            operationHistory.removeLocalVariable("element");
            operationHistory.removeLocalVariable("index");
            return;
        }else {
            targetNode = getNodeAtIndex(index - 1, operationHistory);
            operationHistory.addLocalVariable("targetNode", targetNode);
            operationHistory.removeLocalVariable("current");
        }

        String nextNode = operationHistory.getNextNodeAddress(targetNode);
        operationHistory.addLocalVariable("nextNode", nextNode);

        Node target = MemoryUtils.convertToNode(operationHistory.getObject(targetNode));
        target.setNextAddress(address);
        operationHistory.updateObject(targetNode, target);

        if (nextNode != null) {
            newNode = new Node(element);
            newNode.setNextAddress(nextNode);
            operationHistory.updateObject(address, newNode);
        }else {
            operationHistory.addInstanceVariable("end", address);
        }

        count++;
        operationHistory.addInstanceVariable("count", count);

        operationHistory.removeLocalVariable("nextNode");
        operationHistory.removeLocalVariable("targetNode");
        operationHistory.removeLocalVariable("newNode");
        operationHistory.removeLocalVariable("element");
        operationHistory.removeLocalVariable("index");

        memoryHistory.addOperationHistory(operationHistory);
    }

    public void removeAt(int index) {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("removeAt", memoryHistory, "index", index);

        operationHistory.addLocalVariable("index", index);
        int count = getCount(operationHistory);
        String start = getStart(operationHistory);

        if (index < 0 || index >= count) {
            throw new IllegalArgumentException("index out of range");
        }

        String previousTargetNode;

        if (index == 0) {
            String target = start;
            operationHistory.addLocalVariable("target", target);

            String next = operationHistory.getNextNodeAddress(target);
            operationHistory.addLocalVariable("next", next);

            operationHistory.addInstanceVariable("start", next);

            if (count == 1) {
                operationHistory.addInstanceVariable("end", null);
            }

            operationHistory.freeLocalVariable("target", "Deletion of the node is completed");

            operationHistory.removeLocalVariable("next");
            operationHistory.removeLocalVariable("index");
            return;
        }else {
            String nodeWithAddress = getNodeAtIndex(index - 1, operationHistory);
            previousTargetNode = nodeWithAddress;
            operationHistory.addLocalVariable("previousTargetNode", nodeWithAddress);
            operationHistory.removeLocalVariable("current");
        }

        String targetNode = operationHistory.getNextNodeAddress(previousTargetNode);
        operationHistory.addLocalVariable("targetNode", targetNode);

        String nextNode = operationHistory.getNextNodeAddress(targetNode);
        operationHistory.addLocalVariable("nextNode", nextNode);

        Node previousTarget = MemoryUtils.convertToNode(operationHistory.getObject(previousTargetNode));
        if (nextNode == null) {
            previousTarget.setNextAddress(null);

            operationHistory.addInstanceVariable("end", previousTargetNode);
        }else {
            previousTarget.setNextAddress(nextNode);
        }
        operationHistory.updateObject(previousTargetNode, previousTarget);

        operationHistory.freeLocalVariable("targetNode", "Deletion of the node is completed");
        operationHistory.removeLocalVariable("nextNode");
        operationHistory.removeLocalVariable("previousTargetNode");
        operationHistory.removeLocalVariable("index");

        memoryHistory.addOperationHistory(operationHistory);
    }

    private String getNodeAtIndex(int index, OperationHistoryDto operationHistory) {
        int count = getCount(operationHistory);
        if (index < 0 || index >= count) {
            throw new IllegalArgumentException("index out of range");
        }

        int counter = 0;
        operationHistory.addLocalVariable("counter", 0);

        String currentNodeAddress = getStart(operationHistory);
        operationHistory.addLocalVariable("current", currentNodeAddress);

        while (counter != index) {
            currentNodeAddress = operationHistory.getNextNodeAddress(currentNodeAddress);
            operationHistory.addLocalVariable("current", currentNodeAddress);
            counter++;
            operationHistory.addLocalVariable("counter", counter);
        }

        operationHistory.removeLocalVariable("counter");

        return currentNodeAddress;
    }

    private int getCount(OperationHistoryDto operationHistory) {
        return (int) operationHistory.getInstanceVariableValue("count");
    }

    private String getStart(OperationHistoryDto operationHistory) {
        return (String) operationHistory.getInstanceVariableValue("start");
    }

    private String getEnd(OperationHistoryDto operationHistory) {
        return (String) operationHistory.getInstanceVariableValue("end");
    }

    //TODO in remove, update start and end
    //TODO local variable creation-removal order
    //TODO don't update object, create new one
    //TODO we need to maintain variables in the memory for presentation to be valid,
    // shouldn't depend on actual variables in the program
}
