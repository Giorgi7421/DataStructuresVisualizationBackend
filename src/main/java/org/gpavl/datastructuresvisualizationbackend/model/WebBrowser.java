package org.gpavl.datastructuresvisualizationbackend.model;

import org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils;

import java.util.Collections;

import static org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils.convertToDoublyLinkedNode;

public class WebBrowser extends DataStructure {

    public WebBrowser() {
        memoryHistory = new MemoryHistoryDto();
        OperationHistoryDto operationHistory = new OperationHistoryDto("WebBrowser", Collections.emptyMap());

        DoublyLinkedNode node = new DoublyLinkedNode();
        node.setValue("home");

        String address = operationHistory.addNewObject(node);
        operationHistory.addInstanceVariable("current", address);

        memoryHistory.addOperationHistory(operationHistory);
    }

    public void visit(String url) {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("visit", memoryHistory, "url", url);

        String currentAddress = (String) operationHistory.getInstanceVariableValue("current");
        DoublyLinkedNode current = convertToDoublyLinkedNode(operationHistory.getObject(currentAddress));

        String nextAddress = current.getNextAddress();

        while (nextAddress != null) {
            DoublyLinkedNode next = convertToDoublyLinkedNode(operationHistory.getObject(nextAddress));
            operationHistory.addLocalVariable("toDelete", next);

            DoublyLinkedNode nw = new DoublyLinkedNode();
            nw.setValue(current.getValue());
            nw.setPreviousAddress(current.getPreviousAddress());
            nw.setNextAddress(next.getNextAddress());

            operationHistory.updateObject(currentAddress, nw);

            operationHistory.freeLocalVariable("toDelete", "Node was freed");
            nextAddress = next.getNextAddress();
        }

        DoublyLinkedNode newNode = new DoublyLinkedNode();
        String newAddress = operationHistory.addNewObject(newNode);

        DoublyLinkedNode newNode1 = new DoublyLinkedNode();
        newNode1.setValue(url);
        operationHistory.updateObject(newAddress, newNode1);

        DoublyLinkedNode newNode2 = new DoublyLinkedNode();
        newNode2.setValue(url);
        newNode2.setPreviousAddress(currentAddress);
        operationHistory.updateObject(newAddress, newNode2);

        DoublyLinkedNode curr = new DoublyLinkedNode();
        curr.setValue(current.getValue());
        curr.setPreviousAddress(current.getPreviousAddress());
        curr.setNextAddress(newAddress);

        operationHistory.updateObject(currentAddress, curr);

        memoryHistory.addOperationHistory(operationHistory);
    }

    public void back() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("back", memoryHistory);

        String currentAddress = (String) operationHistory.getInstanceVariableValue("current");
        DoublyLinkedNode current = convertToDoublyLinkedNode(operationHistory.getObject(currentAddress));

        if (current.getNextAddress() != null) {
            operationHistory.addInstanceVariable("current", current.getNextAddress());
        }

        memoryHistory.addOperationHistory(operationHistory);
    }

    public void forward() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("forward", memoryHistory);

        String currentAddress = (String) operationHistory.getInstanceVariableValue("current");
        DoublyLinkedNode current = convertToDoublyLinkedNode(operationHistory.getObject(currentAddress));

        if (current.getPreviousAddress() != null) {
            operationHistory.addInstanceVariable("current", current.getPreviousAddress());
        }

        memoryHistory.addOperationHistory(operationHistory);
    }
}
