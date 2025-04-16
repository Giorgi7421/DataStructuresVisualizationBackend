package org.gpavl.datastructuresvisualizationbackend.service;

import org.gpavl.datastructuresvisualizationbackend.entity.DataStructureState;
import org.gpavl.datastructuresvisualizationbackend.model.MemoryHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.model.Response;
import org.gpavl.datastructuresvisualizationbackend.model.Type;
import org.gpavl.datastructuresvisualizationbackend.model.priorityqueue.*;
import org.gpavl.datastructuresvisualizationbackend.repository.DataStructureRepository;
import org.gpavl.datastructuresvisualizationbackend.util.Converter;
import org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils;
import org.gpavl.datastructuresvisualizationbackend.util.OperationUtils;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class PriorityQueueService extends DataStructureService {

    private final OperationUtils operationUtils;
    private final Map<Type, Supplier<PriorityQueue>> typeMap;

    public PriorityQueueService(
            DataStructureRepository dataStructureRepository,
            MemoryUtils memoryUtils,
            OperationUtils operationUtils
    ) {
        super(dataStructureRepository, memoryUtils);
        this.operationUtils = operationUtils;

        typeMap = Map.of(
                Type.UNSORTED_VECTOR_PRIORITY_QUEUE, UnsortedVectorPriorityQueue::new,
                Type.SORTED_LINKED_LIST_PRIORITY_QUEUE, SortedLinkedListPriorityQueue::new,
                Type.UNSORTED_DOUBLY_LINKED_LIST_PRIORITY_QUEUE, UnsortedDoublyLinkedListPriorityQueue::new,
                Type.BINARY_HEAP_PRIORITY_QUEUE, BinaryHeapPriorityQueue::new
        );
    }

    public Response size(Type type, String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, type);
        PriorityQueue priorityQueue = convertToPriorityQueue(state, type);
        return operationUtils.executeNoArgumentOperation(
                state,
                priorityQueue,
                PriorityQueue::size
        );
    }

    public Response isEmpty(Type type, String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, type);
        PriorityQueue priorityQueue = convertToPriorityQueue(state, type);
        return operationUtils.executeNoArgumentOperation(
                state,
                priorityQueue,
                PriorityQueue::isEmpty
        );
    }

    public Response enqueue(Type type, String name, String element, int priority) {
        DataStructureState state = memoryUtils.getDataStructureState(name, type);
        PriorityQueue priorityQueue = convertToPriorityQueue(state, type);
        return operationUtils.executeTwoArgumentOperation(
                state,
                priorityQueue,
                PriorityQueue::enqueue,
                element,
                priority
        );
    }

    public Response dequeue(Type type, String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, type);
        PriorityQueue priorityQueue = convertToPriorityQueue(state, type);
        return operationUtils.executeNoArgumentOperation(
                state,
                priorityQueue,
                PriorityQueue::dequeue
        );
    }

    public Response peek(Type type, String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, type);
        PriorityQueue priorityQueue = convertToPriorityQueue(state, type);
        return operationUtils.executeNoArgumentOperation(
                state,
                priorityQueue,
                PriorityQueue::peek
        );
    }

    private PriorityQueue convertToPriorityQueue(DataStructureState state, Type type) {
        PriorityQueue priorityQueue = typeMap.get(type).get();
        MemoryHistoryDto memoryHistoryDto = new MemoryHistoryDto();
        memoryHistoryDto.setOperationHistoryList(
                state.getMemoryHistory().getOperationHistoryList().stream().map(Converter::convertToOperationHistoryDto).collect(Collectors.toList())
        );
        priorityQueue.setMemoryHistory(memoryHistoryDto);

        return priorityQueue;
    }
}
