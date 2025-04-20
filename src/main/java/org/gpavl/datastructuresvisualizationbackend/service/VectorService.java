package org.gpavl.datastructuresvisualizationbackend.service;

import org.gpavl.datastructuresvisualizationbackend.entity.DataStructureState;
import org.gpavl.datastructuresvisualizationbackend.model.MemoryHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.model.Response;
import org.gpavl.datastructuresvisualizationbackend.model.Type;
import org.gpavl.datastructuresvisualizationbackend.model.vector.ArrayVector;
import org.gpavl.datastructuresvisualizationbackend.model.vector.LinkedListVector;
import org.gpavl.datastructuresvisualizationbackend.model.vector.Vector;
import org.gpavl.datastructuresvisualizationbackend.repository.DataStructureRepository;
import org.gpavl.datastructuresvisualizationbackend.security.UserService;
import org.gpavl.datastructuresvisualizationbackend.util.Converter;
import org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils;
import org.gpavl.datastructuresvisualizationbackend.util.OperationUtils;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class VectorService extends DataStructureService {

    private final OperationUtils operationUtils;
    private final Map<Type, Supplier<Vector>> typeMap;

    public VectorService(
            DataStructureRepository dataStructureRepository,
            MemoryUtils memoryUtils,
            UserService userService,
            OperationUtils operationUtils
    ) {
        super(dataStructureRepository, memoryUtils, userService);
        this.operationUtils = operationUtils;

        typeMap = Map.of(
                Type.ARRAY_VECTOR, ArrayVector::new,
                Type.LINKED_LIST_VECTOR, LinkedListVector::new
        );
    }

    public Response size(Type type, String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, type);
        Vector vector = convertToVector(state, type);
        return operationUtils.executeNoArgumentOperation(
                state,
                vector,
                Vector::size
        );
    }

    public Response isEmpty(Type type, String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, type);
        Vector vector = convertToVector(state, type);
        return operationUtils.executeNoArgumentOperation(
                state,
                vector,
                Vector::isEmpty
        );
    }

    public Response clear(Type type, String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, type);
        Vector vector = convertToVector(state, type);
        return operationUtils.executeNoArgumentOperation(
                state,
                vector,
                Vector::clear
        );
    }

    public Response get(Type type, String name, int index) {
        DataStructureState state = memoryUtils.getDataStructureState(name, type);
        Vector vector = convertToVector(state, type);
        return operationUtils.executeOneArgumentOperation(
                state,
                vector,
                Vector::get,
                index
        );
    }

    public Response set(Type type, String name, int index, String element) {
        DataStructureState state = memoryUtils.getDataStructureState(name, type);
        Vector vector = convertToVector(state, type);
        return operationUtils.executeTwoArgumentOperation(
                state,
                vector,
                Vector::set,
                index,
                element
        );
    }

    public Response add(Type type, String name, String element) {
        DataStructureState state = memoryUtils.getDataStructureState(name, type);
        Vector vector = convertToVector(state, type);
        return operationUtils.executeOneArgumentOperation(
                state,
                vector,
                Vector::add,
                element
        );
    }

    public Response insertAt(Type type, String name, int index, String element) {
        DataStructureState state = memoryUtils.getDataStructureState(name, type);
        Vector vector = convertToVector(state, type);
        return operationUtils.executeTwoArgumentOperation(
                state,
                vector,
                Vector::insertAt,
                index,
                element
        );
    }

    public Response removeAt(Type type, String name, int index) {
        DataStructureState state = memoryUtils.getDataStructureState(name, type);
        Vector vector = convertToVector(state, type);
        return operationUtils.executeOneArgumentOperation(
                state,
                vector,
                Vector::removeAt,
                index
        );
    }

    private Vector convertToVector(DataStructureState state, Type type) {
        Vector vector = typeMap.get(type).get();
        MemoryHistoryDto memoryHistoryDto = new MemoryHistoryDto();
        memoryHistoryDto.setOperationHistoryList(
                state.getMemoryHistory().getOperationHistoryList().stream().map(Converter::convertToOperationHistoryDto).collect(Collectors.toList())
        );
        vector.setMemoryHistory(memoryHistoryDto);

        return vector;
    }
}
