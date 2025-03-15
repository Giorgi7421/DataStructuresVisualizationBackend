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
import java.util.Map;

@Service
@AllArgsConstructor
public class ArrayVectorService {

    private IntArrayVectorRepository intArrayVectorRepository;
    private StringArrayVectorRepository stringArrayVectorRepository;
    private CharArrayVectorRepository charArrayVectorRepository;
    private FloatArrayVectorRepository floatArrayVectorRepository;
    private BoolArrayVectorRepository boolArrayVectorRepository;

    public ArrayVectorStateResponse<?> createArrayVector(ArrayVectorCreateRequest<?> arrayVectorCreateRequest) {
        DataType dataType = arrayVectorCreateRequest.getDataType();
        switch (dataType) {
            case INT -> {
                ArrayVector<Integer> arrayVector =
                        arrayVectorCreateRequest.getAmount() == null ?
                                new ArrayVector<>() :
                                new ArrayVector<>(arrayVectorCreateRequest.getAmount(), (int) arrayVectorCreateRequest.getValue());

                IntArrayVectorState state = convertToIntArrayVector(arrayVector, arrayVectorCreateRequest.getName());
                IntArrayVectorState result = intArrayVectorRepository.save(state);
                return convertToResponse(result);
            }
            case STRING -> {
                ArrayVector<String> arrayVector =
                        arrayVectorCreateRequest.getAmount() == null ?
                                new ArrayVector<>() :
                                new ArrayVector<>(arrayVectorCreateRequest.getAmount(), (String) arrayVectorCreateRequest.getValue());

                StringArrayVectorState state = convertToStringArrayVector(arrayVector, arrayVectorCreateRequest.getName());
                StringArrayVectorState result = stringArrayVectorRepository.save(state);
                return convertToResponse(result);
            }
            case FLOAT -> {
                ArrayVector<Float> arrayVector =
                        arrayVectorCreateRequest.getAmount() == null ?
                                new ArrayVector<>() :
                                new ArrayVector<>(arrayVectorCreateRequest.getAmount(), (float) arrayVectorCreateRequest.getValue());

                FloatArrayVectorState state = convertToFloatArrayVector(arrayVector, arrayVectorCreateRequest.getName());
                FloatArrayVectorState result = floatArrayVectorRepository.save(state);
                return convertToResponse(result);
            }
            case CHAR -> {
                ArrayVector<Character> arrayVector =
                        arrayVectorCreateRequest.getAmount() == null ?
                                new ArrayVector<>() :
                                new ArrayVector<>(arrayVectorCreateRequest.getAmount(), (char) arrayVectorCreateRequest.getValue());

                CharArrayVectorState state = convertToCharArrayVector(arrayVector, arrayVectorCreateRequest.getName());
                CharArrayVectorState result = charArrayVectorRepository.save(state);
                return convertToResponse(result);
            }
            case BOOL -> {
                ArrayVector<Boolean> arrayVector =
                        arrayVectorCreateRequest.getAmount() == null ?
                                new ArrayVector<>() :
                                new ArrayVector<>(arrayVectorCreateRequest.getAmount(), (boolean) arrayVectorCreateRequest.getValue());

                BoolArrayVectorState state = convertToBoolArrayVector(arrayVector, arrayVectorCreateRequest.getName());
                BoolArrayVectorState result = boolArrayVectorRepository.save(state);
                return convertToResponse(result);
            }
            default -> throw new IllegalArgumentException("Illegal type");
        }
    }

    private Map<DataType, BaseArrayVectorRepository<?>> buildTypeMap() {
        return Map.of(
                DataType.INT, intArrayVectorRepository,
                DataType.STRING, stringArrayVectorRepository,
                DataType.CHAR, charArrayVectorRepository,
                DataType.FLOAT, floatArrayVectorRepository,
                DataType.BOOL, boolArrayVectorRepository
        );
    }

    private IntArrayVectorState convertToIntArrayVector(ArrayVector<Integer> intArrayVector, String name) {
        IntArrayVectorState state = new IntArrayVectorState();
        state.setName(name);
        state.setCount(intArrayVector.getCount());
        state.setCapacity(intArrayVector.getCapacity());
        Object[] objectArray = intArrayVector.getArray();
        Integer[] array = Arrays.copyOf(objectArray, objectArray.length, Integer[].class);
        state.setArray(Arrays.asList(array));
        return state;
    }

    private ArrayVectorStateResponse<Integer> convertToResponse(IntArrayVectorState state) {
        ArrayVectorStateResponse<Integer> response = new ArrayVectorStateResponse<>();
        response.setCount(state.getCount());
        response.setCapacity(state.getCapacity());
        response.setArray(state.getArray());
        return response;
    }

    private StringArrayVectorState convertToStringArrayVector(ArrayVector<String> stringArrayVector, String name) {
        StringArrayVectorState state = new StringArrayVectorState();
        state.setName(name);
        state.setCount(stringArrayVector.getCount());
        state.setCapacity(stringArrayVector.getCapacity());
        state.setArray(Arrays.asList((String[]) stringArrayVector.getArray()));
        return state;
    }

    private ArrayVectorStateResponse<String> convertToResponse(StringArrayVectorState state) {
        ArrayVectorStateResponse<String> response = new ArrayVectorStateResponse<>();
        response.setCount(state.getCount());
        response.setCapacity(state.getCapacity());
        response.setArray(state.getArray());
        return response;
    }

    private FloatArrayVectorState convertToFloatArrayVector(ArrayVector<Float> floatArrayVector, String name) {
        FloatArrayVectorState state = new FloatArrayVectorState();
        state.setName(name);
        state.setCount(floatArrayVector.getCount());
        state.setCapacity(floatArrayVector.getCapacity());
        state.setArray(Arrays.asList((Float[]) floatArrayVector.getArray()));
        return state;
    }

    private ArrayVectorStateResponse<Float> convertToResponse(FloatArrayVectorState state) {
        ArrayVectorStateResponse<Float> response = new ArrayVectorStateResponse<>();
        response.setCount(state.getCount());
        response.setCapacity(state.getCapacity());
        response.setArray(state.getArray());
        return response;
    }

    private CharArrayVectorState convertToCharArrayVector(ArrayVector<Character> charArrayVector, String name) {
        CharArrayVectorState state = new CharArrayVectorState();
        state.setName(name);
        state.setCount(charArrayVector.getCount());
        state.setCapacity(charArrayVector.getCapacity());
        state.setArray(Arrays.asList((Character[]) charArrayVector.getArray()));
        return state;
    }

    private ArrayVectorStateResponse<Character> convertToResponse(CharArrayVectorState state) {
        ArrayVectorStateResponse<Character> response = new ArrayVectorStateResponse<>();
        response.setCount(state.getCount());
        response.setCapacity(state.getCapacity());
        response.setArray(state.getArray());
        return response;
    }

    private BoolArrayVectorState convertToBoolArrayVector(ArrayVector<Boolean> booleanArrayVector, String name) {
        BoolArrayVectorState state = new BoolArrayVectorState();
        state.setName(name);
        state.setCount(booleanArrayVector.getCount());
        state.setCapacity(booleanArrayVector.getCapacity());
        state.setArray(Arrays.asList((Boolean[]) booleanArrayVector.getArray()));
        return state;
    }

    private ArrayVectorStateResponse<Boolean> convertToResponse(BoolArrayVectorState state) {
        ArrayVectorStateResponse<Boolean> response = new ArrayVectorStateResponse<>();
        response.setCount(state.getCount());
        response.setCapacity(state.getCapacity());
        response.setArray(state.getArray());
        return response;
    }
}
