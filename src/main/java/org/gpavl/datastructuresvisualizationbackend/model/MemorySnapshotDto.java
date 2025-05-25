package org.gpavl.datastructuresvisualizationbackend.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    public boolean updateLocalVariable(String variableName, Object variableValue) {
        Object obj = localVariables.put(variableName, variableValue);
        return Objects.equals(obj, variableValue);
    }

    public boolean updateInstanceVariable(String variableName, Object variableValue) {
        Object obj = instanceVariables.put(variableName, variableValue);
        return Objects.equals(obj, variableValue);
    }

    public boolean updateAddressObjectMap(String address, Object value) {
        Object obj = addressObjectMap.put(address, value);
        return Objects.equals(obj, value);
    }

    public boolean removeLocalVariable(String variableName) {
        if (!localVariables.containsKey(variableName)) {
            return false;
        }
        localVariables.remove(variableName);
        return true;
    }

    public void removeInstanceVariable(String variableName) {
        instanceVariables.remove(variableName);
    }

    public void freeAddress(String address) {
        addressObjectMap.remove(address);
    }
}
