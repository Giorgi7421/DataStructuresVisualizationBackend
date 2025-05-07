package org.gpavl.datastructuresvisualizationbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
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
        return (Map<String, Object>) currentMemorySnapshot.getAddressObjectMap().get(address);
    }

    public List<String> getArray(String address) {
        MemorySnapshotDto currentMemorySnapshot = getCurrentMemorySnapshot();
        return (List<String>) currentMemorySnapshot.getAddressObjectMap().get(address);
    }

    public List<Integer> getIntArray(String address) {
        MemorySnapshotDto currentMemorySnapshot = getCurrentMemorySnapshot();
        return (List<Integer>) currentMemorySnapshot.getAddressObjectMap().get(address);
    }

    public void addInstanceVariable(String variableName, Object variableValue) {
        MemorySnapshotDto currentMemorySnapshot = getCurrentMemorySnapshot();
        currentMemorySnapshot.updateInstanceVariable(variableName, variableValue);
        memorySnapshots.add(currentMemorySnapshot);
    }

    public void addLocalVariable(String variableName, Object variableValue) {
        MemorySnapshotDto currentMemorySnapshot = getCurrentMemorySnapshot();
        currentMemorySnapshot.updateLocalVariable(variableName, variableValue);
        memorySnapshots.add(currentMemorySnapshot);
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
        currentMemorySnapshot.updateAddressObjectMap(address, object);
        memorySnapshots.add(currentMemorySnapshot);
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
