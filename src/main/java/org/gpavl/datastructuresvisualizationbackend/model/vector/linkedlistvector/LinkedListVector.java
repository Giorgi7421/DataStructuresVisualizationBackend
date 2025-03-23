package org.gpavl.datastructuresvisualizationbackend.model.vector.linkedlistvector;

import lombok.Getter;
import lombok.Setter;
import org.gpavl.datastructuresvisualizationbackend.model.MemoryHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.model.Node;
import org.gpavl.datastructuresvisualizationbackend.model.vector.Vector;

@Getter
@Setter
public class LinkedListVector implements Vector {

    private Node start;
    private Node end;
    private int count;

    private MemoryHistoryDto memoryHistory;

    public LinkedListVector() {
        count = 0;
    }

    public LinkedListVector(int amount, String element) {
        count = amount;
        start = createLinkedNodes(amount, element);
    }

    public Node createLinkedNodes(int amount, String element) {
        if (amount == 1) {
            Node node = new Node(element);
            end = node;
            return node;
        }
        Node nextNode = createLinkedNodes(amount - 1, element);
        Node currentNode = new Node();
        currentNode.setValue(element);
        currentNode.setNextNode(nextNode);
        return currentNode;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public boolean isEmpty() {
        return count == 0;
    }

    @Override
    public void clear() {
        Node current = start;
        while (current != null) {
            Node next = current.getNextNode();
            //free();
            current = next;
        }
        count = 0;
        start = null;
        end = null;
    }

    @Override
    public String get(int index) {
        Node targetNode = getNodeAtIndex(index);
        return targetNode.getValue();
    }

    @Override
    public void set(int index, String element) {
        Node targetNode = getNodeAtIndex(index);
        targetNode.setValue(element);
    }

    @Override
    public void add(String element) {
        Node newNode = new Node(element);
        if (count != 0) {
            end.setNextNode(newNode);
        }else {
            start = newNode;
        }
        end = newNode;
        count++;
    }

    @Override
    public void insertAt(int index, String element) {
        Node newNode = new Node(element);
        Node targetNode;

        if (index == 0) {
            if (count != 0) {
                targetNode = start;
            }else {
                start = newNode;
                end = newNode;
                count++;
                return;
            }
        }else {
            targetNode = getNodeAtIndex(index - 1);
        }

        Node nextNode = targetNode.getNextNode();
        targetNode.setNextNode(newNode);

        if (nextNode != null) {
            newNode.setNextNode(nextNode);
        }

        count++;
    }

    @Override
    public void removeAt(int index) {
        if (index < 0 || index >= count) {
            throw new IllegalArgumentException("index out of range");
        }

        Node previousTargetNode;

        if (index == 0) {
            previousTargetNode = start;
        }else {
            previousTargetNode = getNodeAtIndex(index - 1);
        }

        Node targetNode = previousTargetNode.getNextNode();

        Node nextNode = targetNode.getNextNode();
        if (nextNode == null) {
            //free();
            previousTargetNode.setNextNode(null);
        }else {
            previousTargetNode.setNextNode(nextNode);
            //free();
        }
    }

    private Node getNodeAtIndex(int index) {
        if (index < 0 || index >= count) {
            throw new IllegalArgumentException("index out of range");
        }

        int count = 0;
        Node currentNode = start;
        while (count != index) {
            currentNode = currentNode.getNextNode();
            count++;
        }
        return currentNode;
    }
}
