package org.gpavl.datastructuresvisualizationbackend.util;

import org.gpavl.datastructuresvisualizationbackend.entity.DataStructureState;
import org.gpavl.datastructuresvisualizationbackend.entity.MemoryHistory;
import org.gpavl.datastructuresvisualizationbackend.entity.MemorySnapshot;
import org.gpavl.datastructuresvisualizationbackend.entity.OperationHistory;
import org.gpavl.datastructuresvisualizationbackend.model.*;

import java.util.HashMap;
import java.util.List;
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
                        .toList()
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
                .toList();

        memoryHistory.setOperationHistoryList(operationHistoryList);

        dataStructureState.setMemoryHistory(memoryHistory);

        return dataStructureState;
    }

    public static OperationHistory convertToOperationHistory(OperationHistoryDto operationHistoryDto) {
        OperationHistory operationHistory = new OperationHistory();
        operationHistory.setOperationName(operationHistoryDto.getOperationName());
        operationHistory.setParameters(operationHistoryDto.getParameters());
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
                        .toList()
        );

        return operationHistoryDto;
    }

    public static MemorySnapshot convertToMemorySnapshot(MemorySnapshotDto memorySnapshotDto) {
        MemorySnapshot memorySnapshot = new MemorySnapshot();
        memorySnapshot.setLocalVariables(new HashMap<>(memorySnapshotDto.getLocalVariables()));
        memorySnapshot.setInstanceVariables(new HashMap<>(memorySnapshotDto.getInstanceVariables()));
        memorySnapshot.setAddressObjectMap(new HashMap<>(memorySnapshotDto.getAddressObjectMap()));
        memorySnapshot.setMessage(memorySnapshotDto.getMessage());
        return memorySnapshot;
    }

    public static MemorySnapshotDto convertToMemorySnapshotDto(MemorySnapshot memorySnapshot) {
        MemorySnapshotDto memorySnapshotdto = new MemorySnapshotDto();
        memorySnapshotdto.setLocalVariables(new HashMap<>(memorySnapshot.getLocalVariables()));
        memorySnapshotdto.setInstanceVariables(new HashMap<>(memorySnapshot.getInstanceVariables()));
        memorySnapshotdto.setAddressObjectMap(new HashMap<>(memorySnapshot.getAddressObjectMap()));
        memorySnapshotdto.setMessage(memorySnapshot.getMessage());
        return memorySnapshotdto;
    }
}
