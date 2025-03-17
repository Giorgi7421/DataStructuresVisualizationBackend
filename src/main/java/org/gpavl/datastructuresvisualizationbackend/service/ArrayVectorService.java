package org.gpavl.datastructuresvisualizationbackend.service;

import lombok.AllArgsConstructor;
import org.gpavl.datastructuresvisualizationbackend.entity.ArrayVectorState;
import org.gpavl.datastructuresvisualizationbackend.model.arrayvector.ArrayVector;
import org.gpavl.datastructuresvisualizationbackend.model.arrayvector.ArrayVectorCreateRequest;
import org.gpavl.datastructuresvisualizationbackend.model.arrayvector.ArrayVectorStateResponse;
import org.gpavl.datastructuresvisualizationbackend.repository.ArrayVectorRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

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

    private ArrayVector buildArrayVector(ArrayVectorCreateRequest arrayVectorCreateRequest) {
        return isDefaultConstructionRequest(arrayVectorCreateRequest) ?
                new ArrayVector() :
                new ArrayVector(
                        arrayVectorCreateRequest.getAmount(),
                        arrayVectorCreateRequest.getValue()
                );
    }

    private boolean isDefaultConstructionRequest(ArrayVectorCreateRequest arrayVectorCreateRequest) {
        return arrayVectorCreateRequest.getAmount() == null
                && arrayVectorCreateRequest.getValue() == null;
    }

    private ArrayVectorState convertToArrayVectorState(ArrayVector arrayVector, String name) {
        ArrayVectorState arrayVectorState = new ArrayVectorState();
        arrayVectorState.setName(name);
        arrayVectorState.setCount(arrayVector.getCount());
        arrayVectorState.setCapacity(arrayVector.getCapacity());
        arrayVectorState.setArray(Arrays.asList(arrayVector.getArray()));
        return arrayVectorState;
    }

    private ArrayVectorStateResponse convertToResponse(ArrayVectorState state) {
        ArrayVectorStateResponse response = new ArrayVectorStateResponse();
        response.setCount(state.getCount());
        response.setCapacity(state.getCapacity());
        response.setArray(state.getArray());
        return response;
    }
}
