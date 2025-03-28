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

        operationHistory.addInstanceVariable("count", amount);

        String start = createLinkedNodes(amount, element, operationHistory);

        operationHistory.addInstanceVariable("start", start);

        operationHistory.removeLocalVariable("firstNodeAddress");
        operationHistory.removeLocalVariable("amount");
        operationHistory.removeLocalVariable("element");

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
        operationHistory.addLocalVariable("nextNodeAddress", nextNodeAddress);

        Node currentNode = new Node();
        currentNode.setValue(element);
        currentNode.setNextAddress(nextNodeAddress);

        String resultAddress = operationHistory.addNewObject(currentNode);
        operationHistory.addLocalVariable("firstNodeAddress", resultAddress);

        operationHistory.removeLocalVariable("nextNodeAddress");

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

        String currentNodeAddress = (String) operationHistory.getInstanceVariableValue("start");
        operationHistory.addLocalVariable(
                "current",
                currentNodeAddress
        );

        operationHistory.addInstanceVariable("count", 0);

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

        operationHistory.addLocalVariable("index", index);

        String targetNodeAddress = getNodeAtIndex(index, operationHistory);

        operationHistory.addLocalVariable("targetNode", targetNodeAddress);
        Node targetNode = (Node) operationHistory.getObject(targetNodeAddress);
        String value = targetNode.getValue();

        operationHistory.addResult(value);
        operationHistory.removeLocalVariable("index");
        operationHistory.removeLocalVariable("targetNode");

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

        operationHistory.addLocalVariable("index", index);
        operationHistory.addLocalVariable("element", element);

        String targetNodeAddress = getNodeAtIndex(index, operationHistory);
        operationHistory.addLocalVariable("targetNode", targetNodeAddress);

        Node targetNode = (Node) operationHistory.getObject(targetNodeAddress);
        targetNode.setValue(element);

        operationHistory.updateObject(targetNodeAddress, targetNode);
        operationHistory.removeLocalVariable("targetNode");
        operationHistory.removeLocalVariable("index");
        operationHistory.removeLocalVariable("element");

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
            Node endNode = (Node) operationHistory.getObject(end);

            endNode.setNextAddress(address);
            operationHistory.updateObject(end, newNode);
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

        operationHistory.addLocalVariable("index", index);
        operationHistory.addLocalVariable("element", element);
        Node newNode = new Node(element);
        String address = operationHistory.addNewObject(newNode);
        operationHistory.addLocalVariable("newNode", address);

        String targetNode;

        int count = getCount(operationHistory);
        String start = getStart(operationHistory);

        if (index == 0) {
            if (count != 0) {
                targetNode = start;
                operationHistory.addLocalVariable("targetNode", start);
            }else {
                operationHistory.addInstanceVariable("start", address);
                operationHistory.addInstanceVariable("end", address);
                count++;
                operationHistory.addInstanceVariable("count", count);
                operationHistory.removeLocalVariable("newNode");
                operationHistory.removeLocalVariable("index");
                operationHistory.removeLocalVariable("element");
                return;
            }
        }else {
            targetNode = getNodeAtIndex(index - 1, operationHistory);
            operationHistory.addLocalVariable("targetNode", targetNode);
        }

        String nextNode = operationHistory.getNextNodeAddress(targetNode);
        operationHistory.addLocalVariable("nextNode", nextNode);

        Node target = (Node) operationHistory.getObject(targetNode);
        target.setNextAddress(address);
        operationHistory.updateObject(targetNode, target);

        if (nextNode != null) {
            newNode.setNextAddress(nextNode);
            operationHistory.updateObject(address, newNode);
        }

        count++;
        operationHistory.addInstanceVariable("count", count);

        operationHistory.removeLocalVariable("newNode");
        operationHistory.removeLocalVariable("targetNode");
        operationHistory.removeLocalVariable("index");
        operationHistory.removeLocalVariable("element");

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
            previousTargetNode = start;
            operationHistory.addLocalVariable("previousTargetNode", start);
        }else {
            String nodeWithAddress = getNodeAtIndex(index - 1, operationHistory);
            previousTargetNode = nodeWithAddress;
            operationHistory.addLocalVariable("previousTargetNode", nodeWithAddress);
        }

        String targetNode = operationHistory.getNextNodeAddress(previousTargetNode);
        operationHistory.addLocalVariable("targetNode", targetNode);

        String nextNode = operationHistory.getNextNodeAddress(targetNode);
        operationHistory.addLocalVariable("nextNode", nextNode);

        Node previousTarget = (Node) operationHistory.getObject(previousTargetNode);
        if (nextNode == null) {
            previousTarget.setNextAddress(null);
        }else {
            previousTarget.setNextAddress(nextNode);
        }
        operationHistory.updateObject(previousTargetNode, previousTarget);

        operationHistory.freeLocalVariable("targetNode", "Deletion of the node is completed");
        operationHistory.removeLocalVariable("index");
        operationHistory.removeLocalVariable("previousTargetNode");
        operationHistory.removeLocalVariable("nextNode");
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
}
