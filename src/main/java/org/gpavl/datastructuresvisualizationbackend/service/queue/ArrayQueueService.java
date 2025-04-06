package org.gpavl.datastructuresvisualizationbackend.service.queue;

import lombok.AllArgsConstructor;
import org.gpavl.datastructuresvisualizationbackend.entity.DataStructureState;
import org.gpavl.datastructuresvisualizationbackend.model.MemoryHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.model.Response;
import org.gpavl.datastructuresvisualizationbackend.model.Type;
import org.gpavl.datastructuresvisualizationbackend.model.queue.ArrayQueue;
import org.gpavl.datastructuresvisualizationbackend.model.stack.ArrayStack;
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
public class ArrayQueueService {

    private DataStructureRepository dataStructureRepository;
    private OperationUtils operationUtils;
    private MemoryUtils memoryUtils;

    public Response create(String name) {
        ArrayQueue arrayQueue = new ArrayQueue();
        DataStructureState state = Converter.convertToDataStructureState(arrayQueue, name, Type.ARRAY_QUEUE);

        DataStructureState result;
        try {
            result = dataStructureRepository.save(state);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateKeyException("Data structure with that name already exists");
        }

        return Converter.convertToResponse(result);
    }

    public Response findByName(String name) {
        return Converter.convertToResponse(memoryUtils.getDataStructureState(name, Type.ARRAY_QUEUE));
    }

    public Response size(String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.ARRAY_QUEUE);
        ArrayQueue arrayQueue = convertToArrayQueue(state);
        return operationUtils.executeNoArgumentOperation(
                state,
                arrayQueue,
                ArrayQueue::size
        );
    }

    public Response isEmpty(String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.ARRAY_QUEUE);
        ArrayQueue arrayQueue = convertToArrayQueue(state);
        return operationUtils.executeNoArgumentOperation(
                state,
                arrayQueue,
                ArrayQueue::isEmpty
        );
    }

    public Response clear(String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.ARRAY_QUEUE);
        ArrayQueue arrayQueue = convertToArrayQueue(state);
        return operationUtils.executeNoArgumentOperation(
                state,
                arrayQueue,
                ArrayQueue::clear
        );
    }

    public Response enqueue(String name, String element) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.ARRAY_QUEUE);
        ArrayQueue arrayQueue = convertToArrayQueue(state);
        return operationUtils.executeOneArgumentOperation(
                state,
                arrayQueue,
                ArrayQueue::enqueue,
                element
        );
    }

    public Response dequeue(String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.ARRAY_QUEUE);
        ArrayQueue arrayQueue = convertToArrayQueue(state);
        return operationUtils.executeNoArgumentOperation(
                state,
                arrayQueue,
                ArrayQueue::dequeue
        );
    }

    public Response peek(String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.ARRAY_QUEUE);
        ArrayQueue arrayQueue = convertToArrayQueue(state);
        return operationUtils.executeNoArgumentOperation(
                state,
                arrayQueue,
                ArrayQueue::peek
        );
    }

    private ArrayQueue convertToArrayQueue(DataStructureState state) {
        ArrayQueue arrayQueue = new ArrayQueue();
        MemoryHistoryDto memoryHistoryDto = new MemoryHistoryDto();
        memoryHistoryDto.setOperationHistoryList(
                state.getMemoryHistory().getOperationHistoryList().stream().map(Converter::convertToOperationHistoryDto).collect(Collectors.toList())
        );
        arrayQueue.setMemoryHistory(memoryHistoryDto);

        return arrayQueue;
    }
}
