package org.gpavl.datastructuresvisualizationbackend.util;

import lombok.AllArgsConstructor;
import org.gpavl.datastructuresvisualizationbackend.entity.DataStructureState;
import org.gpavl.datastructuresvisualizationbackend.model.*;
import org.gpavl.datastructuresvisualizationbackend.repository.DataStructureRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class MemoryUtils {

    private DataStructureRepository dataStructureRepository;

    public static String generateNewAddress() {
        return "0x" + UUID
                .randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 8);
    }

    public static OperationHistoryDto getLastMemorySnapshot(
            String operationName,
            MemoryHistoryDto memoryHistory
    ) {
        return new OperationHistoryDto(
                operationName,
                Collections.emptyMap(),
                memoryHistory.getLastMemorySnapshot()
        );
    }

    public static OperationHistoryDto getLastMemorySnapshot(
            String operationName,
            MemoryHistoryDto memoryHistory,
            String parameterName,
            Object parameterValue
    ) {
        return new OperationHistoryDto(
                operationName,
                Map.of(parameterName, parameterValue),
                memoryHistory.getLastMemorySnapshot()
        );
    }

    public static OperationHistoryDto getLastMemorySnapshot(
            String operationName,
            MemoryHistoryDto memoryHistory,
            String firstParameterName,
            Object firstParameterValue,
            String secondParameterName,
            Object secondParameterValue
    ) {
        return new OperationHistoryDto(
                operationName,
                Map.of(firstParameterName,
                        firstParameterValue,
                        secondParameterName,
                        secondParameterValue
                ),
                memoryHistory.getLastMemorySnapshot()
        );
    }

    public DataStructureState getDataStructureState(String name, Type type) {
        Optional<DataStructureState> optionalState = dataStructureRepository.findByNameAndType(name, type);
        return optionalState.orElseThrow();
    }

    public static Node convertToNode(Map<String, Object> obj) {
        Node node = new Node();
        node.setValue((String) obj.get("value"));
        node.setNextAddress((String) obj.get("nextAddress"));
        return node;
    }

    public static DoublyLinkedNode convertToDoublyLinkedNode(Map<String, Object> obj) {
        DoublyLinkedNode doublyLinkedNode = new DoublyLinkedNode();
        doublyLinkedNode.setValue((String) obj.get("value"));
        doublyLinkedNode.setPreviousAddress((String) obj.get("previousAddress"));
        doublyLinkedNode.setNextAddress((String) obj.get("nextAddress"));
        return doublyLinkedNode;
    }

    public static int getCount(OperationHistoryDto operationHistory) {
        return (int) operationHistory.getInstanceVariableValue("count");
    }

    public static int getCapacity(OperationHistoryDto operationHistory) {
        return (int) operationHistory.getInstanceVariableValue("capacity");
    }

    public static List<String> getArray(OperationHistoryDto operationHistory, String name) {
        String arrayAddress = (String) operationHistory.getInstanceVariableValue(name);
        return operationHistory.getArray(arrayAddress);
    }

    public static List<Integer> getIntArray(OperationHistoryDto operationHistory, String name) {
        String arrayAddress = (String) operationHistory.getInstanceVariableValue(name);
        return operationHistory.getIntArray(arrayAddress);
    }

    public static <T> void updateArray(List<T> array, OperationHistoryDto operationHistoryDto, String name) {
        String arrayAddress = (String) operationHistoryDto.getInstanceVariableValue(name);
        operationHistoryDto.updateObject(arrayAddress, array);
    }

    public static void size(MemoryHistoryDto memoryHistory) {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("size", memoryHistory);
        int size = getCount(operationHistory);
        operationHistory.addResult(size);

        memoryHistory.addOperationHistory(operationHistory);
    }

    public static void isEmpty(MemoryHistoryDto memoryHistory) {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("isEmpty", memoryHistory);
        int size = getCount(operationHistory);
        boolean isEmpty = size == 0;
        operationHistory.addResult(isEmpty);

        memoryHistory.addOperationHistory(operationHistory);
    }
}
