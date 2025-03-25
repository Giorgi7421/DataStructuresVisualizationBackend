package org.gpavl.datastructuresvisualizationbackend.model;

import lombok.Data;

@Data
public class Response {
    private String dataStructureName;
    private MemoryHistoryDto memoryHistory;
}
