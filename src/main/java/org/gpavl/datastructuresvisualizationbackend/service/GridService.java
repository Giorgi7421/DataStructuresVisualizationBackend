package org.gpavl.datastructuresvisualizationbackend.service;

import org.gpavl.datastructuresvisualizationbackend.entity.DataStructureState;
import org.gpavl.datastructuresvisualizationbackend.model.Grid;
import org.gpavl.datastructuresvisualizationbackend.model.MemoryHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.model.Response;
import org.gpavl.datastructuresvisualizationbackend.model.Type;
import org.gpavl.datastructuresvisualizationbackend.repository.DataStructureRepository;
import org.gpavl.datastructuresvisualizationbackend.security.UserService;
import org.gpavl.datastructuresvisualizationbackend.util.Converter;
import org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils;
import org.gpavl.datastructuresvisualizationbackend.util.OperationUtils;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class GridService extends DataStructureService {

    private final OperationUtils operationUtils;

    public GridService(
            DataStructureRepository dataStructureRepository,
            MemoryUtils memoryUtils,
            UserService userService,
            OperationUtils operationUtils
    ) {
        super(dataStructureRepository, memoryUtils, userService);
        this.operationUtils = operationUtils;
    }

    public Response inBounds(String name, int row, int column) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.GRID);
        Grid grid = convertToGrid(state);
        return operationUtils.executeTwoArgumentOperation(
                state,
                grid,
                Grid::inBounds,
                row,
                column
        );
    }

    public Response numRows(String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.GRID);
        Grid grid = convertToGrid(state);
        return operationUtils.executeNoArgumentOperation(
                state,
                grid,
                Grid::numRows
        );
    }

    public Response numColumns(String name) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.GRID);
        Grid grid = convertToGrid(state);
        return operationUtils.executeNoArgumentOperation(
                state,
                grid,
                Grid::numColumns
        );
    }

    public Response get(String name, int row, int column) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.GRID);
        Grid grid = convertToGrid(state);
        return operationUtils.executeTwoArgumentOperation(
                state,
                grid,
                Grid::get,
                row,
                column
        );
    }

    public Response set(String name, int row, int column, String element) {
        DataStructureState state = memoryUtils.getDataStructureState(name, Type.GRID);
        Grid grid = convertToGrid(state);
        return operationUtils.executeThreeArgumentOperation(
                state,
                grid,
                Grid::set,
                row,
                column,
                element
        );
    }

    private Grid convertToGrid(DataStructureState state) {
        Grid grid = new Grid();
        MemoryHistoryDto memoryHistoryDto = new MemoryHistoryDto();
        memoryHistoryDto.setOperationHistoryList(
                state.getMemoryHistory().getOperationHistoryList().stream().map(Converter::convertToOperationHistoryDto).collect(Collectors.toList())
        );
        grid.setMemoryHistory(memoryHistoryDto);

        return grid;
    }
}
