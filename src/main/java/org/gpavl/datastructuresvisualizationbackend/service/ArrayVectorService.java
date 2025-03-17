package org.gpavl.datastructuresvisualizationbackend.service;

import lombok.AllArgsConstructor;
import org.gpavl.datastructuresvisualizationbackend.entity.ArrayVectorState;
import org.gpavl.datastructuresvisualizationbackend.model.arrayvector.ArrayVector;
import org.gpavl.datastructuresvisualizationbackend.model.arrayvector.ArrayVectorCreateRequest;
import org.gpavl.datastructuresvisualizationbackend.model.arrayvector.ArrayVectorStateResponse;
import org.gpavl.datastructuresvisualizationbackend.repository.ArrayVectorRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    public ArrayVectorStateResponse add(String name, String value) {
        Optional<ArrayVectorState> optionalState = arrayVectorRepository.findByName(name);
        ArrayVectorState state = optionalState.orElseThrow();

        ArrayVector arrayVector = convertToArrayVector(state);
        arrayVector.add(value);

        ArrayVectorState newState = convertToArrayVectorState(arrayVector, name);
        newState.setId(state.getId());

        ArrayVectorState result = arrayVectorRepository.save(newState);
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
        arrayVectorState.setArray(Arrays.stream(arrayVector.getArray()).filter(Objects::nonNull).toList());
        return arrayVectorState;
    }

    private ArrayVectorStateResponse convertToResponse(ArrayVectorState state) {
        ArrayVectorStateResponse response = new ArrayVectorStateResponse();
        response.setCount(state.getCount());
        response.setCapacity(state.getCapacity());
        response.setArray(state.getArray());
        return response;
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
        return arrayVector;
    }
}
