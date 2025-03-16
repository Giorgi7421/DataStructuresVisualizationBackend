package org.gpavl.datastructuresvisualizationbackend.service;

import lombok.AllArgsConstructor;
import org.gpavl.datastructuresvisualizationbackend.entity.*;
import org.gpavl.datastructuresvisualizationbackend.model.arrayvector.ArrayVector;
import org.gpavl.datastructuresvisualizationbackend.model.arrayvector.ArrayVectorCreateRequest;
import org.gpavl.datastructuresvisualizationbackend.model.arrayvector.ArrayVectorStateResponse;
import org.gpavl.datastructuresvisualizationbackend.repository.*;
import org.gpavl.datastructuresvisualizationbackend.util.DataType;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ArrayVectorService {

    private IntArrayVectorRepository intArrayVectorRepository;
    private StringArrayVectorRepository stringArrayVectorRepository;
    private CharArrayVectorRepository charArrayVectorRepository;
    private FloatArrayVectorRepository floatArrayVectorRepository;
    private BoolArrayVectorRepository boolArrayVectorRepository;


    @SuppressWarnings("unchecked")
    public <T> ArrayVectorStateResponse<T> createArrayVector(ArrayVectorCreateRequest<T> arrayVectorCreateRequest) {
        DataType dataType = arrayVectorCreateRequest.getDataType();
        switch (dataType) {
            case INT -> {
                ArrayVector<Integer> arrayVector = (ArrayVector<Integer>) buildArrayVector(arrayVectorCreateRequest);
                IntArrayVectorState state = (IntArrayVectorState) convertToArrayVectorState(arrayVector, arrayVectorCreateRequest.getName(), int.class);

                Object[] objectArray = arrayVector.getArray();
                Integer[] array = Arrays.copyOf(objectArray, objectArray.length, Integer[].class);
                List<Integer> intList = Arrays.asList(array);
                state.setArray(intList);

                BaseArrayVectorState result = intArrayVectorRepository.save(state);
                ArrayVectorStateResponse<T> response = convertToResponse(result);
                response.setArray((List<T>) intList);
                return response;
            }
            case STRING -> {
                ArrayVector<String> arrayVector = (ArrayVector<String>) buildArrayVector(arrayVectorCreateRequest);
                StringArrayVectorState state = (StringArrayVectorState) convertToArrayVectorState(arrayVector, arrayVectorCreateRequest.getName(), String.class);

                Object[] objectArray = arrayVector.getArray();
                String[] array = Arrays.copyOf(objectArray, objectArray.length, String[].class);
                List<String> stringList = Arrays.asList(array);
                state.setArray(stringList);

                BaseArrayVectorState result = stringArrayVectorRepository.save(state);
                ArrayVectorStateResponse<T> response = convertToResponse(result);
                response.setArray((List<T>) stringList);
                return response;
            }
            case FLOAT -> {
                ArrayVector<Double> arrayVector = (ArrayVector<Double>) buildArrayVector(arrayVectorCreateRequest);
                FloatArrayVectorState state = (FloatArrayVectorState) convertToArrayVectorState(arrayVector, arrayVectorCreateRequest.getName(), double.class);

                Object[] objectArray = arrayVector.getArray();
                Double[] array = Arrays.copyOf(objectArray, objectArray.length, Double[].class);
                List<Double> floatList = Arrays.asList(array);
                state.setArray(floatList);

                BaseArrayVectorState result = floatArrayVectorRepository.save(state);
                ArrayVectorStateResponse<T> response = convertToResponse(result);
                response.setArray((List<T>) floatList);
                return response;
            }
            case CHAR -> {
                ArrayVector<Character> arrayVector = (ArrayVector<Character>) buildArrayVector(arrayVectorCreateRequest);
                CharArrayVectorState state = (CharArrayVectorState) convertToArrayVectorState(arrayVector, arrayVectorCreateRequest.getName(), char.class);

                Object[] objectArray = arrayVector.getArray();
                Character[] array = Arrays.copyOf(objectArray, objectArray.length, Character[].class);
                List<Character> charList = Arrays.asList(array);
                state.setArray(charList);

                BaseArrayVectorState result = charArrayVectorRepository.save(state);
                ArrayVectorStateResponse<T> response = convertToResponse(result);
                response.setArray((List<T>) charList);
                return response;
            }
            case BOOL -> {
                ArrayVector<Boolean> arrayVector = (ArrayVector<Boolean>) buildArrayVector(arrayVectorCreateRequest);
                BoolArrayVectorState state = (BoolArrayVectorState) convertToArrayVectorState(arrayVector, arrayVectorCreateRequest.getName(), boolean.class);

                Object[] objectArray = arrayVector.getArray();
                Boolean[] array = Arrays.copyOf(objectArray, objectArray.length, Boolean[].class);
                List<Boolean> boolList = Arrays.asList(array);
                state.setArray(boolList);

                BaseArrayVectorState result = boolArrayVectorRepository.save(state);
                ArrayVectorStateResponse<T> response = convertToResponse(result);
                response.setArray((List<T>) boolList);
                return response;
            }
            default -> throw new IllegalArgumentException("Illegal type");
        }
    }

    private <T> ArrayVector<T> buildArrayVector(ArrayVectorCreateRequest<T> arrayVectorCreateRequest) {
        return isDefaultConstructionRequest(arrayVectorCreateRequest) ?
                new ArrayVector<>() :
                new ArrayVector<>(
                        arrayVectorCreateRequest.getAmount(),
                        arrayVectorCreateRequest.getValue()
                );
    }

    private <T> boolean isDefaultConstructionRequest(ArrayVectorCreateRequest<T> arrayVectorCreateRequest) {
        return arrayVectorCreateRequest.getAmount() == null
                && arrayVectorCreateRequest.getValue() == null;
    }

    private <T> BaseArrayVectorState convertToArrayVectorState(ArrayVector<T> arrayVector, String name, Class<?> clazz) {
        BaseArrayVectorState arrayVectorState = buildClassVectorMap().get(clazz);
        arrayVectorState.setName(name);
        arrayVectorState.setCount(arrayVector.getCount());
        arrayVectorState.setCapacity(arrayVector.getCapacity());
        return arrayVectorState;
    }

    private Map<Class<?>, BaseArrayVectorState> buildClassVectorMap() {
        return Map.of(
                int.class, new IntArrayVectorState(),
                String.class, new StringArrayVectorState(),
                char.class, new CharArrayVectorState(),
                double.class, new FloatArrayVectorState(),
                boolean.class, new BoolArrayVectorState()
        );
    }

    private <T> ArrayVectorStateResponse<T> convertToResponse(BaseArrayVectorState state) {
        ArrayVectorStateResponse<T> response = new ArrayVectorStateResponse<>();
        response.setCount(state.getCount());
        response.setCapacity(state.getCapacity());
        return response;
    }
}
