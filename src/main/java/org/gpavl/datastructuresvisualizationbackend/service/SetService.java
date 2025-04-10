package org.gpavl.datastructuresvisualizationbackend.service;

import org.gpavl.datastructuresvisualizationbackend.entity.DataStructureState;
import org.gpavl.datastructuresvisualizationbackend.model.MemoryHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.model.Response;
import org.gpavl.datastructuresvisualizationbackend.model.Type;
import org.gpavl.datastructuresvisualizationbackend.model.vector.ArrayVector;
import org.gpavl.datastructuresvisualizationbackend.model.vector.LinkedListVector;
import org.gpavl.datastructuresvisualizationbackend.model.vector.Vector;
import org.gpavl.datastructuresvisualizationbackend.repository.DataStructureRepository;
import org.gpavl.datastructuresvisualizationbackend.util.Converter;
import org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils;
import org.gpavl.datastructuresvisualizationbackend.util.OperationUtils;
import org.springframework.stereotype.Service;
import org.gpavl.datastructuresvisualizationbackend.model.set.Set;

import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class SetService extends DataStructureService {

    private final OperationUtils operationUtils;
    private final Map<Type, Supplier<Set>> typeMap;

    public SetService(
            DataStructureRepository dataStructureRepository,
            MemoryUtils memoryUtils,
            OperationUtils operationUtils
    ) {
        super(dataStructureRepository, memoryUtils);
        this.operationUtils = operationUtils;

        typeMap = Map.of(
                //Type.ARRAY_VECTOR, ArrayVector::new,
                //Type.LINKED_LIST_VECTOR, LinkedListVector::new
        );
    }

    public Response size(Type type, String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, type);
        Set set = convertToSet(state, type);
        return operationUtils.executeNoArgumentOperation(
                state,
                set,
                Set::size
        );
    }

    public Response isEmpty(Type type, String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, type);
        Set set = convertToSet(state, type);
        return operationUtils.executeNoArgumentOperation(
                state,
                set,
                Set::isEmpty
        );
    }

    public Response clear(Type type, String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, type);
        Set set = convertToSet(state, type);
        return operationUtils.executeNoArgumentOperation(
                state,
                set,
                Set::clear
        );
    }

    public Response add(Type type, String name, String element) {
        DataStructureState state = memoryUtils.getDataStructureState(name, type);
        Set set = convertToSet(state, type);
        return operationUtils.executeOneArgumentOperation(
                state,
                set,
                Set::add,
                element
        );
    }

    public Response remove(Type type, String name, String element) {
        DataStructureState state = memoryUtils.getDataStructureState(name, type);
        Set set = convertToSet(state, type);
        return operationUtils.executeOneArgumentOperation(
                state,
                set,
                Set::remove,
                element
        );
    }

    public Response contains(Type type, String name, String element) {
        DataStructureState state = memoryUtils.getDataStructureState(name, type);
        Set set = convertToSet(state, type);
        return operationUtils.executeOneArgumentOperation(
                state,
                set,
                Set::contains,
                element
        );
    }

    private Set convertToSet(DataStructureState state, Type type) {
        Set set = typeMap.get(type).get();
        MemoryHistoryDto memoryHistoryDto = new MemoryHistoryDto();
        memoryHistoryDto.setOperationHistoryList(
                state.getMemoryHistory().getOperationHistoryList().stream().map(Converter::convertToOperationHistoryDto).collect(Collectors.toList())
        );
        set.setMemoryHistory(memoryHistoryDto);

        return set;
    }
}
