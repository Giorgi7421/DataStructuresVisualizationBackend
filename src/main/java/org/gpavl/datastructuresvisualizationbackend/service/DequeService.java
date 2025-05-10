package org.gpavl.datastructuresvisualizationbackend.service;

import org.gpavl.datastructuresvisualizationbackend.entity.DataStructureState;
import org.gpavl.datastructuresvisualizationbackend.model.Deque;
import org.gpavl.datastructuresvisualizationbackend.model.MemoryHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.model.Response;
import org.gpavl.datastructuresvisualizationbackend.model.Type;
import org.gpavl.datastructuresvisualizationbackend.repository.DataStructureRepository;
import org.gpavl.datastructuresvisualizationbackend.security.UserService;
import org.gpavl.datastructuresvisualizationbackend.util.Converter;
import org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils;
import org.gpavl.datastructuresvisualizationbackend.util.OperationUtils;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class DequeService extends DataStructureService {

    private final OperationUtils operationUtils;

    public DequeService(
            DataStructureRepository dataStructureRepository,
            MemoryUtils memoryUtils,
            UserService userService,
            OperationUtils operationUtils
    ) {
        super(dataStructureRepository, memoryUtils, userService);
        this.operationUtils = operationUtils;
    }

    public Response pushBack(String name, String element) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.DEQUE);
        Deque deque = convertToDeque(state);
        return operationUtils.executeOneArgumentOperation(
                state,
                deque,
                Deque::pushBack,
                element
        );
    }

    public Response popBack(String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.DEQUE);
        Deque deque = convertToDeque(state);
        return operationUtils.executeNoArgumentOperation(
                state,
                deque,
                Deque::popBack
        );
    }

    public Response getBack(String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.DEQUE);
        Deque deque = convertToDeque(state);
        return operationUtils.executeNoArgumentOperation(
                state,
                deque,
                Deque::getBack
        );
    }

    public Response pushFront(String name, String element) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.DEQUE);
        Deque deque = convertToDeque(state);
        return operationUtils.executeOneArgumentOperation(
                state,
                deque,
                Deque::pushFront,
                element
        );
    }

    public Response popFront(String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.DEQUE);
        Deque deque = convertToDeque(state);
        return operationUtils.executeNoArgumentOperation(
                state,
                deque,
                Deque::popFront
        );
    }

    public Response getFront(String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.DEQUE);
        Deque deque = convertToDeque(state);
        return operationUtils.executeNoArgumentOperation(
                state,
                deque,
                Deque::getFront
        );
    }

    public Response clear(String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.DEQUE);
        Deque deque = convertToDeque(state);
        return operationUtils.executeNoArgumentOperation(
                state,
                deque,
                Deque::clear
        );
    }

    public Response size(String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.DEQUE);
        Deque deque = convertToDeque(state);
        return operationUtils.executeNoArgumentOperation(
                state,
                deque,
                Deque::size
        );
    }

    public Response isEmpty(String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.DEQUE);
        Deque deque = convertToDeque(state);
        return operationUtils.executeNoArgumentOperation(
                state,
                deque,
                Deque::isEmpty
        );
    }

    private Deque convertToDeque(DataStructureState state) {
        Deque deque = new Deque();
        MemoryHistoryDto memoryHistoryDto = new MemoryHistoryDto();
        memoryHistoryDto.setOperationHistoryList(
                state.getMemoryHistory().getOperationHistoryList().stream().map(Converter::convertToOperationHistoryDto).collect(Collectors.toList())
        );
        deque.setMemoryHistory(memoryHistoryDto);

        return deque;
    }
}
