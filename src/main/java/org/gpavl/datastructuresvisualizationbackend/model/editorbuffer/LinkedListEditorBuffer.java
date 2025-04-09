package org.gpavl.datastructuresvisualizationbackend.model.editorbuffer;

import org.gpavl.datastructuresvisualizationbackend.model.MemoryHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.model.Node;
import org.gpavl.datastructuresvisualizationbackend.model.OperationHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils;

import java.util.Collections;
import java.util.Objects;

import static org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils.convertToNode;

public class LinkedListEditorBuffer extends EditorBuffer {

    public LinkedListEditorBuffer() {
        memoryHistory = new MemoryHistoryDto();
        OperationHistoryDto operationHistory = new OperationHistoryDto("LinkedListEditorBuffer", Collections.emptyMap());

        Node node = new Node();
        String nodeAddress = operationHistory.addNewObject(node);
        operationHistory.addInstanceVariable("start", nodeAddress);
        operationHistory.addInstanceVariable("cursor", nodeAddress);

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void moveCursorForward() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("moveCursorForward", memoryHistory);

        String cursorAddress = (String) operationHistory.getInstanceVariableValue("cursor");
        Node cursor = convertToNode(operationHistory.getObject(cursorAddress));

        if (cursor.getNextAddress() != null) {
            operationHistory.addInstanceVariable("cursor", cursor.getNextAddress());
        }

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void moveCursorBackward() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("moveCursorBackward", memoryHistory);

        String startAddress = (String) operationHistory.getInstanceVariableValue("start");
        operationHistory.addLocalVariable("cp", startAddress);
        Node node = convertToNode(operationHistory.getObject(startAddress));

        String cursorAddress = (String) operationHistory.getInstanceVariableValue("cursor");

        if (!Objects.equals(cursorAddress, startAddress)) {
            while (!Objects.equals(node.getNextAddress(), cursorAddress)) {
                String next = node.getNextAddress();
                operationHistory.addLocalVariable("cp", next);
                node = convertToNode(operationHistory.getObject(next));
            }
            String nxt = (String) operationHistory.getLocalVariableValue("cp");
            operationHistory.addInstanceVariable("cursor", nxt);
        }

        operationHistory.removeLocalVariable("cp");

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void moveCursorToStart() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("moveCursorToStart", memoryHistory);

        String startAddress = (String) operationHistory.getInstanceVariableValue("start");
        operationHistory.addInstanceVariable("cursor", startAddress);

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void moveCursorToEnd() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("moveCursorToEnd", memoryHistory);

        String cursorAddress = (String) operationHistory.getInstanceVariableValue("cursor");
        Node cursor = convertToNode(operationHistory.getObject(cursorAddress));

        while (cursor.getNextAddress() != null) {
            operationHistory.addInstanceVariable("cursor", cursor.getNextAddress());
            cursor = convertToNode(operationHistory.getObject(cursor.getNextAddress()));
        }

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void insertCharacter(char character) {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("insertCharacter", memoryHistory, "character", character);

        operationHistory.addLocalVariable("character", character);

        String cursorAddress = (String) operationHistory.getInstanceVariableValue("cursor");
        Node cursor = convertToNode(operationHistory.getObject(cursorAddress));

        Node cp = new Node();
        String cpAddress = operationHistory.addNewObject(cp);
        operationHistory.addLocalVariable("cp", cpAddress);

        Node cp1 = new Node();
        cp1.setValue(String.valueOf(character));
        operationHistory.updateObject(cpAddress, cp1);

        Node cp2 = new Node();
        cp2.setValue(String.valueOf(character));
        cp2.setNextAddress(cursor.getNextAddress());
        operationHistory.updateObject(cpAddress, cp2);

        Node newCursor = new Node();
        newCursor.setValue(cursor.getValue());
        newCursor.setNextAddress(cpAddress);
        operationHistory.updateObject(cursorAddress, newCursor);

        operationHistory.addInstanceVariable("cursor", cpAddress);

        operationHistory.removeLocalVariable("cp");
        operationHistory.removeLocalVariable("character");

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void deleteCharacter() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("deleteCharacter", memoryHistory);

        String cursorAddress = (String) operationHistory.getInstanceVariableValue("cursor");
        Node cursor = convertToNode(operationHistory.getObject(cursorAddress));

        if (cursor.getNextAddress() != null) {
            operationHistory.addLocalVariable("oldCell", cursor.getNextAddress());

            Node next = convertToNode(operationHistory.getObject(cursor.getNextAddress()));

            Node newCursor = new Node();
            newCursor.setValue(cursor.getValue());
            newCursor.setNextAddress(next.getNextAddress());

            operationHistory.updateObject(cursorAddress, newCursor);

            operationHistory.freeLocalVariable("oldCell", "Node was freed");
        }

        memoryHistory.addOperationHistory(operationHistory);
    }
}
