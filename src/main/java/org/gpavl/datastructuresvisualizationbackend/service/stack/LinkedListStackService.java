package org.gpavl.datastructuresvisualizationbackend.service.stack;

import io.swagger.v3.oas.models.links.Link;
import lombok.AllArgsConstructor;
import org.gpavl.datastructuresvisualizationbackend.entity.DataStructureState;
import org.gpavl.datastructuresvisualizationbackend.model.MemoryHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.model.Response;
import org.gpavl.datastructuresvisualizationbackend.model.Type;
import org.gpavl.datastructuresvisualizationbackend.model.stack.ArrayStack;
import org.gpavl.datastructuresvisualizationbackend.model.stack.LinkedListStack;
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
public class LinkedListStackService {

    private DataStructureRepository dataStructureRepository;
    private OperationUtils operationUtils;
    private MemoryUtils memoryUtils;

    public Response create(String name) {
        LinkedListStack linkedListStack = new LinkedListStack();
        DataStructureState state = Converter.convertToDataStructureState(linkedListStack, name, Type.LINKED_LIST_STACK);

        DataStructureState result;
        try {
            result = dataStructureRepository.save(state);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateKeyException("Data structure with that name already exists");
        }

        return Converter.convertToResponse(result);
    }

    public Response findByName(String name) {
        return Converter.convertToResponse(memoryUtils.getDataStructureState(name, Type.LINKED_LIST_STACK));
    }

    public Response size(String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.LINKED_LIST_STACK);
        LinkedListStack linkedListStack = convertToLinkedListStack(state);
        return operationUtils.executeNoArgumentOperation(
                state,
                linkedListStack,
                LinkedListStack::size
        );
    }

    public Response isEmpty(String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.LINKED_LIST_STACK);
        LinkedListStack linkedListStack = convertToLinkedListStack(state);
        return operationUtils.executeNoArgumentOperation(
                state,
                linkedListStack,
                LinkedListStack::isEmpty
        );
    }

    public Response clear(String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.LINKED_LIST_STACK);
        LinkedListStack linkedListStack = convertToLinkedListStack(state);
        return operationUtils.executeNoArgumentOperation(
                state,
                linkedListStack,
                LinkedListStack::clear
        );
    }

    public Response push(String name, String element) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.LINKED_LIST_STACK);
        LinkedListStack linkedListStack = convertToLinkedListStack(state);
        return operationUtils.executeOneArgumentOperation(
                state,
                linkedListStack,
                LinkedListStack::push,
                element
        );
    }

    public Response pop(String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.LINKED_LIST_STACK);
        LinkedListStack linkedListStack = convertToLinkedListStack(state);
        return operationUtils.executeNoArgumentOperation(
                state,
                linkedListStack,
                LinkedListStack::pop
        );
    }

    public Response peek(String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.LINKED_LIST_STACK);
        LinkedListStack linkedListStack = convertToLinkedListStack(state);
        return operationUtils.executeNoArgumentOperation(
                state,
                linkedListStack,
                LinkedListStack::peek
        );
    }

    private LinkedListStack convertToLinkedListStack(DataStructureState state) {
        LinkedListStack linkedListStack = new LinkedListStack();
        MemoryHistoryDto memoryHistoryDto = new MemoryHistoryDto();
        memoryHistoryDto.setOperationHistoryList(
                state.getMemoryHistory().getOperationHistoryList().stream().map(Converter::convertToOperationHistoryDto).collect(Collectors.toList())
        );
        linkedListStack.setMemoryHistory(memoryHistoryDto);

        return linkedListStack;
    }
}
