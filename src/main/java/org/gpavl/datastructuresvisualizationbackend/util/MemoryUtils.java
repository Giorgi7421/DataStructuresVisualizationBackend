package org.gpavl.datastructuresvisualizationbackend.util;

import org.gpavl.datastructuresvisualizationbackend.model.MemoryHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.model.OperationHistoryDto;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class MemoryUtils {

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
}
