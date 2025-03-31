package org.gpavl.datastructuresvisualizationbackend.service.stack;

import lombok.AllArgsConstructor;
import org.gpavl.datastructuresvisualizationbackend.entity.DataStructureState;
import org.gpavl.datastructuresvisualizationbackend.model.MemoryHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.model.Response;
import org.gpavl.datastructuresvisualizationbackend.model.Type;
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
public class ArrayStackService {

    private DataStructureRepository dataStructureRepository;
    private OperationUtils operationUtils;
    private MemoryUtils memoryUtils;

    public Response create(String name) {
        ArrayStack arrayStack = new ArrayStack();
        DataStructureState state = Converter.convertToDataStructureState(arrayStack, name, Type.ARRAY_STACK);

        DataStructureState result;
        try {
            result = dataStructureRepository.save(state);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateKeyException("Stack with that name already exists");
        }

        return Converter.convertToResponse(result);
    }

    public Response findByName(String name) {
        return Converter.convertToResponse(memoryUtils.getDataStructureState(name, Type.ARRAY_STACK));
    }

    public Response size(String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.ARRAY_STACK);
        ArrayStack arrayStack = convertToArrayStack(state);
        return operationUtils.executeNoArgumentOperation(
                state,
                arrayStack,
                ArrayStack::size
        );
    }

    public Response isEmpty(String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.ARRAY_STACK);
        ArrayStack arrayStack = convertToArrayStack(state);
        return operationUtils.executeNoArgumentOperation(
                state,
                arrayStack,
                ArrayStack::isEmpty
        );
    }

    public Response clear(String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.ARRAY_STACK);
        ArrayStack arrayStack = convertToArrayStack(state);
        return operationUtils.executeNoArgumentOperation(
                state,
                arrayStack,
                ArrayStack::clear
        );
    }

    public Response push(String name, String element) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.ARRAY_STACK);
        ArrayStack arrayStack = convertToArrayStack(state);
        return operationUtils.executeOneArgumentOperation(
                state,
                arrayStack,
                ArrayStack::push,
                element
        );
    }

    public Response pop(String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.ARRAY_STACK);
        ArrayStack arrayStack = convertToArrayStack(state);
        return operationUtils.executeNoArgumentOperation(
                state,
                arrayStack,
                ArrayStack::pop
        );
    }

    public Response peek(String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.ARRAY_STACK);
        ArrayStack arrayStack = convertToArrayStack(state);
        return operationUtils.executeNoArgumentOperation(
                state,
                arrayStack,
                ArrayStack::peek
        );
    }

    private ArrayStack convertToArrayStack(DataStructureState state) {
        ArrayStack arrayStack = new ArrayStack();
        MemoryHistoryDto memoryHistoryDto = new MemoryHistoryDto();
        memoryHistoryDto.setOperationHistoryList(
                state.getMemoryHistory().getOperationHistoryList().stream().map(Converter::convertToOperationHistoryDto).collect(Collectors.toList())
        );
        arrayStack.setMemoryHistory(memoryHistoryDto);

        return arrayStack;
    }
}
