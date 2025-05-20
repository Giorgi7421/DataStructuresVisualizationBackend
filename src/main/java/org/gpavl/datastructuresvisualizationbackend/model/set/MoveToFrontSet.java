package org.gpavl.datastructuresvisualizationbackend.model.set;

import org.gpavl.datastructuresvisualizationbackend.model.MemoryHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.model.Node;
import org.gpavl.datastructuresvisualizationbackend.model.OperationHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import static org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils.convertToNode;

public class MoveToFrontSet extends Set {

    public MoveToFrontSet() {
        memoryHistory = new MemoryHistoryDto();
        OperationHistoryDto operationHistory = new OperationHistoryDto("MoveToFrontSet", Collections.emptyMap());

        operationHistory.addInstanceVariable("size", 0);
        operationHistory.addInstanceVariable("head", null);

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void size() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("size", memoryHistory);

        int size = (int) operationHistory.getInstanceVariableValue("size");
        operationHistory.addResult(size);

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void isEmpty() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("isEmpty", memoryHistory);

        int size = (int) operationHistory.getInstanceVariableValue("size");
        operationHistory.addResult(size == 0);

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void clear() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("clear", memoryHistory);

        String headAddress = (String) operationHistory.getInstanceVariableValue("head");
        while (headAddress != null) {
            Node next = convertToNode(operationHistory.getObject(headAddress));
            operationHistory.freeInstanceVariable("head", "head freed");
            operationHistory.addInstanceVariable("head", next.getNextAddress());
            headAddress = (String) operationHistory.getInstanceVariableValue("head");
        }

        operationHistory.addInstanceVariable("size", 0);

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void add(String element) {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("add", memoryHistory, "element", element);
        operationHistory.addLocalVariable("element", element);

        if (!containsWrapper(operationHistory, element)) {
            Node newNode = new Node();
            String address = operationHistory.addNewObject(newNode);
            operationHistory.addLocalVariable("newNode", address);

            Node newNode1 = new Node(element);
            operationHistory.updateObject(address, newNode1);

            Node newNode2 = new Node(element);
            String nextAddress = (String) operationHistory.getInstanceVariableValue("head");
            if (nextAddress != null) {
                newNode2.setNextAddress(nextAddress);
            }
            operationHistory.updateObject(address, newNode2);

            operationHistory.addInstanceVariable("head", address);
            operationHistory.removeLocalVariable("newNode");

            int size = (int) operationHistory.getInstanceVariableValue("size");
            size++;
            operationHistory.addInstanceVariable("size", size);
        }

        operationHistory.removeLocalVariable("element");
        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void remove(String element) {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("remove", memoryHistory, "element", element);
        operationHistory.addLocalVariable("element", element);

        if (containsWrapper(operationHistory, element)) {
            String cellToRemove = (String) operationHistory.getInstanceVariableValue("head");
            operationHistory.addLocalVariable("nodeToRemove", cellToRemove);

            Node headNode = convertToNode(operationHistory.getObject((String) operationHistory.getInstanceVariableValue("head")));
            operationHistory.addInstanceVariable("head", headNode.getNextAddress());

            operationHistory.freeLocalVariable("nodeToRemove", "Node freed");

            int size = (int) operationHistory.getInstanceVariableValue("size");
            size--;
            operationHistory.addInstanceVariable("size", size);
        }

        operationHistory.removeLocalVariable("element");

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void contains(String element) {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("contains", memoryHistory, "element", element);
        operationHistory.addLocalVariable("element", element);

        containsWrapper(operationHistory, element);

        operationHistory.removeLocalVariable("element");

        memoryHistory.addOperationHistory(operationHistory);
    }

    private boolean containsWrapper(OperationHistoryDto operationHistory, String element) {
        boolean result;

        String prev = null;
        operationHistory.addLocalVariable("prev", prev);

        String curr = (String) operationHistory.getInstanceVariableValue("head");
        Node currNode = curr != null ? convertToNode(operationHistory.getObject(curr)) : null;
        operationHistory.addLocalVariable("curr", curr);

        while (curr != null && !Objects.equals(currNode.getValue(), element)) {
            prev = curr;
            operationHistory.addLocalVariable("prev", curr);
            curr = currNode.getNextAddress();
            currNode = curr != null ? convertToNode(operationHistory.getObject(curr)) : null;
            operationHistory.addLocalVariable("curr", curr);
        }

        curr = (String) operationHistory.getLocalVariableValue("curr");
        String head = (String) operationHistory.getInstanceVariableValue("head");

        if (curr == null) {
            result = false;
            operationHistory.addResult(false);
        }else {
            if (!curr.equals(head)) {
                currNode = convertToNode(operationHistory.getObject(curr));

                Node prv = convertToNode(operationHistory.getObject(prev));
                var newPrv = Map.of(
                        "value", prv.getValue(),
                        "nextAddress", currNode.getNextAddress()
                );
                operationHistory.updateObject(prev, newPrv);

                Node cu = convertToNode(operationHistory.getObject(curr));
                var newCu = Map.of(
                        "value", cu.getValue(),
                        "nextAddress", (String) operationHistory.getInstanceVariableValue("head")
                );
                operationHistory.updateObject(curr, newCu);

                operationHistory.addInstanceVariable("head", curr);
            }
            result = true;
            operationHistory.addResult(true);
        }

        operationHistory.removeLocalVariable("curr");
        operationHistory.removeLocalVariable("prev");
        return result;
    }
}
