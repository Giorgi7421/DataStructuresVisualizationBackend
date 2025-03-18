package org.gpavl.datastructuresvisualizationbackend.service;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.function.TriConsumer;
import org.gpavl.datastructuresvisualizationbackend.entity.ArrayVectorState;
import org.gpavl.datastructuresvisualizationbackend.entity.MemorySnapshot;
import org.gpavl.datastructuresvisualizationbackend.model.MemorySnapshotDto;
import org.gpavl.datastructuresvisualizationbackend.model.arrayvector.ArrayVector;
import org.gpavl.datastructuresvisualizationbackend.model.arrayvector.ArrayVectorCreateRequest;
import org.gpavl.datastructuresvisualizationbackend.model.arrayvector.ArrayVectorStateResponse;
import org.gpavl.datastructuresvisualizationbackend.repository.ArrayVectorRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ArrayVectorService {

    private ArrayVectorRepository arrayVectorRepository;

    public ArrayVectorStateResponse createArrayVector(ArrayVectorCreateRequest arrayVectorCreateRequest) {
        ArrayVector arrayVector = buildArrayVector(arrayVectorCreateRequest);
        ArrayVectorState state = convertToArrayVectorState(arrayVector, arrayVectorCreateRequest.getName());

        ArrayVectorState result = arrayVectorRepository.save(state);
        return convertToResponse(result);
    }

    public ArrayVectorStateResponse findByName(String name) {
        Optional<ArrayVectorState> optionalState = arrayVectorRepository.findByName(name);

        ArrayVectorState state = optionalState.orElseThrow();
        return convertToResponse(state);
    }

    public int size(String name) {
        return executeNoArgumentGetOperation(name, ArrayVector::size);
    }

    public boolean isEmpty(String name) {
        return executeNoArgumentGetOperation(name, ArrayVector::isEmpty);
    }

    public ArrayVectorStateResponse clear(String name) {
        return executeNoArgumentSetOperation(name, ArrayVector::clear);
    }

    public String get(String name, int index) {
        return executeOneArgumentGetOperation(name, ArrayVector::get, index);
    }

    public ArrayVectorStateResponse set(String name, int index, String element) {
        return executeTwoArgumentSetOperation(name, ArrayVector::set, index, element);
    }

    public ArrayVectorStateResponse add(String name, String element) {
        return executeOneArgumentSetOperation(name, ArrayVector::add, element);
    }

    public ArrayVectorStateResponse insertAt(String name, int index, String element) {
        return executeTwoArgumentSetOperation(name, ArrayVector::insertAt, index, element);
    }

    public ArrayVectorStateResponse removeAt(String name, int index) {
        return executeOneArgumentSetOperation(name, ArrayVector::removeAt, index);
    }

    private ArrayVector buildArrayVector(ArrayVectorCreateRequest arrayVectorCreateRequest) {
        return isDefaultConstructionRequest(arrayVectorCreateRequest) ?
                new ArrayVector() :
                new ArrayVector(
                        arrayVectorCreateRequest.getAmount(),
                        arrayVectorCreateRequest.getValue()
                );
    }

    private ArrayVectorState convertToArrayVectorState(ArrayVector arrayVector, String name) {
        ArrayVectorState arrayVectorState = new ArrayVectorState();
        arrayVectorState.setName(name);
        arrayVectorState.setCount(arrayVector.getCount());
        arrayVectorState.setCapacity(arrayVector.getCapacity());
        arrayVectorState.setArray(Arrays.stream(arrayVector.getArray()).filter(Objects::nonNull).toList());
        arrayVectorState.setSteps(arrayVector.getSteps().stream().map(ArrayVectorService::convertToMemorySnapshot).toList());
        return arrayVectorState;
    }

    private ArrayVectorStateResponse convertToResponse(ArrayVectorState state) {
        ArrayVectorStateResponse response = new ArrayVectorStateResponse();
        response.setCount(state.getCount());
        response.setCapacity(state.getCapacity());
        response.setArray(state.getArray());
        response.setSteps(state.getSteps().stream().map(ArrayVectorService::convertToMemorySnapshotDto).toList());
        return response;
    }

    private <R> R executeNoArgumentGetOperation(String name, Function<ArrayVector, R> operation) {
        ArrayVectorState state = getArrayVectorState(name);
        ArrayVector arrayVector = convertToArrayVector(state);
        return operation.apply(arrayVector);
    }

    private static MemorySnapshot convertToMemorySnapshot(MemorySnapshotDto memorySnapshotDto) {
        MemorySnapshot memorySnapshot = new MemorySnapshot();
        memorySnapshot.setLocalVariables(new HashMap<>(memorySnapshotDto.getLocalVariables()));
        memorySnapshot.setInstanceVariables(new HashMap<>(memorySnapshotDto.getInstanceVariables()));
        memorySnapshot.setMessage(memorySnapshotDto.getMessage());
        return memorySnapshot;
    }

    private static MemorySnapshotDto convertToMemorySnapshotDto(MemorySnapshot memorySnapshot) {
        MemorySnapshotDto memorySnapshotdto = new MemorySnapshotDto();
        memorySnapshotdto.setLocalVariables(new HashMap<>(memorySnapshot.getLocalVariables()));
        memorySnapshotdto.setInstanceVariables(new HashMap<>(memorySnapshot.getInstanceVariables()));
        memorySnapshotdto.setMessage(memorySnapshot.getMessage());
        return memorySnapshotdto;
    }

    private <U, R> R executeOneArgumentGetOperation(String name, BiFunction<ArrayVector, U, R> operation, U argument) {
        ArrayVectorState state = getArrayVectorState(name);
        ArrayVector arrayVector = convertToArrayVector(state);
        return operation.apply(arrayVector, argument);
    }

    private <U, V> ArrayVectorStateResponse executeTwoArgumentSetOperation(String name, TriConsumer<ArrayVector, U, V> operation, U firstArgument, V secondArgument) {
        ArrayVectorState state = getArrayVectorState(name);
        ArrayVector arrayVector = convertToArrayVector(state);
        operation.accept(arrayVector, firstArgument, secondArgument);
        ArrayVectorState newState = convertToArrayVectorState(arrayVector, name);
        newState.setId(state.getId());
        ArrayVectorState result = arrayVectorRepository.save(newState);
        return convertToResponse(result);
    }

    private <U> ArrayVectorStateResponse executeOneArgumentSetOperation(String name, BiConsumer<ArrayVector, U> operation, U argument) {
        ArrayVectorState state = getArrayVectorState(name);
        ArrayVector arrayVector = convertToArrayVector(state);
        operation.accept(arrayVector, argument);
        ArrayVectorState newState = convertToArrayVectorState(arrayVector, name);
        newState.setId(state.getId());
        ArrayVectorState result = arrayVectorRepository.save(newState);
        return convertToResponse(result);
    }

    private ArrayVectorStateResponse executeNoArgumentSetOperation(String name, Consumer<ArrayVector> operation) {
        ArrayVectorState state = getArrayVectorState(name);
        ArrayVector arrayVector = convertToArrayVector(state);
        operation.accept(arrayVector);
        ArrayVectorState newState = convertToArrayVectorState(arrayVector, name);
        newState.setId(state.getId());
        ArrayVectorState result = arrayVectorRepository.save(newState);
        return convertToResponse(result);
    }

    private boolean isDefaultConstructionRequest(ArrayVectorCreateRequest arrayVectorCreateRequest) {
        return arrayVectorCreateRequest.getAmount() == null
                && arrayVectorCreateRequest.getValue() == null;
    }

    private ArrayVectorState getArrayVectorState(String name) {
        Optional<ArrayVectorState> optionalState = arrayVectorRepository.findByName(name);
        return optionalState.orElseThrow();
    }

    private ArrayVector convertToArrayVector(ArrayVectorState state) {
        int capacity = state.getCapacity();
        List<String> stateArray = state.getArray();

        ArrayVector arrayVector = new ArrayVector();
        arrayVector.setCapacity(capacity);
        arrayVector.setCount(state.getCount());

        String[] array = new String[capacity];
        for (int i = 0; i < stateArray.size(); i++) {
            array[i] = stateArray.get(i);
        }

        arrayVector.setArray(array);
        arrayVector.setSteps(state.getSteps().stream().map(ArrayVectorService::convertToMemorySnapshotDto).collect(Collectors.toList()));
        return arrayVector;
    }
}
