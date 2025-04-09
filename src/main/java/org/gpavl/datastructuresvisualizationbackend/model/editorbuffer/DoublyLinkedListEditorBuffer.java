package org.gpavl.datastructuresvisualizationbackend.model.editorbuffer;

import org.gpavl.datastructuresvisualizationbackend.model.DoublyLinkedNode;
import org.gpavl.datastructuresvisualizationbackend.model.MemoryHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.model.OperationHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils;

import java.util.Collections;
import java.util.Objects;

import static org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils.convertToDoublyLinkedNode;

public class DoublyLinkedListEditorBuffer extends EditorBuffer {

    public DoublyLinkedListEditorBuffer() {
        memoryHistory = new MemoryHistoryDto();
        OperationHistoryDto operationHistory = new OperationHistoryDto("DoublyLinkedListEditorBuffer", Collections.emptyMap());

        DoublyLinkedNode node = new DoublyLinkedNode();
        String nodeAddress = operationHistory.addNewObject(node);

        DoublyLinkedNode nwNode = new DoublyLinkedNode();
        nwNode.setPreviousAddress(nodeAddress);
        nwNode.setNextAddress(nodeAddress);

        operationHistory.updateObject(nodeAddress, nwNode);
        operationHistory.addInstanceVariable("start", nodeAddress);
        operationHistory.addInstanceVariable("cursor", nodeAddress);

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void moveCursorForward() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("moveCursorForward", memoryHistory);

        String cursorAddress = (String) operationHistory.getInstanceVariableValue("cursor");
        DoublyLinkedNode cursor = convertToDoublyLinkedNode(operationHistory.getObject(cursorAddress));

        String startAddress = (String) operationHistory.getInstanceVariableValue("start");

        if (!Objects.equals(cursor.getNextAddress(), startAddress)) {
            operationHistory.addInstanceVariable("cursor", cursor.getNextAddress());
        }

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void moveCursorBackward() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("moveCursorForward", memoryHistory);

        String cursorAddress = (String) operationHistory.getInstanceVariableValue("cursor");
        DoublyLinkedNode cursor = convertToDoublyLinkedNode(operationHistory.getObject(cursorAddress));

        String startAddress = (String) operationHistory.getInstanceVariableValue("start");

        if (!Objects.equals(cursor.getPreviousAddress(), startAddress)) {
            operationHistory.addInstanceVariable("cursor", cursor.getPreviousAddress());
        }

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
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("moveCursorToStart", memoryHistory);

        String startAddress = (String) operationHistory.getInstanceVariableValue("start");
        DoublyLinkedNode node = convertToDoublyLinkedNode(operationHistory.getObject(startAddress));

        operationHistory.addInstanceVariable("cursor", node.getPreviousAddress());

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void insertCharacter(char character) {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("insertCharacter", memoryHistory, "character", character);

        operationHistory.addLocalVariable("character", character);

        String cursorAddress = (String) operationHistory.getInstanceVariableValue("cursor");
        DoublyLinkedNode cursor = convertToDoublyLinkedNode(operationHistory.getObject(cursorAddress));

        DoublyLinkedNode cp = new DoublyLinkedNode();
        String cpAddress = operationHistory.addNewObject(cp);
        operationHistory.addLocalVariable("cp", cpAddress);

        DoublyLinkedNode cp1 = new DoublyLinkedNode();
        cp1.setValue(String.valueOf(character));
        operationHistory.updateObject(cpAddress, cp1);

        DoublyLinkedNode cp2 = new DoublyLinkedNode();
        cp2.setValue(String.valueOf(character));
        cp2.setNextAddress(cursor.getNextAddress());
        operationHistory.updateObject(cpAddress, cp2);

        DoublyLinkedNode cp3 = new DoublyLinkedNode();
        cp3.setValue(String.valueOf(character));
        cp3.setNextAddress(cursor.getNextAddress());
        cp3.setPreviousAddress(cursorAddress);
        operationHistory.updateObject(cpAddress, cp3);

        DoublyLinkedNode newCursor = new DoublyLinkedNode();
        newCursor.setValue(cursor.getValue());
        newCursor.setPreviousAddress(cursor.getPreviousAddress());
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
        DoublyLinkedNode cursor = convertToDoublyLinkedNode(operationHistory.getObject(cursorAddress));

        String startAddress = (String) operationHistory.getInstanceVariableValue("start");

        if (!Objects.equals(cursor.getNextAddress(), startAddress)) {
            operationHistory.addLocalVariable("oldCell", cursor.getNextAddress());

            DoublyLinkedNode next = convertToDoublyLinkedNode(operationHistory.getObject(cursor.getNextAddress()));

            DoublyLinkedNode newCursor = new DoublyLinkedNode();
            newCursor.setValue(cursor.getValue());
            newCursor.setPreviousAddress(cursor.getPreviousAddress());
            newCursor.setNextAddress(next.getNextAddress());

            operationHistory.updateObject(cursorAddress, newCursor);

            if (next.getNextAddress() != null) {
                DoublyLinkedNode nextNext = convertToDoublyLinkedNode(operationHistory.getObject(next.getNextAddress()));

                DoublyLinkedNode nextNext1 = new DoublyLinkedNode();
                nextNext1.setValue(nextNext.getValue());
                nextNext1.setNextAddress(nextNext.getNextAddress());
                nextNext1.setPreviousAddress(cursorAddress);

                operationHistory.updateObject(next.getNextAddress(), nextNext1);
            }

            operationHistory.freeLocalVariable("oldCell", "Node was freed");
        }

        memoryHistory.addOperationHistory(operationHistory);
    }
}
