package org.gpavl.datastructuresvisualizationbackend.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MemoryHistoryDto {

    List<OperationHistoryDto> operationHistoryList;

    public MemoryHistoryDto() {
        operationHistoryList = new ArrayList<>();
    }

    public void addOperationHistory(OperationHistoryDto operationHistory) {
        operationHistoryList.add(operationHistory);
    }

    public OperationHistoryDto getLastOperationHistory() {
        return operationHistoryList.getLast();
    }

    public MemorySnapshotDto getLastMemorySnapshot() {
        return getLastOperationHistory().getCurrentMemorySnapshot();
    }
}
