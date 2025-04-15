package org.gpavl.datastructuresvisualizationbackend.service;

import org.gpavl.datastructuresvisualizationbackend.entity.DataStructureState;
import org.gpavl.datastructuresvisualizationbackend.model.MemoryHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.model.Response;
import org.gpavl.datastructuresvisualizationbackend.model.Type;
import org.gpavl.datastructuresvisualizationbackend.model.stack.ArrayStack;
import org.gpavl.datastructuresvisualizationbackend.model.stack.LinkedListStack;
import org.gpavl.datastructuresvisualizationbackend.model.stack.Stack;
import org.gpavl.datastructuresvisualizationbackend.model.stack.TwoQueueStack;
import org.gpavl.datastructuresvisualizationbackend.repository.DataStructureRepository;
import org.gpavl.datastructuresvisualizationbackend.util.Converter;
import org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils;
import org.gpavl.datastructuresvisualizationbackend.util.OperationUtils;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class StackService extends DataStructureService {

    private final OperationUtils operationUtils;
    private final Map<Type, Supplier<Stack>> typeMap;

    public StackService(
            DataStructureRepository dataStructureRepository,
            MemoryUtils memoryUtils,
            OperationUtils operationUtils
    ) {
        super(dataStructureRepository, memoryUtils);
        this.operationUtils = operationUtils;

        typeMap = Map.of(
                Type.ARRAY_STACK, ArrayStack::new,
                Type.LINKED_LIST_STACK, LinkedListStack::new,
                Type.TWO_QUEUE_STACK, TwoQueueStack::new
        );
    }

    public Response size(Type type, String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, type);
        Stack stack = convertToStack(state, type);
        return operationUtils.executeNoArgumentOperation(
                state,
                stack,
                Stack::size
        );
    }

    public Response isEmpty(Type type, String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, type);
        Stack stack = convertToStack(state, type);
        return operationUtils.executeNoArgumentOperation(
                state,
                stack,
                Stack::isEmpty
        );
    }

    public Response clear(Type type, String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, type);
        Stack stack = convertToStack(state, type);
        return operationUtils.executeNoArgumentOperation(
                state,
                stack,
                Stack::clear
        );
    }

    public Response push(Type type, String name, String element) {
        DataStructureState state = memoryUtils.getDataStructureState(name, type);
        Stack stack = convertToStack(state, type);
        return operationUtils.executeOneArgumentOperation(
                state,
                stack,
                Stack::push,
                element
        );
    }

    public Response pop(Type type, String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, type);
        Stack stack = convertToStack(state, type);
        return operationUtils.executeNoArgumentOperation(
                state,
                stack,
                Stack::pop
        );
    }

    public Response peek(Type type, String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, type);
        Stack stack = convertToStack(state, type);
        return operationUtils.executeNoArgumentOperation(
                state,
                stack,
                Stack::peek
        );
    }

    private Stack convertToStack(DataStructureState state, Type type) {
        Stack stack = typeMap.get(type).get();
        MemoryHistoryDto memoryHistoryDto = new MemoryHistoryDto();
        memoryHistoryDto.setOperationHistoryList(
                state.getMemoryHistory().getOperationHistoryList().stream().map(Converter::convertToOperationHistoryDto).collect(Collectors.toList())
        );
        stack.setMemoryHistory(memoryHistoryDto);

        return stack;
    }
}
