package org.gpavl.datastructuresvisualizationbackend.model.arrayvector;

import lombok.Data;
import org.gpavl.datastructuresvisualizationbackend.model.MemoryHistoryDto;

import java.util.List;

@Data
public class ArrayVectorStateResponse {

    private int capacity;
    private int count;
    private List<String> array;
    private MemoryHistoryDto memoryHistory;
}
