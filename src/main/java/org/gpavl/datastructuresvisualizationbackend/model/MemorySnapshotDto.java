package org.gpavl.datastructuresvisualizationbackend.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class MemorySnapshotDto {

    private Map<String, Object> localVariables;
    private Map<String, Object> instanceVariables;
    private Map<String, Object> addressObjectMap;

    private Object getResult;
    private String message;

    public MemorySnapshotDto() {
        localVariables = new HashMap<>();
        instanceVariables = new HashMap<>();
        addressObjectMap = new HashMap<>();
    }

    public MemorySnapshotDto(MemorySnapshotDto other) {
        this.localVariables = new HashMap<>(other.localVariables);
        this.instanceVariables = new HashMap<>(other.instanceVariables);
        this.addressObjectMap = new HashMap<>(other.addressObjectMap);
    }

    public void updateLocalVariable(String variableName, Object variableValue) {
        localVariables.put(variableName, variableValue);
    }

    public void updateInstanceVariable(String variableName, Object variableValue) {
        instanceVariables.put(variableName, variableValue);
    }

    public void updateAddressObjectMap(String address, Object value) {
        addressObjectMap.put(address, value);
    }

    public void removeLocalVariable(String variableName) {
        localVariables.remove(variableName);
    }

    public void removeInstanceVariable(String variableName) {
        instanceVariables.remove(variableName);
    }

    public void freeAddress(String address) {
        addressObjectMap.remove(address);
    }
}
