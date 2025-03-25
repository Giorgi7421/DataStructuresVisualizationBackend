package org.gpavl.datastructuresvisualizationbackend.service;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.function.TriConsumer;
import org.gpavl.datastructuresvisualizationbackend.entity.MemoryHistory;
import org.gpavl.datastructuresvisualizationbackend.entity.DataStructureState;
import org.gpavl.datastructuresvisualizationbackend.entity.MemorySnapshot;
import org.gpavl.datastructuresvisualizationbackend.model.MemoryHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.model.MemorySnapshotDto;
import org.gpavl.datastructuresvisualizationbackend.model.Response;
import org.gpavl.datastructuresvisualizationbackend.model.vector.ArrayVector;
import org.gpavl.datastructuresvisualizationbackend.model.vector.VectorCreateRequest;
import org.gpavl.datastructuresvisualizationbackend.repository.DataStructureRepository;
import org.gpavl.datastructuresvisualizationbackend.util.Converter;
import org.gpavl.datastructuresvisualizationbackend.util.QuadConsumer;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.*;

@Service
@AllArgsConstructor
public class ArrayVectorService {

    private DataStructureRepository arrayVectorRepository;

    public Response createArrayVector(VectorCreateRequest arrayVectorCreateRequest) {
        ArrayVector arrayVector = buildArrayVector(arrayVectorCreateRequest);
        DataStructureState state = Converter.convertToDataStructureState(arrayVector, arrayVectorCreateRequest.getName());

        DataStructureState result;
        try {
            result = arrayVectorRepository.save(state);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateKeyException("Vector with that name already exists");
        }

        return Converter.convertToResponse(result);
    }

    public Response findByName(String name) {
        Optional<DataStructureState> optionalState = arrayVectorRepository.findByName(name);

        DataStructureState state = optionalState.orElseThrow();
        return Converter.convertToResponse(state);
    }

    public Response size(String name) {
        return executeNoArgumentOperation(name, ArrayVector::size);
    }

    public Response isEmpty(String name) {
        return executeNoArgumentOperation(name, ArrayVector::isEmpty);
    }

    public Response clear(String name) {
        return executeNoArgumentOperation(name, ArrayVector::clear);
    }

    public Response get(String name, int index) {
        return executeOneArgumentOperation(name, ArrayVector::get, index);
    }

    public Response set(String name, int index, String element) {
        return executeTwoArgumentOperation(name, ArrayVector::set, index, element);
    }

    public Response add(String name, String element) {
        return executeOneArgumentOperation(name, ArrayVector::add, element);
    }

    public Response insertAt(String name, int index, String element) {
        return executeThreeArgumentOperation(name, ArrayVector::insertAt, index, element, null);
    }

    public Response removeAt(String name, int index) {
        return executeOneArgumentOperation(name, ArrayVector::removeAt, index);
    }

    private ArrayVector buildArrayVector(VectorCreateRequest arrayVectorCreateRequest) {
        return isDefaultConstructionRequest(arrayVectorCreateRequest) ?
                new ArrayVector() :
                new ArrayVector(
                        arrayVectorCreateRequest.getAmount(),
                        arrayVectorCreateRequest.getValue()
                );
    }

    private <U, V, R> Response executeThreeArgumentOperation(String name, QuadConsumer<ArrayVector, U, V, R> operation, U firstArgument, V secondArgument, R thirdArgument) {
        DataStructureState state = getArrayVectorState(name);
        ArrayVector arrayVector = convertToArrayVector(state);
        operation.accept(arrayVector, firstArgument, secondArgument, thirdArgument);
        DataStructureState newState = Converter.convertToDataStructureState(arrayVector, name);
        newState.setId(state.getId());
        DataStructureState result = arrayVectorRepository.save(newState);
        return Converter.convertToResponse(result);
    }

    private <U, V> Response executeTwoArgumentOperation(String name, TriConsumer<ArrayVector, U, V> operation, U firstArgument, V secondArgument) {
        DataStructureState state = getArrayVectorState(name);
        ArrayVector arrayVector = convertToArrayVector(state);
        operation.accept(arrayVector, firstArgument, secondArgument);
        DataStructureState newState = Converter.convertToDataStructureState(arrayVector, name);
        newState.setId(state.getId());
        DataStructureState result = arrayVectorRepository.save(newState);
        return Converter.convertToResponse(result);
    }

    private <U> Response executeOneArgumentOperation(String name, BiConsumer<ArrayVector, U> operation, U argument) {
        DataStructureState state = getArrayVectorState(name);
        ArrayVector arrayVector = convertToArrayVector(state);
        operation.accept(arrayVector, argument);
        DataStructureState newState = Converter.convertToDataStructureState(arrayVector, name);
        newState.setId(state.getId());
        DataStructureState result = arrayVectorRepository.save(newState);
        return Converter.convertToResponse(result);
    }

    private Response executeNoArgumentOperation(String name, Consumer<ArrayVector> operation) {
        DataStructureState state = getArrayVectorState(name);
        ArrayVector arrayVector = convertToArrayVector(state);
        operation.accept(arrayVector);
        DataStructureState newState = Converter.convertToDataStructureState(arrayVector, name);
        newState.setId(state.getId());
        DataStructureState result = arrayVectorRepository.save(newState);
        return Converter.convertToResponse(result);
    }

    private boolean isDefaultConstructionRequest(VectorCreateRequest arrayVectorCreateRequest) {
        return arrayVectorCreateRequest.getAmount() == null
                && arrayVectorCreateRequest.getValue() == null;
    }

    private DataStructureState getArrayVectorState(String name) {
        Optional<DataStructureState> optionalState = arrayVectorRepository.findByName(name);
        return optionalState.orElseThrow();
    }

    private ArrayVector convertToArrayVector(DataStructureState state) {
        ArrayVector arrayVector = new ArrayVector();
        MemoryHistoryDto memoryHistoryDto = new MemoryHistoryDto();
        memoryHistoryDto.setOperationHistoryList(
                state.getMemoryHistory().getOperationHistoryList().stream().map(Converter::convertToOperationHistoryDto).toList()
        );
        arrayVector.setMemoryHistory(memoryHistoryDto);

        return arrayVector;
    }
}
