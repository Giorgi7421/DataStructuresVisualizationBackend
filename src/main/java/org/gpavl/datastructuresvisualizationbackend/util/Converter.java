package org.gpavl.datastructuresvisualizationbackend.util;

import org.gpavl.datastructuresvisualizationbackend.entity.*;
import org.gpavl.datastructuresvisualizationbackend.model.*;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Converter {

    public static Response convertToResponse(DataStructureState state) {
        Response response = new Response();
        response.setDataStructureName(state.getName());

        MemoryHistoryDto memoryHistoryDto = new MemoryHistoryDto();
        memoryHistoryDto.setOperationHistoryList(
                state
                        .getMemoryHistory()
                        .getOperationHistoryList()
                        .stream()
                        .map(Converter::convertToOperationHistoryDto)
                        .collect(Collectors.toList())
        );

        response.setMemoryHistory(memoryHistoryDto);
        return response;
    }

    public static DataStructureState convertToDataStructureState(DataStructure dataStructure, String name, Type type) {
        DataStructureState dataStructureState = new DataStructureState();
        dataStructureState.setName(name);
        dataStructureState.setType(type);

        MemoryHistory memoryHistory = new MemoryHistory();
        List<OperationHistory> operationHistoryList = dataStructure
                .getMemoryHistory()
                .getOperationHistoryList()
                .stream()
                .map(Converter::convertToOperationHistory)
                .collect(Collectors.toList());

        memoryHistory.setOperationHistoryList(operationHistoryList);

        dataStructureState.setMemoryHistory(memoryHistory);

        return dataStructureState;
    }

    public static OperationHistory convertToOperationHistory(OperationHistoryDto operationHistoryDto) {
        OperationHistory operationHistory = new OperationHistory();
        operationHistory.setOperationName(operationHistoryDto.getOperationName());
        operationHistory.setParameters(new HashMap<>(operationHistoryDto.getParameters()));
        operationHistory.setMemorySnapshots(
                operationHistoryDto.getMemorySnapshots().stream().map(Converter::convertToMemorySnapshot).collect(Collectors.toList())
        );
        return operationHistory;
    }

    public static OperationHistoryDto convertToOperationHistoryDto(OperationHistory operationHistory) {
        OperationHistoryDto operationHistoryDto =  new OperationHistoryDto(
                operationHistory.getOperationName(),
                operationHistory.getParameters()
        );

        operationHistoryDto.setMemorySnapshots(
                operationHistory
                .getMemorySnapshots()
                        .stream()
                        .map(Converter::convertToMemorySnapshotDto)
                        .collect(Collectors.toList()));

        return operationHistoryDto;
    }

    public static MemorySnapshot convertToMemorySnapshot(MemorySnapshotDto memorySnapshotDto) {
        MemorySnapshot memorySnapshot = new MemorySnapshot();
        Map<String, Object> locals = new HashMap<>(memorySnapshotDto.getLocalVariables());

        Map<String, Object> modifiedLocals = locals.keySet().stream().collect(Collectors.toMap(Function.identity(), k -> {
            Object value = locals.get(k);
            return value != null ? value : "__NULL__";
        }));

        Map<String, Object> instances = new HashMap<>(memorySnapshotDto.getInstanceVariables());

        Map<String, Object> modifiedInstances = instances.keySet().stream().collect(Collectors.toMap(Function.identity(), k -> {
            Object value = instances.get(k);
            return value != null ? value : "__NULL__";
        }));

        memorySnapshot.setLocalVariables(modifiedLocals);
        memorySnapshot.setInstanceVariables(modifiedInstances);
        memorySnapshot.setAddressObjectMap(new HashMap<>(memorySnapshotDto.getAddressObjectMap()));
        memorySnapshot.setMessage(memorySnapshotDto.getMessage());
        memorySnapshot.setGetResult(memorySnapshotDto.getGetResult());
        return memorySnapshot;
    }

    public static MemorySnapshotDto convertToMemorySnapshotDto(MemorySnapshot memorySnapshot) {
        MemorySnapshotDto memorySnapshotdto = new MemorySnapshotDto();

        Map<String, Object> locals = new HashMap<>(memorySnapshot.getLocalVariables());
        Set<String> localKeys = locals.keySet();
        for (String key : localKeys) {
            if (locals.get(key).equals("__NULL__")) {
                locals.put(key, null);
            }
        }


        Map<String, Object> instances = new HashMap<>(memorySnapshot.getInstanceVariables());
        Set<String> instanceKeys = instances.keySet();
        for (String key : instanceKeys) {
            if (instances.get(key).equals("__NULL__")) {
                instances.put(key, null);
            }
        }

        memorySnapshotdto.setLocalVariables(locals);
        memorySnapshotdto.setInstanceVariables(instances);

        memorySnapshotdto.setAddressObjectMap(new HashMap<>(memorySnapshot.getAddressObjectMap()));
        memorySnapshotdto.setMessage(memorySnapshot.getMessage());
        memorySnapshotdto.setGetResult(memorySnapshot.getGetResult());
        return memorySnapshotdto;
    }
}
