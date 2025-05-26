package org.gpavl.datastructuresvisualizationbackend.model.map;

import org.gpavl.datastructuresvisualizationbackend.model.MemoryHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.model.OperationHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils.*;

public class HashMap extends Map {

    private static final int INITIAL_BUCKET_COUNT = 10;

    public HashMap() {
        memoryHistory = new MemoryHistoryDto();
        OperationHistoryDto operationHistory = new OperationHistoryDto("HashMap", Collections.emptyMap());

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

            HashMapNode node = bucketAddress != null ? convertToHashMapNode(operationHistory.getObject(bucketAddress)) : null;

            while (bucketAddress != null) {
                String oldBucketAddress = bucketAddress;
                operationHistory.addLocalVariable("oldBucketAddress", oldBucketAddress);

                bucketAddress = node.getLinkAddress();
                operationHistory.addLocalVariable("bucketAddress", bucketAddress);

                node = bucketAddress != null ? convertToHashMapNode(operationHistory.getObject(bucketAddress)) : null;

                operationHistory.freeLocalVariable("oldBucketAddress", "old address freed");
            }

            operationHistory.removeLocalVariable("bucketAddress");

            operationHistory.removeLocalVariable("bucketIndex");
        }

        operationHistory.addInstanceVariable("count", 0);

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void put(String key, String value) {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("put", memoryHistory, "key", key, "value", value);

        operationHistory.addLocalVariable("key", key);
        operationHistory.addLocalVariable("value", value);

        int nBuckets = (int) operationHistory.getInstanceVariableValue("nBuckets");
        List<String> buckets = getArray(operationHistory, "buckets");

        int bucket = Math.abs(key.hashCode() % nBuckets);
        operationHistory.addLocalVariable("bucket", bucket);

        String targetNodeAddress = findNode(operationHistory, bucket, key);
        operationHistory.addLocalVariable("targetNode", targetNodeAddress);
        operationHistory.removeLocalVariable("currentNode");

        if (targetNodeAddress == null) {
            HashMapNode newNode = new HashMapNode();
            newNode.setKey(key);
            newNode.setLinkAddress(buckets.get(bucket));

            String newNodeAddress = operationHistory.addNewObject(newNode);
            targetNodeAddress = newNodeAddress;

            buckets = new ArrayList<>(buckets);
            buckets.set(bucket, newNodeAddress);
            updateArray(buckets, operationHistory, "buckets");

            int count = (int) operationHistory.getInstanceVariableValue("count");
            count++;
            operationHistory.addInstanceVariable("count", count);
        }
        HashMapNode storedNode = convertToHashMapNode(operationHistory.getObject(targetNodeAddress));

        HashMapNode newNode1 = new HashMapNode();
        newNode1.setKey(storedNode.getKey());
        newNode1.setLinkAddress(storedNode.getLinkAddress());
        newNode1.setValue(value);

        operationHistory.updateObject(targetNodeAddress, newNode1);
        operationHistory.removeLocalVariable("targetNode");
        operationHistory.removeLocalVariable("bucket");

        operationHistory.removeLocalVariable("value");
        operationHistory.removeLocalVariable("key");

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void get(String key) {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("get", memoryHistory, "key", key);

        operationHistory.addLocalVariable("key", key);

        int nBuckets = (int) operationHistory.getInstanceVariableValue("nBuckets");

        int bucket = Math.abs(key.hashCode() % nBuckets);
        operationHistory.addLocalVariable("bucket", bucket);

        String targetNodeAddress = findNode(operationHistory, bucket, key);
        operationHistory.addLocalVariable("targetNode", targetNodeAddress);
        operationHistory.removeLocalVariable("currentNode");

        if (targetNodeAddress == null) {
            throw new IllegalArgumentException("Key not found");
        }

        HashMapNode storedNode = convertToHashMapNode(operationHistory.getObject(targetNodeAddress));
        operationHistory.addResult(storedNode.getValue());

        operationHistory.removeLocalVariable("targetNode");
        operationHistory.removeLocalVariable("bucket");

        operationHistory.removeLocalVariable("key");

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void containsKey(String key) {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("containsKey", memoryHistory, "key", key);

        operationHistory.addLocalVariable("key", key);

        int nBuckets = (int) operationHistory.getInstanceVariableValue("nBuckets");

        int bucket = Math.abs(key.hashCode() % nBuckets);
        operationHistory.addLocalVariable("bucket", bucket);

        String targetNodeAddress = findNode(operationHistory, bucket, key);
        operationHistory.addLocalVariable("targetNode", targetNodeAddress);
        operationHistory.removeLocalVariable("currentNode");

        operationHistory.addResult(targetNodeAddress != null);

        operationHistory.removeLocalVariable("targetNode");
        operationHistory.removeLocalVariable("bucket");

        operationHistory.removeLocalVariable("key");
        
        memoryHistory.addOperationHistory(operationHistory);
    }

    private String findNode(OperationHistoryDto operationHistory, int bucket, String key) {
        List<String> buckets = getArray(operationHistory, "buckets");

        String currentNodeAddress = buckets.get(bucket);
        operationHistory.addLocalVariable("currentNode", currentNodeAddress);

        HashMapNode currentNode = currentNodeAddress != null ? convertToHashMapNode(operationHistory.getObject(currentNodeAddress)) : null;

        while (currentNodeAddress != null && !key.equals(currentNode.getKey())) {
            currentNodeAddress = currentNode.getLinkAddress();
            currentNode = currentNodeAddress != null ? convertToHashMapNode(operationHistory.getObject(currentNodeAddress)) : null;
        }
        return currentNodeAddress;
    }
}
