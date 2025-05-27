package org.gpavl.datastructuresvisualizationbackend.model.map;


import org.gpavl.datastructuresvisualizationbackend.model.MemoryHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.model.OperationHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils.*;

public class ArrayMap extends Map {

    private static final int INITIAL_CAPACITY = 10;

    public ArrayMap() {
        memoryHistory = new MemoryHistoryDto();
        OperationHistoryDto operationHistory = new OperationHistoryDto("ArrayMap", Collections.emptyMap());

        operationHistory.addInstanceVariable("capacity", INITIAL_CAPACITY);
        operationHistory.addInstanceVariable("count", 0);

        List<KeyValuePair> array = Collections.nCopies(INITIAL_CAPACITY, null);
        String newAddress = operationHistory.addNewObject(array);
        operationHistory.addInstanceVariable("array", newAddress);

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
        operationHistory.addInstanceVariable("count", 0);
        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void put(String key, String value) {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("put", memoryHistory, "key", key, "value", value);

        operationHistory.addLocalVariable("key", key);
        operationHistory.addLocalVariable("value", value);

        int index = findKey(operationHistory, key);
        operationHistory.addLocalVariable("index", index);
        operationHistory.removeLocalVariable("i");

        int count = getCount(operationHistory);
        int capacity = getCapacity(operationHistory);

        if  (index == -1) {
            if (count == capacity) {
                extendCapacity(operationHistory);
            }
            index = count;
            operationHistory.addLocalVariable("index", index);

            count++;
            operationHistory.addInstanceVariable("count", count);

            List<KeyValuePair> array = getKeyValuePairArray(operationHistory, "array");
            array = new ArrayList<>(array);

            KeyValuePair newKeyValuePair = new KeyValuePair();
            newKeyValuePair.setKey(key);

            array.set(index, newKeyValuePair);
            updateArray(array, operationHistory, "array");
        }

        List<KeyValuePair> array1 = getKeyValuePairArray(operationHistory, "array");
        array1 = new ArrayList<>(array1);

        KeyValuePair newKeyValuePair = new KeyValuePair();
        newKeyValuePair.setKey(array1.get(index).getKey());
        newKeyValuePair.setValue(value);

        array1.set(index, newKeyValuePair);
        updateArray(array1, operationHistory, "array");

        operationHistory.removeLocalVariable("index");
        operationHistory.removeLocalVariable("value");
        operationHistory.removeLocalVariable("key");

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void get(String key) {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("get", memoryHistory, "key", key);

        operationHistory.addLocalVariable("key", key);

        int index = findKey(operationHistory, key);
        operationHistory.addLocalVariable("index", index);
        operationHistory.removeLocalVariable("i");

        if (index == -1) {
            throw new IllegalArgumentException("Key not found");
        }

        List<KeyValuePair> array = getKeyValuePairArray(operationHistory, "array");
        operationHistory.addResult(array.get(index).getValue());

        operationHistory.removeLocalVariable("index");
        operationHistory.removeLocalVariable("key");

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void containsKey(String key) {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("containsKey", memoryHistory, "key", key);

        operationHistory.addLocalVariable("key", key);

        operationHistory.addResult(findKey(operationHistory, key) != -1);
        operationHistory.removeLocalVariable("i");

        operationHistory.removeLocalVariable("key");

        memoryHistory.addOperationHistory(operationHistory);
    }

    private int findKey(OperationHistoryDto operationHistory, String key) {
        int count = getCount(operationHistory);
        List<KeyValuePair> keyValuePairs = getKeyValuePairArray(operationHistory, "array");

        for (int i = 0; i < count; i++) {
            operationHistory.addLocalVariable("i", i);
            KeyValuePair keyValuePair = keyValuePairs.get(i);

            if (keyValuePair.getKey().equals(key)) {
                operationHistory.removeLocalVariable("i");
                return i;
            }
        }
        return -1;
    }

    public static void extendCapacity(OperationHistoryDto operationHistory) {
        List<KeyValuePair> oldArray = getKeyValuePairArray(operationHistory, "array");
        String arrayAddress = (String) operationHistory.getInstanceVariableValue("array");
        operationHistory.addLocalVariable("oldArray", arrayAddress);

        int capacity = getCapacity(operationHistory);
        capacity *= 2;
        operationHistory.addInstanceVariable("capacity", capacity);

        List<KeyValuePair> array = Collections.nCopies(capacity, null);
        String newAddress = operationHistory.addNewObject(array);
        operationHistory.addInstanceVariable("array", newAddress);

        int count = getCount(operationHistory);

        for (int i = 0; i < count; i++) {
            array = new ArrayList<>(array);
            array.set(i, oldArray.get(i));
            updateArray(array, operationHistory, "array");
            if (i == count - 1) {
                operationHistory.addMessage("Extending the capacity is completed");
            }
        }

        operationHistory.freeLocalVariable("oldArray", "Deleted old array to avoid memory leaks");
    }
}
