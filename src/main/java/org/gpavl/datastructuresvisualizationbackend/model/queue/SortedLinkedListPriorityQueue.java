package org.gpavl.datastructuresvisualizationbackend.model.queue;

import org.gpavl.datastructuresvisualizationbackend.model.MemoryHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.model.OperationHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils;

import java.util.Collections;

public class SortedLinkedListPriorityQueue extends Queue {

    public SortedLinkedListPriorityQueue() {
        memoryHistory = new MemoryHistoryDto();
        OperationHistoryDto operationHistory = new OperationHistoryDto("SortedLinkedListPriorityQueue", Collections.emptyMap());

        operationHistory.addInstanceVariable("head", null);
        operationHistory.addInstanceVariable("count", 0);

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void size() {
        MemoryUtils.size(memoryHistory);
    }

    @Override
    public void isEmpty() {
        MemoryUtils.isEmpty(memoryHistory);
    }

    @Override
    public void clear() {

    }

    @Override
    public void enqueue(String element) {

    }

    @Override
    public void dequeue() {

    }

    @Override
    public void peek() {

    }
}
