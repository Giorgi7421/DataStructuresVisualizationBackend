package org.gpavl.datastructuresvisualizationbackend.model.set;

import org.gpavl.datastructuresvisualizationbackend.model.MemoryHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.model.OperationHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.model.map.HashMapNode;
import org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils.*;

public class HashSet extends Set {

    private static final int INITIAL_BUCKET_COUNT = 10;

    public HashSet() {
        memoryHistory = new MemoryHistoryDto();
        OperationHistoryDto operationHistory = new OperationHistoryDto("HashSet", Collections.emptyMap());

        operationHistory.addInstanceVariable("nBuckets", INITIAL_BUCKET_COUNT);

        List<String> elems = Collections.nCopies(INITIAL_BUCKET_COUNT, null);
        String newAddress = operationHistory.addNewObject(elems);
        operationHistory.addInstanceVariable("buckets", newAddress);

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
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("clear", memoryHistory);

        int nBuckets = (int) operationHistory.getInstanceVariableValue("nBuckets");
        List<String> buckets = getArray(operationHistory, "buckets");

        for (int i = 0; i < nBuckets; i++) {
            operationHistory.addLocalVariable("bucketIndex", i);

            String bucketAddress = buckets.get(i);
            operationHistory.addLocalVariable("bucketAddress", bucketAddress);

            HashSetNode node = bucketAddress != null ? convertToHashSetNode(operationHistory.getObject(bucketAddress)) : null;

            while (bucketAddress != null) {
                String oldBucketAddress = bucketAddress;
                operationHistory.addLocalVariable("oldBucketAddress", oldBucketAddress);

                bucketAddress = node.getLinkAddress();
                operationHistory.addLocalVariable("bucketAddress", bucketAddress);

                node = bucketAddress != null ? convertToHashSetNode(operationHistory.getObject(bucketAddress)) : null;

                operationHistory.freeLocalVariable("oldBucketAddress", "old address freed");
            }

            operationHistory.removeLocalVariable("bucketAddress");

            operationHistory.removeLocalVariable("bucketIndex");
        }

        operationHistory.addInstanceVariable("count", 0);

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void add(String element) {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("add", memoryHistory, "element", element);

        operationHistory.addLocalVariable("element", element);

        int nBuckets = (int) operationHistory.getInstanceVariableValue("nBuckets");
        List<String> buckets = getArray(operationHistory, "buckets");

        int bucket = Math.abs(element.hashCode() % nBuckets);
        operationHistory.addLocalVariable("bucket", bucket);

        String targetNodeAddress = findNode(operationHistory, bucket, element);
        operationHistory.addLocalVariable("targetNode", targetNodeAddress);
        operationHistory.removeLocalVariable("currentNode");

        if (targetNodeAddress == null) {
            HashSetNode newNode = new HashSetNode();
            newNode.setElement(element);
            newNode.setLinkAddress(buckets.get(bucket));

            String newNodeAddress = operationHistory.addNewObject(newNode);

            buckets = new ArrayList<>(buckets);
            buckets.set(bucket, newNodeAddress);
            updateArray(buckets, operationHistory, "buckets");

            int count = (int) operationHistory.getInstanceVariableValue("count");
            count++;
            operationHistory.addInstanceVariable("count", count);
        }

        operationHistory.removeLocalVariable("targetNode");
        operationHistory.removeLocalVariable("bucket");

        operationHistory.removeLocalVariable("element");

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void remove(String element) {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("remove", memoryHistory, "element", element);

        operationHistory.addLocalVariable("element", element);

        int nBuckets = (int) operationHistory.getInstanceVariableValue("nBuckets");

        int bucket = Math.abs(element.hashCode() % nBuckets);
        operationHistory.addLocalVariable("bucket", bucket);

        String targetNodeAddress = findPreviousNode(operationHistory, bucket, element);
        operationHistory.addLocalVariable("targetNode", targetNodeAddress);
        operationHistory.removeLocalVariable("currentNode");

        if (targetNodeAddress != null) {
            HashSetNode targetNode = convertToHashSetNode(operationHistory.getObject(targetNodeAddress));
            String temp = targetNode.getLinkAddress();
            operationHistory.addLocalVariable("temp", temp);

            HashSetNode actualTarget = convertToHashSetNode(operationHistory.getObject(temp));

            HashSetNode newTargetNode = new HashSetNode();
            newTargetNode.setElement(targetNode.getElement());
            newTargetNode.setLinkAddress(actualTarget.getLinkAddress());

            operationHistory.updateObject(targetNodeAddress, newTargetNode);
            operationHistory.freeLocalVariable("temp", "node freed");
        }

        operationHistory.removeLocalVariable("targetNode");
        operationHistory.removeLocalVariable("bucket");
        operationHistory.removeLocalVariable("element");

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void contains(String element) {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("contains", memoryHistory, "element", element);

        operationHistory.addLocalVariable("element", element);

        int nBuckets = (int) operationHistory.getInstanceVariableValue("nBuckets");

        int bucket = Math.abs(element.hashCode() % nBuckets);
        operationHistory.addLocalVariable("bucket", bucket);

        String targetNodeAddress = findNode(operationHistory, bucket, element);
        operationHistory.addLocalVariable("targetNode", targetNodeAddress);
        operationHistory.removeLocalVariable("currentNode");

        operationHistory.addResult(targetNodeAddress != null);

        operationHistory.removeLocalVariable("targetNode");
        operationHistory.removeLocalVariable("bucket");

        operationHistory.removeLocalVariable("key");

        memoryHistory.addOperationHistory(operationHistory);
    }

    private String findNode(OperationHistoryDto operationHistory, int bucket, String element) {
        List<String> buckets = getArray(operationHistory, "buckets");

        String currentNodeAddress = buckets.get(bucket);
        operationHistory.addLocalVariable("currentNode", currentNodeAddress);

        HashSetNode currentNode = currentNodeAddress != null ? convertToHashSetNode(operationHistory.getObject(currentNodeAddress)) : null;

        while (currentNodeAddress != null && !element.equals(currentNode.getElement())) {
            currentNodeAddress = currentNode.getLinkAddress();
            operationHistory.addLocalVariable("currentNode", currentNodeAddress);
            currentNode = currentNodeAddress != null ? convertToHashSetNode(operationHistory.getObject(currentNodeAddress)) : null;
        }
        return currentNodeAddress;
    }

    private String findPreviousNode(OperationHistoryDto operationHistory, int bucket, String element) {
        List<String> buckets = getArray(operationHistory, "buckets");

        String currentNodeAddress = buckets.get(bucket);
        operationHistory.addLocalVariable("currentNode", currentNodeAddress);
        HashSetNode currentNode = currentNodeAddress != null ? convertToHashSetNode(operationHistory.getObject(currentNodeAddress)) : null;

        String nextNodeAddress = currentNode != null ? currentNode.getLinkAddress() : null;
        operationHistory.addLocalVariable("nextNode", nextNodeAddress);
        HashSetNode nextNode = nextNodeAddress != null ? convertToHashSetNode(operationHistory.getObject(nextNodeAddress)) : null;

        while (nextNodeAddress != null && !element.equals(nextNode.getElement())) {
            currentNodeAddress = currentNode.getLinkAddress();
            operationHistory.addLocalVariable("currentNode", currentNodeAddress);
            currentNode = currentNodeAddress != null ? convertToHashSetNode(operationHistory.getObject(currentNodeAddress)) : null;

            nextNodeAddress = currentNode != null ? currentNode.getLinkAddress() : null;
            operationHistory.addLocalVariable("nextNode", nextNodeAddress);
            nextNode = nextNodeAddress != null ? convertToHashSetNode(operationHistory.getObject(nextNodeAddress)) : null;
        }
        return currentNodeAddress;
    }
}
