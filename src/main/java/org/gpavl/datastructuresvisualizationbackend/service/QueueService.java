package org.gpavl.datastructuresvisualizationbackend.service;

import org.gpavl.datastructuresvisualizationbackend.entity.DataStructureState;
import org.gpavl.datastructuresvisualizationbackend.model.MemoryHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.model.Response;
import org.gpavl.datastructuresvisualizationbackend.model.Type;
import org.gpavl.datastructuresvisualizationbackend.model.queue.*;
import org.gpavl.datastructuresvisualizationbackend.repository.DataStructureRepository;
import org.gpavl.datastructuresvisualizationbackend.util.Converter;
import org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils;
import org.gpavl.datastructuresvisualizationbackend.util.OperationUtils;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class QueueService extends DataStructureService {

    private final OperationUtils operationUtils;
    private final Map<Type, Supplier<Queue>> typeMap;

    public QueueService(
            DataStructureRepository dataStructureRepository,
            MemoryUtils memoryUtils,
            OperationUtils operationUtils
    ) {
        super(dataStructureRepository, memoryUtils);
        this.operationUtils = operationUtils;

        typeMap = Map.of(
                Type.ARRAY_QUEUE, ArrayQueue::new,
                Type.LINKED_LIST_QUEUE, LinkedListQueue::new,
                Type.UNSORTED_VECTOR_PRIORITY_QUEUE, UnsortedVectorPriorityQueue::new,
                Type.SORTED_LINKED_LIST_PRIORITY_QUEUE, SortedLinkedListPriorityQueue::new,
                Type.UNSORTED_DOUBLY_LINKED_LIST_PRIORITY_QUEUE, UnsortedDoublyLinkedListPriorityQueue::new,
                Type.BINARY_HEAP_PRIORITY_QUEUE, BinaryHeapPriorityQueue::new
        );
    }

    public Response size(Type type, String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, type);
        Queue queue = convertToQueue(state, type);
        return operationUtils.executeNoArgumentOperation(
                state,
                queue,
                Queue::size
        );
    }

    public Response isEmpty(Type type, String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, type);
        Queue queue = convertToQueue(state, type);
        return operationUtils.executeNoArgumentOperation(
                state,
                queue,
                Queue::isEmpty
        );
    }

    public Response clear(Type type, String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, type);
        Queue queue = convertToQueue(state, type);
        return operationUtils.executeNoArgumentOperation(
                state,
                queue,
                Queue::clear
        );
    }

    public Response enqueue(Type type, String name, String element) {
        DataStructureState state = memoryUtils.getDataStructureState(name, type);
        Queue queue = convertToQueue(state, type);
        return operationUtils.executeOneArgumentOperation(
                state,
                queue,
                Queue::enqueue,
                element
        );
    }

    public Response dequeue(Type type, String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, type);
        Queue queue = convertToQueue(state, type);
        return operationUtils.executeNoArgumentOperation(
                state,
                queue,
                Queue::dequeue
        );
    }

    public Response peek(Type type, String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, type);
        Queue queue = convertToQueue(state, type);
        return operationUtils.executeNoArgumentOperation(
                state,
                queue,
                Queue::peek
        );
    }

    private Queue convertToQueue(DataStructureState state, Type type) {
        Queue queue = typeMap.get(type).get();
        MemoryHistoryDto memoryHistoryDto = new MemoryHistoryDto();
        memoryHistoryDto.setOperationHistoryList(
                state.getMemoryHistory().getOperationHistoryList().stream().map(Converter::convertToOperationHistoryDto).collect(Collectors.toList())
        );
        queue.setMemoryHistory(memoryHistoryDto);

        return queue;
    }
}
