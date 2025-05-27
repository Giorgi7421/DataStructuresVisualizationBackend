package org.gpavl.datastructuresvisualizationbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.gpavl.datastructuresvisualizationbackend.model.map.HashMapNode;
import org.gpavl.datastructuresvisualizationbackend.model.map.KeyValuePair;
import org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils.generateNewAddress;

@Getter
@Setter
public class OperationHistoryDto {

    private String operationName;
    private Map<String , Object> parameters;
    private List<MemorySnapshotDto> memorySnapshots;

    public OperationHistoryDto(String operationName, Map<String ,Object> parameters) {
        memorySnapshots = new ArrayList<>();
        this.operationName = operationName;
        this.parameters = new HashMap<>(parameters);
    }

    public OperationHistoryDto(String operationName, Map<String ,Object> parameters, MemorySnapshotDto memorySnapshot) {
        memorySnapshots = new ArrayList<>();
        memorySnapshots.add(memorySnapshot);
        this.operationName = operationName;
        this.parameters = new HashMap<>(parameters);
    }

    public String getNextNodeAddress(String nodeAddress) {
        Node node = MemoryUtils.convertToNode(getObject(nodeAddress));
        return node.getNextAddress();
    }

    public void addResult(Object result) {
        MemorySnapshotDto currentMemorySnapshot = getCurrentMemorySnapshot();
        currentMemorySnapshot.setGetResult(result);
        memorySnapshots.add(currentMemorySnapshot);
    }

    public void addMessage(String message) {
        memorySnapshots.getLast().setMessage(message);
    }

    public Map<String, Object> getObject(String address) {
        MemorySnapshotDto currentMemorySnapshot = getCurrentMemorySnapshot();
        Object obj = currentMemorySnapshot.getAddressObjectMap().get(address);

        if (obj instanceof DoublyLinkedNode node) {
            Map<String, Object> result = new HashMap<>();
            result.put("value", node.getValue());
            result.put("previousAddress", node.getPreviousAddress());
            result.put("nextAddress", node.getNextAddress());
            return result;
        }else if (obj instanceof HashMapNode otherNode) {
            Map<String, Object> result = new HashMap<>();
            result.put("key", otherNode.getKey());
            result.put("value", otherNode.getValue());
            result.put("linkAddress", otherNode.getLinkAddress());
            return result;
        }

        return (Map<String, Object>) obj;
    }

    public List<String> getArray(String address) {
        MemorySnapshotDto currentMemorySnapshot = getCurrentMemorySnapshot();
        return (List<String>) currentMemorySnapshot.getAddressObjectMap().get(address);
    }

    public List<KeyValuePair> getKeyValuePairArray(String address) {
        MemorySnapshotDto currentMemorySnapshot = getCurrentMemorySnapshot();
        List<Object> objects = (List<Object>) currentMemorySnapshot.getAddressObjectMap().get(address);
        return objects.stream().map(obj -> {
            if (obj == null) {
                return null;
            }

            if (obj instanceof KeyValuePair keyValuePair) {
                return keyValuePair;
            }else {
                Map<String, Object> casted = (Map<String, Object>) obj;

                KeyValuePair keyValuePair = new KeyValuePair();
                keyValuePair.setKey((String) casted.get("key"));
                keyValuePair.setValue((String) casted.get("value"));
                return keyValuePair;
            }
        }).toList();
    }

    public List<Integer> getIntArray(String address) {
        MemorySnapshotDto currentMemorySnapshot = getCurrentMemorySnapshot();
        return (List<Integer>) currentMemorySnapshot.getAddressObjectMap().get(address);
    }

    public void addInstanceVariable(String variableName, Object variableValue) {
        MemorySnapshotDto currentMemorySnapshot = getCurrentMemorySnapshot();
        boolean equals = currentMemorySnapshot.updateInstanceVariable(variableName, variableValue);
        if (!equals) {
            memorySnapshots.add(currentMemorySnapshot);
        }
    }

    public void addLocalVariable(String variableName, Object variableValue) {
        MemorySnapshotDto currentMemorySnapshot = getCurrentMemorySnapshot();
        boolean equals = currentMemorySnapshot.updateLocalVariable(variableName, variableValue);
        if (!equals) {
            memorySnapshots.add(currentMemorySnapshot);
        }
    }

    public String addNewObject(Object object) {
        String newAddress = generateNewAddress();
        MemorySnapshotDto currentMemorySnapshot = getCurrentMemorySnapshot();
        currentMemorySnapshot.updateAddressObjectMap(newAddress, object);
        memorySnapshots.add(currentMemorySnapshot);
        return newAddress;
    }

    public void updateObject(String address, Object object) {
        MemorySnapshotDto currentMemorySnapshot = getCurrentMemorySnapshot();
        boolean equals = currentMemorySnapshot.updateAddressObjectMap(address, object);
        if (!equals) {
            memorySnapshots.add(currentMemorySnapshot);
        }
    }

    public void updateObjectInPlace(String address, Object object) {
        MemorySnapshotDto currentMemorySnapshot = memorySnapshots.getLast();
        currentMemorySnapshot.updateAddressObjectMap(address, object);
    }

    public void removeLocalVariable(String variableName) {
        MemorySnapshotDto memorySnapshotDto = getCurrentMemorySnapshot();
        boolean removed = memorySnapshotDto.removeLocalVariable(variableName);

        if (!removed) {
            return;
        }

        memorySnapshots.add(memorySnapshotDto);
    }

    public void freeObject(String address, String message) {
        MemorySnapshotDto memorySnapshotDto = getCurrentMemorySnapshot();
        memorySnapshotDto.freeAddress(address);
        memorySnapshotDto.setMessage(message);
        memorySnapshots.add(memorySnapshotDto);
    }

    public void freeInstanceVariable(String variableName, String message) {
        String address = (String) getInstanceVariableValue(variableName);
        freeObject(address, message);
    }

    public void freeLocalVariable(String variableName, String message) {
        String address = (String) getLocalVariableValue(variableName);
        freeObject(address, message);
        removeLocalVariable(variableName);
    }

    public Object getInstanceVariableValue(String variableName) {
        return getCurrentMemorySnapshot().getInstanceVariables().get(variableName);
    }

    public Object getLocalVariableValue(String variableName) {
        return getCurrentMemorySnapshot().getLocalVariables().get(variableName);
    }

    @JsonIgnore
    public MemorySnapshotDto getCurrentMemorySnapshot() {
        return !memorySnapshots.isEmpty()
                ? new MemorySnapshotDto(memorySnapshots.getLast())
                : new MemorySnapshotDto();
    }
}
