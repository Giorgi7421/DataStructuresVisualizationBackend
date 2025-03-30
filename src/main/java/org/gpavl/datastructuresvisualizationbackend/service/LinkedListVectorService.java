package org.gpavl.datastructuresvisualizationbackend.service;

import lombok.AllArgsConstructor;
import org.gpavl.datastructuresvisualizationbackend.entity.DataStructureState;
import org.gpavl.datastructuresvisualizationbackend.model.MemoryHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.model.Response;
import org.gpavl.datastructuresvisualizationbackend.model.Type;
import org.gpavl.datastructuresvisualizationbackend.model.vector.LinkedListVector;
import org.gpavl.datastructuresvisualizationbackend.model.vector.VectorCreateRequest;
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
public class LinkedListVectorService {

    private DataStructureRepository dataStructureRepository;
    private OperationUtils operationUtils;
    private MemoryUtils memoryUtils;

    public Response create(VectorCreateRequest vectorCreateRequest) {
        LinkedListVector linkedListVector = buildVector(vectorCreateRequest);
        DataStructureState state = Converter.convertToDataStructureState(linkedListVector, vectorCreateRequest.getName(), Type.LINKED_LIST_VECTOR);

        DataStructureState result;
        try {
            result = dataStructureRepository.save(state);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateKeyException("Vector with that name already exists");
        }

        return Converter.convertToResponse(result);
    }

    public Response findByName(String name) {
        return Converter.convertToResponse(memoryUtils.getDataStructureState(name, Type.LINKED_LIST_VECTOR));
    }

    public Response size(String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.LINKED_LIST_VECTOR);
        LinkedListVector linkedListVector = convertToLinkedListVector(state);
        return operationUtils.executeNoArgumentOperation(
                state,
                linkedListVector,
                LinkedListVector::size
        );
    }

    public Response isEmpty(String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.LINKED_LIST_VECTOR);
        LinkedListVector linkedListVector = convertToLinkedListVector(state);
        return operationUtils.executeNoArgumentOperation(
                state,
                linkedListVector,
                LinkedListVector::isEmpty
        );
    }

    public Response clear(String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.LINKED_LIST_VECTOR);
        LinkedListVector linkedListVector = convertToLinkedListVector(state);
        return operationUtils.executeNoArgumentOperation(
                state,
                linkedListVector,
                LinkedListVector::clear
        );
    }

    public Response get(String name, int index) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.LINKED_LIST_VECTOR);
        LinkedListVector linkedListVector = convertToLinkedListVector(state);
        return operationUtils.executeOneArgumentOperation(
                state,
                linkedListVector,
                LinkedListVector::get,
                index
        );
    }

    public Response set(String name, int index, String element) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.LINKED_LIST_VECTOR);
        LinkedListVector linkedListVector = convertToLinkedListVector(state);
        return operationUtils.executeTwoArgumentOperation(
                state,
                linkedListVector,
                LinkedListVector::set,
                index,
                element
        );
    }

    public Response add(String name, String element) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.LINKED_LIST_VECTOR);
        LinkedListVector linkedListVector = convertToLinkedListVector(state);
        return operationUtils.executeOneArgumentOperation(
                state,
                linkedListVector,
                LinkedListVector::add,
                element
        );
    }

    public Response insertAt(String name, int index, String element) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.LINKED_LIST_VECTOR);
        LinkedListVector linkedListVector = convertToLinkedListVector(state);
        return operationUtils.executeTwoArgumentOperation(
                state,
                linkedListVector,
                LinkedListVector::insertAt,
                index,
                element
        );
    }

    public Response removeAt(String name, int index) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.LINKED_LIST_VECTOR);
        LinkedListVector linkedListVector = convertToLinkedListVector(state);
        return operationUtils.executeOneArgumentOperation(
                state,
                linkedListVector,
                LinkedListVector::removeAt,
                index
        );
    }

    private LinkedListVector convertToLinkedListVector(DataStructureState state) {
        LinkedListVector linkedListVector = new LinkedListVector();
        MemoryHistoryDto memoryHistoryDto = new MemoryHistoryDto();
        memoryHistoryDto.setOperationHistoryList(
                state.getMemoryHistory().getOperationHistoryList().stream().map(Converter::convertToOperationHistoryDto).collect(Collectors.toList())
        );
        linkedListVector.setMemoryHistory(memoryHistoryDto);

        return linkedListVector;
    }

    private LinkedListVector buildVector(VectorCreateRequest vectorCreateRequest) {
        return isDefaultConstructionRequest(vectorCreateRequest) ?
                new LinkedListVector() :
                new LinkedListVector(
                        vectorCreateRequest.getAmount(),
                        vectorCreateRequest.getValue()
                );
    }

    private boolean isDefaultConstructionRequest(VectorCreateRequest vectorCreateRequest) {
        return vectorCreateRequest.getAmount() == null
                && vectorCreateRequest.getValue() == null;
    }
}
