package org.gpavl.datastructuresvisualizationbackend.service.vector;

import lombok.AllArgsConstructor;
import org.gpavl.datastructuresvisualizationbackend.entity.DataStructureState;
import org.gpavl.datastructuresvisualizationbackend.model.MemoryHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.model.Response;
import org.gpavl.datastructuresvisualizationbackend.model.Type;
import org.gpavl.datastructuresvisualizationbackend.model.vector.ArrayVector;
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
public class ArrayVectorService {

    private DataStructureRepository dataStructureRepository;
    private OperationUtils operationUtils;
    private MemoryUtils memoryUtils;

    public Response create(VectorCreateRequest vectorCreateRequest) {
        ArrayVector arrayVector = buildVector(vectorCreateRequest);
        DataStructureState state = Converter.convertToDataStructureState(arrayVector, vectorCreateRequest.getName(), Type.ARRAY_VECTOR);

        DataStructureState result;
        try {
            result = dataStructureRepository.save(state);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateKeyException("Data structure with that name already exists");
        }

        return Converter.convertToResponse(result);
    }

    public Response findByName(String name) {
        return Converter.convertToResponse(memoryUtils.getDataStructureState(name, Type.ARRAY_VECTOR));
    }

    public Response size(String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.ARRAY_VECTOR);
        ArrayVector arrayVector = convertToArrayVector(state);
        return operationUtils.executeNoArgumentOperation(
                state,
                arrayVector,
                ArrayVector::size
        );
    }

    public Response isEmpty(String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.ARRAY_VECTOR);
        ArrayVector arrayVector = convertToArrayVector(state);
        return operationUtils.executeNoArgumentOperation(
                state,
                arrayVector,
                ArrayVector::isEmpty
        );
    }

    public Response clear(String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.ARRAY_VECTOR);
        ArrayVector arrayVector = convertToArrayVector(state);
        return operationUtils.executeNoArgumentOperation(
                state,
                arrayVector,
                ArrayVector::clear
        );
    }

    public Response get(String name, int index) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.ARRAY_VECTOR);
        ArrayVector arrayVector = convertToArrayVector(state);
        return operationUtils.executeOneArgumentOperation(
                state,
                arrayVector,
                ArrayVector::get,
                index
        );
    }

    public Response set(String name, int index, String element) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.ARRAY_VECTOR);
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
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.ARRAY_VECTOR);
        ArrayVector arrayVector = convertToArrayVector(state);
        return operationUtils.executeOneArgumentOperation(
                state,
                arrayVector,
                ArrayVector::add,
                element
        );
    }

    public Response insertAt(String name, int index, String element) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.ARRAY_VECTOR);
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
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.ARRAY_VECTOR);
        ArrayVector arrayVector = convertToArrayVector(state);
        return operationUtils.executeOneArgumentOperation(
                state,
                arrayVector,
                ArrayVector::removeAt,
                index
        );
    }

    private ArrayVector buildVector(VectorCreateRequest vectorCreateRequest) {
        return isDefaultConstructionRequest(vectorCreateRequest) ?
                new ArrayVector() :
                new ArrayVector(
                        vectorCreateRequest.getAmount(),
                        vectorCreateRequest.getValue()
                );
    }

    private boolean isDefaultConstructionRequest(VectorCreateRequest vectorCreateRequest) {
        return vectorCreateRequest.getAmount() == null
                && vectorCreateRequest.getValue() == null;
    }

    private ArrayVector convertToArrayVector(DataStructureState state) {
        ArrayVector arrayVector = new ArrayVector();
        MemoryHistoryDto memoryHistoryDto = new MemoryHistoryDto();
        memoryHistoryDto.setOperationHistoryList(
                state.getMemoryHistory().getOperationHistoryList().stream().map(Converter::convertToOperationHistoryDto).collect(Collectors.toList())
        );
        arrayVector.setMemoryHistory(memoryHistoryDto);

        return arrayVector;
    }
}
