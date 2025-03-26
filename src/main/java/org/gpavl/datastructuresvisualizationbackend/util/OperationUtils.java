package org.gpavl.datastructuresvisualizationbackend.util;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.function.TriConsumer;
import org.gpavl.datastructuresvisualizationbackend.entity.DataStructureState;
import org.gpavl.datastructuresvisualizationbackend.model.DataStructure;
import org.gpavl.datastructuresvisualizationbackend.model.Response;
import org.gpavl.datastructuresvisualizationbackend.repository.DataStructureRepository;
import org.springframework.stereotype.Service;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Service
@AllArgsConstructor
public class OperationUtils {

    private DataStructureRepository dataStructureRepository;

    public <T extends DataStructure, U, V, R> Response executeThreeArgumentOperation(
            DataStructureState dataStructureState,
            T dataStructure,
            QuadConsumer<T, U, V, R> operation,
            U firstArgument,
            V secondArgument,
            R thirdArgument
    ) {
        operation.accept(dataStructure, firstArgument, secondArgument, thirdArgument);
        return getResponse(dataStructureState, dataStructure);
    }

    public <T extends DataStructure, U, V> Response executeTwoArgumentOperation(
            DataStructureState dataStructureState,
            T dataStructure,
            TriConsumer<T, U, V> operation,
            U firstArgument,
            V secondArgument
    ) {
        operation.accept(dataStructure, firstArgument, secondArgument);
        return getResponse(dataStructureState, dataStructure);
    }

    public <T extends DataStructure, U> Response executeOneArgumentOperation(
            DataStructureState dataStructureState,
            T dataStructure,
            BiConsumer<T, U> operation,
            U argument
    ) {
        operation.accept(dataStructure, argument);
        return getResponse(dataStructureState, dataStructure);
    }

    public <T extends DataStructure> Response executeNoArgumentOperation(
            DataStructureState dataStructureState,
            T dataStructure,
            Consumer<T> operation
    ) {
        operation.accept(dataStructure);
        return getResponse(dataStructureState, dataStructure);
    }

    private Response getResponse(DataStructureState dataStructureState, DataStructure dataStructure) {
        DataStructureState newState = Converter.convertToDataStructureState(
                dataStructure,
                dataStructureState.getName(),
                dataStructureState.getType()
        );

        newState.setId(dataStructureState.getId());
        DataStructureState result = dataStructureRepository.save(newState);
        return Converter.convertToResponse(result);
    }
}
