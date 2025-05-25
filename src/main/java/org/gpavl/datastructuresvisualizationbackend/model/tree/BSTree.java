package org.gpavl.datastructuresvisualizationbackend.model.tree;

import org.gpavl.datastructuresvisualizationbackend.model.MemoryHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.model.OperationHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.model.TreeNode;
import org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils;

import java.util.Collections;
import java.util.Objects;

import static org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils.convertToTreeNode;

public class BSTree extends Tree {

    public BSTree() {
        memoryHistory = new MemoryHistoryDto();
        OperationHistoryDto operationHistory = new OperationHistoryDto("BSTree", Collections.emptyMap());

        operationHistory.addInstanceVariable("root", null);

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void insert(String element) {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("insert", memoryHistory, "element", element);
        operationHistory.addLocalVariable("element", element);

        String rootAddress = (String) operationHistory.getInstanceVariableValue("root");
        String address = insertWrapper(operationHistory, rootAddress, element);
        operationHistory.addInstanceVariable("root", address);
        operationHistory.removeLocalVariable("current");

        operationHistory.removeLocalVariable("element");
        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void remove(String element) {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("remove", memoryHistory, "element", element);
        operationHistory.addLocalVariable("element", element);

        String rootAddress = (String) operationHistory.getInstanceVariableValue("root");
        String result = removeWrapper(operationHistory, rootAddress, element);
        operationHistory.addInstanceVariable("root", result);
        operationHistory.removeLocalVariable("current");
        operationHistory.removeLocalVariable("temp");

        operationHistory.removeLocalVariable("element");
        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void search(String element) {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("search", memoryHistory, "element", element);
        operationHistory.addLocalVariable("element", element);

        String rootAddress = (String) operationHistory.getInstanceVariableValue("root");
        boolean found = searchWrapper(operationHistory, rootAddress, element);
        operationHistory.addResult(found);

        operationHistory.removeLocalVariable("element");
        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void clear() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("clear", memoryHistory);

        String rootAddress = (String) operationHistory.getInstanceVariableValue("root");
        clearWrapper(operationHistory, rootAddress);

        operationHistory.addInstanceVariable("root", null);

        memoryHistory.addOperationHistory(operationHistory);
    }

    private String insertWrapper(OperationHistoryDto operationHistory, String current, String element) {
        operationHistory.addLocalVariable("current", current);

        if (current == null) {
            TreeNode treeNode = new TreeNode(element);
            operationHistory.removeLocalVariable("current");
            return operationHistory.addNewObject(treeNode);
        }

        TreeNode currentNode = convertToTreeNode(operationHistory.getObject(current));

        if (element.compareTo(currentNode.getValue()) < 0) {
            currentNode.setLeft(insertWrapper(operationHistory, currentNode.getLeft(), element));
            operationHistory.addLocalVariable("current", current);
            operationHistory.updateObject(current, currentNode);
        } else if (element.compareTo(currentNode.getValue()) > 0) {
            currentNode.setRight(insertWrapper(operationHistory, currentNode.getRight(), element));
            operationHistory.addLocalVariable("current", current);
            operationHistory.updateObject(current, currentNode);
        }

        return current;
    }

    private boolean searchWrapper(OperationHistoryDto operationHistory, String current, String element) {
        operationHistory.addLocalVariable("current", current);

        if (current == null) {
            operationHistory.removeLocalVariable("current");
            return false;
        }

        TreeNode currentNode = convertToTreeNode(operationHistory.getObject(current));

        if (Objects.equals(currentNode.getValue(), element)) {
            operationHistory.removeLocalVariable("current");
            return true;
        }

        if (element.compareTo(currentNode.getValue()) < 0) {
            operationHistory.removeLocalVariable("current");
            return searchWrapper(operationHistory, currentNode.getLeft(), element);
        }else {
            operationHistory.removeLocalVariable("current");
            return searchWrapper(operationHistory, currentNode.getRight(), element);
        }
    }

    private String removeWrapper(OperationHistoryDto operationHistory, String current, String element) {
        operationHistory.addLocalVariable("current", current);

        if (current == null) {
            return null;
        }

        TreeNode currentNode = convertToTreeNode(operationHistory.getObject(current));

        if (element.compareTo(currentNode.getValue()) < 0) {
            currentNode.setLeft(removeWrapper(operationHistory, currentNode.getLeft(), element));
            operationHistory.addLocalVariable("current", current);
            operationHistory.updateObject(current, currentNode);
        }else if (element.compareTo(currentNode.getValue()) > 0) {
            currentNode.setRight(removeWrapper(operationHistory, currentNode.getRight(), element));
            operationHistory.addLocalVariable("current", current);
            operationHistory.updateObject(current, currentNode);
        }else {
            if (currentNode.getLeft() == null) {
                String temp = currentNode.getRight();
                operationHistory.addLocalVariable("temp", temp);

                operationHistory.freeLocalVariable("current", "current freed");
                return temp;
            }else if (currentNode.getRight() == null) {
                String temp = currentNode.getLeft();
                operationHistory.addLocalVariable("temp", temp);

                operationHistory.freeLocalVariable("current", "current freed");
                return temp;
            }

            String temp = findMinNode(operationHistory, currentNode.getRight());
            TreeNode tempNode = convertToTreeNode(operationHistory.getObject(temp));

            currentNode.setValue(tempNode.getValue());
            operationHistory.updateObject(current, currentNode);

            currentNode.setRight(removeWrapper(operationHistory, currentNode.getRight(), tempNode.getValue()));
            operationHistory.addLocalVariable("current", current);
        }

        return current;
    }

    private String findMinNode(OperationHistoryDto operationHistory, String node) {
        String curr = node;
        operationHistory.addLocalVariable("curr", curr);

        TreeNode currentNode = curr != null ? convertToTreeNode(operationHistory.getObject(curr)) : null;

        while (curr != null && currentNode.getLeft() != null) {
            curr = currentNode.getLeft();
            currentNode = convertToTreeNode(operationHistory.getObject(curr));
            operationHistory.addLocalVariable("curr", curr);
        }

        operationHistory.removeLocalVariable("curr");
        return curr;
    }

    private void clearWrapper(OperationHistoryDto operationHistory, String current) {
        operationHistory.addLocalVariable("current", current);
        TreeNode currentNode = current != null ? convertToTreeNode(operationHistory.getObject(current)) : null;

        if (current != null) {
            clearWrapper(operationHistory, currentNode.getLeft());
            operationHistory.addLocalVariable("current", current);
            clearWrapper(operationHistory, currentNode.getRight());
            operationHistory.addLocalVariable("current", current);
            operationHistory.freeLocalVariable("current", "current freed");
        }
    }
}
