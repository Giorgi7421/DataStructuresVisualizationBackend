package org.gpavl.datastructuresvisualizationbackend.util;

import lombok.AllArgsConstructor;
import org.gpavl.datastructuresvisualizationbackend.entity.DataStructureState;
import org.gpavl.datastructuresvisualizationbackend.model.MemoryHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.model.Node;
import org.gpavl.datastructuresvisualizationbackend.model.OperationHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.model.Type;
import org.gpavl.datastructuresvisualizationbackend.repository.DataStructureRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MemoryUtils {

    private DataStructureRepository dataStructureRepository;

    public static String generateNewAddress() {
        return "0x" + UUID
                .randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 8);
    }

    public static OperationHistoryDto getLastMemorySnapshot(
            String operationName,
            MemoryHistoryDto memoryHistory
    ) {
        return new OperationHistoryDto(
                operationName,
                Collections.emptyMap(),
                memoryHistory.getLastMemorySnapshot()
        );
    }

    public static OperationHistoryDto getLastMemorySnapshot(
            String operationName,
            MemoryHistoryDto memoryHistory,
            String parameterName,
            Object parameterValue
    ) {
        return new OperationHistoryDto(
                operationName,
                Map.of(parameterName, parameterValue),
                memoryHistory.getLastMemorySnapshot()
        );
    }

    public static OperationHistoryDto getLastMemorySnapshot(
            String operationName,
            MemoryHistoryDto memoryHistory,
            String firstParameterName,
            Object firstParameterValue,
            String secondParameterName,
            Object secondParameterValue
    ) {
        return new OperationHistoryDto(
                operationName,
                Map.of(firstParameterName,
                        firstParameterValue,
                        secondParameterName,
                        secondParameterValue
                ),
                memoryHistory.getLastMemorySnapshot()
        );
    }

    public DataStructureState getDataStructureState(String name, Type type) {
        Optional<DataStructureState> optionalState = dataStructureRepository.findByNameAndType(name, type);
        return optionalState.orElseThrow();
    }

    public static Node convertToNode(Map<String, Object> obj) {
        Node node = new Node();
        node.setValue((String) obj.get("value"));
        node.setNextAddress((String) obj.get("nextAddress"));
        return node;
    }
}
