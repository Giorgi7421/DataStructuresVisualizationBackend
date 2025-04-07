package org.gpavl.datastructuresvisualizationbackend.service.queue;

import lombok.AllArgsConstructor;
import org.gpavl.datastructuresvisualizationbackend.entity.DataStructureState;
import org.gpavl.datastructuresvisualizationbackend.model.MemoryHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.model.Response;
import org.gpavl.datastructuresvisualizationbackend.model.Type;
import org.gpavl.datastructuresvisualizationbackend.model.queue.LinkedListQueue;
import org.gpavl.datastructuresvisualizationbackend.repository.DataStructureRepository;
import org.gpavl.datastructuresvisualizationbackend.util.Converter;
import org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils;
import org.gpavl.datastructuresvisualizationbackend.util.OperationUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LinkedListQueueService {

    private DataStructureRepository dataStructureRepository;
    private OperationUtils operationUtils;
    private MemoryUtils memoryUtils;

    public Response create(String name) {
        LinkedListQueue linkedListQueue = new LinkedListQueue();
        DataStructureState state = Converter.convertToDataStructureState(linkedListQueue, name, Type.LINKED_LIST_QUEUE);

        DataStructureState result;
        try {
            result = dataStructureRepository.save(state);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateKeyException("Data structure with that name already exists");
        }

        return Converter.convertToResponse(result);
    }

    public Response findByName(String name) {
        return Converter.convertToResponse(memoryUtils.getDataStructureState(name, Type.LINKED_LIST_QUEUE));
    }

    public Response size(String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.ARRAY_QUEUE);
        LinkedListQueue linkedListQueue = convertToLinkedListQueue(state);
        return operationUtils.executeNoArgumentOperation(
                state,
                linkedListQueue,
                LinkedListQueue::size
        );
    }

    public Response isEmpty(String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.ARRAY_QUEUE);
        LinkedListQueue linkedListQueue = convertToLinkedListQueue(state);
        return operationUtils.executeNoArgumentOperation(
                state,
                linkedListQueue,
                LinkedListQueue::isEmpty
        );
    }

    public Response clear(String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.ARRAY_QUEUE);
        LinkedListQueue linkedListQueue = convertToLinkedListQueue(state);
        return operationUtils.executeNoArgumentOperation(
                state,
                linkedListQueue,
                LinkedListQueue::clear
        );
    }

    public Response enqueue(String name, String element) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.ARRAY_QUEUE);
        LinkedListQueue linkedListQueue = convertToLinkedListQueue(state);
        return operationUtils.executeOneArgumentOperation(
                state,
                linkedListQueue,
                LinkedListQueue::enqueue,
                element
        );
    }

    public Response dequeue(String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.ARRAY_QUEUE);
        LinkedListQueue linkedListQueue = convertToLinkedListQueue(state);
        return operationUtils.executeNoArgumentOperation(
                state,
                linkedListQueue,
                LinkedListQueue::dequeue
        );
    }

    public Response peek(String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.ARRAY_QUEUE);
        LinkedListQueue linkedListQueue = convertToLinkedListQueue(state);
        return operationUtils.executeNoArgumentOperation(
                state,
                linkedListQueue,
                LinkedListQueue::peek
        );
    }

    private LinkedListQueue convertToLinkedListQueue(DataStructureState state) {
        LinkedListQueue linkedListQueue = new LinkedListQueue();
        MemoryHistoryDto memoryHistoryDto = new MemoryHistoryDto();
        memoryHistoryDto.setOperationHistoryList(
                state.getMemoryHistory().getOperationHistoryList().stream().map(Converter::convertToOperationHistoryDto).collect(Collectors.toList())
        );
        linkedListQueue.setMemoryHistory(memoryHistoryDto);

        return linkedListQueue;
    }
}
