package org.gpavl.datastructuresvisualizationbackend.util;

import lombok.AllArgsConstructor;
import org.gpavl.datastructuresvisualizationbackend.entity.DataStructureState;
import org.gpavl.datastructuresvisualizationbackend.entity.User;
import org.gpavl.datastructuresvisualizationbackend.model.*;
import org.gpavl.datastructuresvisualizationbackend.model.map.HashMapNode;
import org.gpavl.datastructuresvisualizationbackend.model.map.KeyValuePair;
import org.gpavl.datastructuresvisualizationbackend.model.set.HashSetNode;
import org.gpavl.datastructuresvisualizationbackend.repository.DataStructureRepository;
import org.gpavl.datastructuresvisualizationbackend.security.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Map;

@Service
@AllArgsConstructor
public class MemoryUtils {

    private DataStructureRepository dataStructureRepository;
    private UserService userService;

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

    public static OperationHistoryDto getLastMemorySnapshot(
            String operationName,
            MemoryHistoryDto memoryHistory,
            String firstParameterName,
            Object firstParameterValue,
            String secondParameterName,
            Object secondParameterValue,
            String thirdParameterName,
            Object thirdParameterValue
    ) {
        return new OperationHistoryDto(
                operationName,
                Map.of(firstParameterName,
                        firstParameterValue,
                        secondParameterName,
                        secondParameterValue,
                        thirdParameterName,
                        thirdParameterValue
                ),
                memoryHistory.getLastMemorySnapshot()
        );
    }

    public DataStructureState getDataStructureState(String name, Type type) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userService.getCurrentUser(userName);
        Optional<DataStructureState> optionalState = dataStructureRepository.findByNameAndUserAndType(name, currentUser, type);
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

    public static TreeNode convertToTreeNode(Map<String, Object> obj) {
        TreeNode treeNode = new TreeNode();
        treeNode.setValue((String) obj.get("value"));
        treeNode.setLeft((String) obj.get("left"));
        treeNode.setRight((String) obj.get("right"));
        return treeNode;
    }

    public static HashMapNode convertToHashMapNode(Map<String, Object> obj) {
        HashMapNode hashMapNode = new HashMapNode();
        hashMapNode.setKey((String) obj.get("key"));
        hashMapNode.setValue((String) obj.get("value"));
        hashMapNode.setLinkAddress((String) obj.get("linkAddress"));
        return hashMapNode;
    }

    public static HashSetNode convertToHashSetNode(Map<String, Object> obj) {
        HashSetNode hashSetNode = new HashSetNode();
        hashSetNode.setElement((String) obj.get("element"));
        hashSetNode.setLinkAddress((String) obj.get("linkAddress"));
        return hashSetNode;
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

    public static List<KeyValuePair> getKeyValuePairArray(OperationHistoryDto operationHistory, String name) {
        String arrayAddress = (String) operationHistory.getInstanceVariableValue(name);
        return operationHistory.getKeyValuePairArray(arrayAddress);
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

    public static void extendCapacity(OperationHistoryDto operationHistory) {
        List<String> oldArray = getArray(operationHistory, "array");
        String arrayAddress = (String) operationHistory.getInstanceVariableValue("array");
        operationHistory.addLocalVariable("oldArray", arrayAddress);

        int capacity = getCapacity(operationHistory);
        capacity *= 2;
        operationHistory.addInstanceVariable("capacity", capacity);

        List<String> array = Collections.nCopies(capacity, null);
        String newAddress = operationHistory.addNewObject(array);
        operationHistory.addInstanceVariable("array", newAddress);

        int count = getCount(operationHistory);

        for (int i = 0; i < count; i++) {
            array = new ArrayList<>(array);
            array.set(i, oldArray.get(i));
            updateArray(array, operationHistory, "array");
            if (i == count - 1) {
                operationHistory.addMessage("Extending the capacity is completed");
            }
        }

        operationHistory.freeLocalVariable("oldArray", "Deleted old array to avoid memory leaks");
    }
}
