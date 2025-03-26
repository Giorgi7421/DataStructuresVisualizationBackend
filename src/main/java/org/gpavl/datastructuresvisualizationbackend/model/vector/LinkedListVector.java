//package org.gpavl.datastructuresvisualizationbackend.model.vector;
//
//import lombok.Getter;
//import lombok.Setter;
//import org.gpavl.datastructuresvisualizationbackend.model.MemoryHistoryDto;
//import org.gpavl.datastructuresvisualizationbackend.model.MemoryNode;
//
//@Getter
//@Setter
//public class LinkedListVector implements Vector {
//
//    private Node start;
//    private Node end;
//    private int count;
//
//    private MemoryHistoryDto memoryHistory;
//
//    public LinkedListVector() {
//        count = 0;
//        memoryHistory.addInstanceVariableToMemory("count", count);
//
//        memoryHistory.addInstanceVariableToMemory("start", null);
//        memoryHistory.addInstanceVariableToMemory("end", null);
//    }
//
//    public LinkedListVector(int amount, String element) {
//        if (amount <= 0) {
//            throw new IllegalArgumentException("Illegal amount of elements");
//        }
//
//        memoryHistory.addLocalVariableToMemory("amount", amount);
//        memoryHistory.addLocalVariableToMemory("element", element);
//
//        count = amount;
//        memoryHistory.addInstanceVariableToMemory("count", count);
//
//        NodeWithAddress nodeWithAddress = createLinkedNodes(amount, element);
//        start = nodeWithAddress.getNode();
//
//        memoryHistory.addInstanceVariableToMemory("start", nodeWithAddress.getAddress());
//        memoryHistory.removeLocalVariableFromMemory("nextNode");
//        memoryHistory.removeLocalVariableFromMemory("firstNode");
//
//        memoryHistory.removeLocalVariableFromMemory("amount");
//        memoryHistory.removeLocalVariableFromMemory("element");
//    }
//
//    public NodeWithAddress createLinkedNodes(int amount, String element) {
//        if (amount == 1) {
//            Node newNode = new Node(element);
//            MemoryNode memoryNode = new MemoryNode(element);
//            String address = memoryHistory.addNewObjectInAddressMemoryMap(memoryNode);
//            memoryHistory.addLocalVariableToMemory("newNode", address);
//
//            end = newNode;
//            memoryHistory.addInstanceVariableToMemory("end", address);
//            memoryHistory.removeLocalVariableFromMemory("newNode");
//            return new NodeWithAddress(newNode, address);
//        }
//        NodeWithAddress nextNodeWithAddress = createLinkedNodes(amount - 1, element);
//        Node nextNode = nextNodeWithAddress.getNode();
//        String address = nextNodeWithAddress.getAddress();
//        memoryHistory.addLocalVariableToMemory("nextNode", address);
//
//        Node currentNode = new Node();
//        currentNode.setValue(element);
//        currentNode.setNextNode(nextNode);
//
//        MemoryNode currentmemoryNode = new MemoryNode();
//        currentmemoryNode.setValue(element);
//        currentmemoryNode.setNextAddress(address);
//        String resultAddress = memoryHistory.addNewObjectInAddressMemoryMap(currentmemoryNode);
//        memoryHistory.addLocalVariableToMemory("firstNode", resultAddress);
//
//        return new NodeWithAddress(currentNode, resultAddress);
//    }
//
//    @Override
//    public int size() {
//        return count;
//    }
//
//    @Override
//    public boolean isEmpty() {
//        return count == 0;
//    }
//
//    @Override
//    public void clear() {
//        Node current = start;
//        memoryHistory.addLocalVariableToMemory(
//                "current",
//                memoryHistory.getInstanceVariableAddress("start")
//        );
//
//        count = 0;
//        memoryHistory.addInstanceVariableToMemory("count", count);
//
//        start = null;
//        memoryHistory.addInstanceVariableToMemory("start", null);
//        end = null;
//        memoryHistory.addInstanceVariableToMemory("end", null);
//
//        while (current != null) {
//            Node next = current.getNextNode();
//            memoryHistory.addLocalVariableToMemory(
//                    "next",
//                    memoryHistory.getNextNodeAddress(memoryHistory.getInstanceVariableAddress("current"))
//            );
//            memoryHistory.freeInstanceVariable("current", null);
//            memoryHistory.addInstanceVariableToMemory("current", null);
//            current = next;
//            memoryHistory.addLocalVariableToMemory("current", memoryHistory.getInstanceVariableAddress("next"));
//            memoryHistory.removeLocalVariableFromMemory("next");
//        }
//        memoryHistory.removeLocalVariableFromMemory("current");
//        memoryHistory.getMemorySnapshots().getLast().setMessage("Freeing the memory is finished");
//    }
//
//    @Override
//    public String get(int index) {
//        memoryHistory.addLocalVariableToMemory("index", index);
//        NodeWithAddress targetNodeWithAddress = getNodeAtIndex(index);
//        Node targetNode = targetNodeWithAddress.getNode();
//        memoryHistory.addLocalVariableToMemory("targetNode", targetNodeWithAddress.getAddress());
//        String value = targetNode.getValue();
//        memoryHistory.addLocalVariableToMemory("value", value);
//        memoryHistory.removeLocalVariableFromMemory("index");
//        memoryHistory.removeLocalVariableFromMemory("targetNode");
//        memoryHistory.removeLocalVariableFromMemory("value");
//        return value;
//    }
//
//    @Override
//    public void set(int index, String element) {
//        memoryHistory.addLocalVariableToMemory("index", index);
//        memoryHistory.addLocalVariableToMemory("element", element);
//
//        NodeWithAddress targetNodeWithAddress = getNodeAtIndex(index);
//        Node targetNode = targetNodeWithAddress.getNode();
//        memoryHistory.addLocalVariableToMemory("targetNode", targetNodeWithAddress.getAddress());
//
//        targetNode.setValue(element);
//        memoryHistory.updateObjectInAddressMemoryMap(targetNodeWithAddress.getAddress(), targetNodeWithAddress);
//        memoryHistory.removeLocalVariableFromMemory("targetNode");
//        memoryHistory.removeLocalVariableFromMemory("index");
//        memoryHistory.removeLocalVariableFromMemory("element");
//    }
//
//    @Override
//    public void add(String element) {
//        memoryHistory.addLocalVariableToMemory("element", element);
//        Node newNode = new Node(element);
//        MemoryNode memoryNode = new MemoryNode(element);
//        String address = memoryHistory.addNewObjectInAddressMemoryMap(memoryNode);
//        memoryHistory.addInstanceVariableToMemory("newNode", address);
//
//        if (count != 0) {
//            end.setNextNode(newNode);
//            String endAddress = memoryHistory.getInstanceVariableAddress("end");
//            memoryHistory.updateObjectInAddressMemoryMap(endAddress, memoryNode);
//        }else {
//            start = newNode;
//            memoryHistory.addInstanceVariableToMemory("start", address);
//        }
//        end = newNode;
//        memoryHistory.addInstanceVariableToMemory("end", address);
//        count++;
//        memoryHistory.addInstanceVariableToMemory("count", count);
//        memoryHistory.removeLocalVariableFromMemory("newNode");
//        memoryHistory.removeLocalVariableFromMemory("element");
//    }
//
//    @Override
//    public void insertAt(int index, String element) {
//        memoryHistory.addInstanceVariableToMemory("index", index);
//        memoryHistory.addInstanceVariableToMemory("element", element);
//        Node newNode = new Node(element);
//        MemoryNode memoryNode = new MemoryNode(element);
//        String address = memoryHistory.addNewObjectInAddressMemoryMap(memoryNode);
//        memoryHistory.addInstanceVariableToMemory("newNode", address);
//        Node targetNode;
//
//        if (index == 0) {
//            if (count != 0) {
//                targetNode = start;
//                memoryHistory.addLocalVariableToMemory("targetNode", memoryHistory.getInstanceVariableAddress("start"));
//            }else {
//                start = newNode;
//                memoryHistory.addInstanceVariableToMemory("start", address);
//                end = newNode;
//                memoryHistory.addInstanceVariableToMemory("end", address);
//                count++;
//                memoryHistory.addInstanceVariableToMemory("count", count);
//                memoryHistory.removeLocalVariableFromMemory("newNode");
//                memoryHistory.removeLocalVariableFromMemory("index");
//                memoryHistory.removeLocalVariableFromMemory("element");
//                return;
//            }
//        }else {
//            NodeWithAddress nodeWithAddress = getNodeAtIndex(index - 1);
//            targetNode = nodeWithAddress.getNode();
//            memoryHistory.addLocalVariableToMemory("targetNode", nodeWithAddress.getAddress());
//        }
//
//        Node nextNode = targetNode.getNextNode();
//        memoryHistory.addLocalVariableToMemory(
//                "next",
//                memoryHistory.getNextNodeAddress(memoryHistory.getInstanceVariableAddress("targetNode"))
//        );
//        targetNode.setNextNode(newNode);
//        String targetNodeAddress = memoryHistory.getInstanceVariableAddress("targetNode");
//        memoryHistory.updateObjectInAddressMemoryMap(targetNodeAddress, newNode);
//
//        if (nextNode != null) {
//            newNode.setNextNode(nextNode);
//            String newNodeAddress = memoryHistory.getInstanceVariableAddress("newNode");
//            memoryHistory.updateObjectInAddressMemoryMap(newNodeAddress, nextNode);
//        }
//
//        count++;
//        memoryHistory.addInstanceVariableToMemory("count", count);
//        memoryHistory.removeLocalVariableFromMemory("newNode");
//        memoryHistory.removeLocalVariableFromMemory("targetNode");
//        memoryHistory.removeLocalVariableFromMemory("index");
//        memoryHistory.removeLocalVariableFromMemory("element");
//    }
//
//    @Override
//    public void removeAt(int index) {
//        memoryHistory.addLocalVariableToMemory("index", index);
//
//        if (index < 0 || index >= count) {
//            throw new IllegalArgumentException("index out of range");
//        }
//
//        Node previousTargetNode;
//
//        if (index == 0) {
//            previousTargetNode = start;
//            memoryHistory.addLocalVariableToMemory("previousTargetNode", memoryHistory.getInstanceVariableAddress("start"));
//        }else {
//            NodeWithAddress nodeWithAddress = getNodeAtIndex(index - 1);
//            previousTargetNode = nodeWithAddress.getNode();
//            memoryHistory.addLocalVariableToMemory("previousTargetNode", nodeWithAddress.getAddress());
//        }
//
//        Node targetNode = previousTargetNode.getNextNode();
//        String targetNodeAddress = memoryHistory.getNextNodeAddress(memoryHistory.getLocalVariableAddress("previousTargetNode"));
//        memoryHistory.addLocalVariableToMemory("targetNode", targetNodeAddress);
//
//        Node nextNode = targetNode.getNextNode();
//        String nextNodeAddress = memoryHistory.getNextNodeAddress(memoryHistory.getLocalVariableAddress("targetNode"));
//        memoryHistory.addLocalVariableToMemory("nextNode", nextNodeAddress);
//
//        if (nextNode == null) {
//            previousTargetNode.setNextNode(null);
//
//            MemoryNode previousMemoryNode = (MemoryNode) memoryHistory.getAddressObjectFromMemory(memoryHistory.getLocalVariableAddress("previousTargetNode"));
//            previousMemoryNode.setNextAddress(null);
//            memoryHistory.updateObjectInAddressMemoryMap(memoryHistory.getLocalVariableAddress("previousTargetNode"), previousMemoryNode);
//        }else {
//            previousTargetNode.setNextNode(nextNode);
//
//            MemoryNode previousMemoryNode = (MemoryNode) memoryHistory.getAddressObjectFromMemory(memoryHistory.getLocalVariableAddress("previousTargetNode"));
//            previousMemoryNode.setNextAddress(memoryHistory.getLocalVariableAddress("nextNode"));
//            memoryHistory.updateObjectInAddressMemoryMap(memoryHistory.getLocalVariableAddress("previousTargetNode"), previousMemoryNode);
//        }
//        memoryHistory.freeLocalVariable("targetNode", "Deletion of the node is completed");
//        memoryHistory.removeLocalVariableFromMemory("index");
//        memoryHistory.removeLocalVariableFromMemory("previousTargetNode");
//        memoryHistory.removeLocalVariableFromMemory("nextNode");
//    }
//
//    private NodeWithAddress getNodeAtIndex(int index) {
//        if (index < 0 || index >= count) {
//            throw new IllegalArgumentException("index out of range");
//        }
//
//        int counter = 0;
//        memoryHistory.addLocalVariableToMemory("counter", counter);
//
//        Node current = start;
//        memoryHistory.addLocalVariableToMemory(
//                "current",
//                memoryHistory.getInstanceVariableAddress("start")
//        );
//
//        while (counter != index) {
//            current = current.getNextNode();
//            memoryHistory.addLocalVariableToMemory(
//                    "current",
//                    memoryHistory.getNextNodeAddress(memoryHistory.getInstanceVariableAddress("current"))
//            );
//            counter++;
//            memoryHistory.addLocalVariableToMemory("counter", counter);
//        }
//        return new NodeWithAddress(current, memoryHistory.getInstanceVariableAddress("current"));
//    }
//}
