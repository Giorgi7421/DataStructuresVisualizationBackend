package org.gpavl.datastructuresvisualizationbackend.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static org.gpavl.datastructuresvisualizationbackend.util.AddressUtils.generateNewAddress;

@Getter
@Setter
public class MemoryHistoryDto {

    List<MemorySnapshotDto> memorySnapshots;

    public MemoryHistoryDto() {
        memorySnapshots = new ArrayList<>();
    }

    public void addInstanceVariableToMemory(String variableName, Object variableValue) {
        MemorySnapshotDto currentMemorySnapshot = getCurrentMemorySnapshot();
        currentMemorySnapshot.updateInstanceVariable(variableName, variableValue);
        memorySnapshots.add(currentMemorySnapshot);
    }

    public void addLocalVariableToMemory(String variableName, Object variableValue) {
        MemorySnapshotDto currentMemorySnapshot = getCurrentMemorySnapshot();
        currentMemorySnapshot.updateLocalVariable(variableName, variableValue);
        memorySnapshots.add(currentMemorySnapshot);
    }

    public String addNewObjectInAddressMemoryMap(Object object) {
        String newAddress = generateNewAddress();
        MemorySnapshotDto currentMemorySnapshot = getCurrentMemorySnapshot();
        currentMemorySnapshot.updateAddressObjectMap(newAddress, object);
        memorySnapshots.add(currentMemorySnapshot);
        return newAddress;
    }

    public void updateObjectInAddressMemoryMap(String address, Object object) {
        MemorySnapshotDto currentMemorySnapshot = getCurrentMemorySnapshot();
        currentMemorySnapshot.updateAddressObjectMap(address, object);
        memorySnapshots.add(currentMemorySnapshot);
    }

    public void freeInstanceVariable(String variableName, String message) {
        MemorySnapshotDto memorySnapshotDto = getCurrentMemorySnapshot();
        memorySnapshotDto.removeLocalVariable(variableName);

        String address = getInstanceVariableAddress(variableName);

        memorySnapshotDto.freeAddress(address);
        memorySnapshotDto.setMessage(message);
        memorySnapshots.add(memorySnapshotDto);
    }

    public void freeLocalVariable(String variableName, String message) {
        MemorySnapshotDto memorySnapshotDto = getCurrentMemorySnapshot();
        memorySnapshotDto.removeLocalVariable(variableName);

        String address = getLocalVariableAddress(variableName);

        memorySnapshotDto.freeAddress(address);
        memorySnapshotDto.setMessage(message);
        memorySnapshots.add(memorySnapshotDto);
    }

    public String getInstanceVariableAddress(String variableName) {
        return (String) getCurrentMemorySnapshot().getInstanceVariables().get(variableName);
    }

    public String getLocalVariableAddress(String variableName) {
        return (String) getCurrentMemorySnapshot().getLocalVariables().get(variableName);
    }

    private MemorySnapshotDto getCurrentMemorySnapshot() {
        return !memorySnapshots.isEmpty()
                ? new MemorySnapshotDto(memorySnapshots.getLast())
                : new MemorySnapshotDto();
    }
}
