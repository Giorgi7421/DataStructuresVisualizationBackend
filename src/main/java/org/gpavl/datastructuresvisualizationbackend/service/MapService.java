package org.gpavl.datastructuresvisualizationbackend.service;

import org.gpavl.datastructuresvisualizationbackend.entity.DataStructureState;
import org.gpavl.datastructuresvisualizationbackend.model.MemoryHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.model.Response;
import org.gpavl.datastructuresvisualizationbackend.model.Type;
import org.gpavl.datastructuresvisualizationbackend.model.map.ArrayMap;
import org.gpavl.datastructuresvisualizationbackend.model.map.HashMap;
import org.gpavl.datastructuresvisualizationbackend.model.set.MoveToFrontSet;
import org.gpavl.datastructuresvisualizationbackend.model.set.Set;
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
public class MapService extends DataStructureService {

    private final OperationUtils operationUtils;
    private final Map<Type, Supplier<org.gpavl.datastructuresvisualizationbackend.model.map.Map>> typeMap;

    public MapService(
            DataStructureRepository dataStructureRepository,
            MemoryUtils memoryUtils,
            UserService userService,
            OperationUtils operationUtils
    ) {
        super(dataStructureRepository, memoryUtils, userService);
        this.operationUtils = operationUtils;

        typeMap = Map.of(
                Type.HASH_MAP, HashMap::new,
                Type.ARRAY_MAP, ArrayMap::new
        );
    }

    public Response size(Type type, String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, type);
        org.gpavl.datastructuresvisualizationbackend.model.map.Map map = convertToMap(state, type);
        return operationUtils.executeNoArgumentOperation(
                state,
                map,
                org.gpavl.datastructuresvisualizationbackend.model.map.Map::size
        );
    }

    public Response isEmpty(Type type, String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, type);
        org.gpavl.datastructuresvisualizationbackend.model.map.Map map = convertToMap(state, type);
        return operationUtils.executeNoArgumentOperation(
                state,
                map,
                org.gpavl.datastructuresvisualizationbackend.model.map.Map::isEmpty
        );
    }

    public Response clear(Type type, String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, type);
        org.gpavl.datastructuresvisualizationbackend.model.map.Map map = convertToMap(state, type);
        return operationUtils.executeNoArgumentOperation(
                state,
                map,
                org.gpavl.datastructuresvisualizationbackend.model.map.Map::clear
        );
    }

    public Response put(Type type, String name, String key, String value) {
        DataStructureState state = memoryUtils.getDataStructureState(name, type);
        org.gpavl.datastructuresvisualizationbackend.model.map.Map map = convertToMap(state, type);
        return operationUtils.executeTwoArgumentOperation(
                state,
                map,
                org.gpavl.datastructuresvisualizationbackend.model.map.Map::put,
                key,
                value
        );
    }

    public Response get(Type type, String name, String key) {
        DataStructureState state = memoryUtils.getDataStructureState(name, type);
        org.gpavl.datastructuresvisualizationbackend.model.map.Map map = convertToMap(state, type);
        return operationUtils.executeOneArgumentOperation(
                state,
                map,
                org.gpavl.datastructuresvisualizationbackend.model.map.Map::get,
                key
        );
    }

    public Response containsKey(Type type, String name, String key) {
        DataStructureState state = memoryUtils.getDataStructureState(name, type);
        org.gpavl.datastructuresvisualizationbackend.model.map.Map map = convertToMap(state, type);
        return operationUtils.executeOneArgumentOperation(
                state,
                map,
                org.gpavl.datastructuresvisualizationbackend.model.map.Map::containsKey,
                key
        );
    }

    private org.gpavl.datastructuresvisualizationbackend.model.map.Map convertToMap(DataStructureState state, Type type) {
        org.gpavl.datastructuresvisualizationbackend.model.map.Map map = typeMap.get(type).get();
        MemoryHistoryDto memoryHistoryDto = new MemoryHistoryDto();
        memoryHistoryDto.setOperationHistoryList(
                state.getMemoryHistory().getOperationHistoryList().stream().map(Converter::convertToOperationHistoryDto).collect(Collectors.toList())
        );
        map.setMemoryHistory(memoryHistoryDto);

        return map;
    }
}
