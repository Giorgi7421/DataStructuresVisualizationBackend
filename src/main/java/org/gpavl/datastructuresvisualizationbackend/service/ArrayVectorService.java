package org.gpavl.datastructuresvisualizationbackend.service;

import lombok.AllArgsConstructor;
import org.gpavl.datastructuresvisualizationbackend.entity.DataStructureState;
import org.gpavl.datastructuresvisualizationbackend.model.MemoryHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.model.Response;
import org.gpavl.datastructuresvisualizationbackend.model.Type;
import org.gpavl.datastructuresvisualizationbackend.model.vector.ArrayVector;
import org.gpavl.datastructuresvisualizationbackend.model.vector.VectorCreateRequest;
import org.gpavl.datastructuresvisualizationbackend.repository.DataStructureRepository;
import org.gpavl.datastructuresvisualizationbackend.util.Converter;
import org.gpavl.datastructuresvisualizationbackend.util.OperationUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ArrayVectorService {

    private DataStructureRepository arrayVectorRepository;
    private OperationUtils operationUtils;

    public Response createArrayVector(VectorCreateRequest arrayVectorCreateRequest) {
        ArrayVector arrayVector = buildArrayVector(arrayVectorCreateRequest);
        DataStructureState state = Converter.convertToDataStructureState(arrayVector, arrayVectorCreateRequest.getName(), Type.ARRAY_VECTOR);

        DataStructureState result;
        try {
            result = arrayVectorRepository.save(state);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateKeyException("Vector with that name already exists");
        }

        return Converter.convertToResponse(result);
    }

    public Response findByName(String name) {
        return Converter.convertToResponse(getArrayVectorState(name));
    }

    public Response size(String name) {
        DataStructureState state = getArrayVectorState(name);
        ArrayVector arrayVector = convertToArrayVector(state);
        return operationUtils.executeNoArgumentOperation(
                state,
                arrayVector,
                ArrayVector::size
        );
    }

    public Response isEmpty(String name) {
        DataStructureState state = getArrayVectorState(name);
        ArrayVector arrayVector = convertToArrayVector(state);
        return operationUtils.executeNoArgumentOperation(
                state,
                arrayVector,
                ArrayVector::isEmpty
        );
    }

    public Response clear(String name) {
        DataStructureState state = getArrayVectorState(name);
        ArrayVector arrayVector = convertToArrayVector(state);
        return operationUtils.executeNoArgumentOperation(
                state,
                arrayVector,
                ArrayVector::clear
        );
    }

    public Response get(String name, int index) {
        DataStructureState state = getArrayVectorState(name);
        ArrayVector arrayVector = convertToArrayVector(state);
        return operationUtils.executeOneArgumentOperation(
                state,
                arrayVector,
                ArrayVector::get,
                index
        );
    }

    public Response set(String name, int index, String element) {
        DataStructureState state = getArrayVectorState(name);
        ArrayVector arrayVector = convertToArrayVector(state);
        return operationUtils.executeTwoArgumentOperation(
                state,
                arrayVector,
                ArrayVector::set,
                index,
                element
        );
    }

    public Response add(String name, String element) {
        DataStructureState state = getArrayVectorState(name);
        ArrayVector arrayVector = convertToArrayVector(state);
        return operationUtils.executeOneArgumentOperation(
                state,
                arrayVector,
                ArrayVector::add,
                element
        );
    }

    public Response insertAt(String name, int index, String element) {
        DataStructureState state = getArrayVectorState(name);
        ArrayVector arrayVector = convertToArrayVector(state);
        return operationUtils.executeThreeArgumentOperation(
                state,
                arrayVector,
                ArrayVector::insertAt,
                index,
                element,
                null
        );
    }

    public Response removeAt(String name, int index) {
        DataStructureState state = getArrayVectorState(name);
        ArrayVector arrayVector = convertToArrayVector(state);
        return operationUtils.executeOneArgumentOperation(
                state,
                arrayVector,
                ArrayVector::removeAt,
                index
        );
    }

    private ArrayVector buildArrayVector(VectorCreateRequest arrayVectorCreateRequest) {
        return isDefaultConstructionRequest(arrayVectorCreateRequest) ?
                new ArrayVector() :
                new ArrayVector(
                        arrayVectorCreateRequest.getAmount(),
                        arrayVectorCreateRequest.getValue()
                );
    }

    private boolean isDefaultConstructionRequest(VectorCreateRequest arrayVectorCreateRequest) {
        return arrayVectorCreateRequest.getAmount() == null
                && arrayVectorCreateRequest.getValue() == null;
    }

    private DataStructureState getArrayVectorState(String name) {
        Optional<DataStructureState> optionalState = arrayVectorRepository.findByNameAndType(name, Type.ARRAY_VECTOR);
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
