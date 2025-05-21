package org.gpavl.datastructuresvisualizationbackend.model.editorbuffer;

import org.gpavl.datastructuresvisualizationbackend.model.MemoryHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.model.OperationHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils.*;

public class ArrayEditorBuffer extends EditorBuffer {

    private static final int INITIAL_CAPACITY = 10;

    public ArrayEditorBuffer() {
        memoryHistory = new MemoryHistoryDto();
        OperationHistoryDto operationHistory = new OperationHistoryDto("ArrayEditorBuffer", Collections.emptyMap());

        operationHistory.addInstanceVariable("capacity", INITIAL_CAPACITY);

        List<String> array = Collections.nCopies(INITIAL_CAPACITY, null);
        String newAddress = operationHistory.addNewObject(array);
        operationHistory.addInstanceVariable("array", newAddress);

        operationHistory.addInstanceVariable("count", 0);
        operationHistory.addInstanceVariable("cursor", 0);

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void moveCursorForward() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("moveCursorForward", memoryHistory);

        int count = getCount(operationHistory);
        int cursor = getCursor(operationHistory);

        if (cursor < count) {
            cursor++;
            operationHistory.addInstanceVariable("cursor", cursor);
        }

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void moveCursorBackward() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("moveCursorBackward", memoryHistory);

        int cursor = getCursor(operationHistory);

        if (cursor > 0) {
            cursor--;
            operationHistory.addInstanceVariable("cursor", cursor);
        }

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void moveCursorToStart() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("moveCursorToStart", memoryHistory);
        operationHistory.addInstanceVariable("cursor", 0);
        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void moveCursorToEnd() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("moveCursorToEnd", memoryHistory);
        int count = getCount(operationHistory);
        operationHistory.addInstanceVariable("cursor", count);
        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void insertCharacter(char character) {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("insertCharacter", memoryHistory, "character", character);

        operationHistory.addLocalVariable("character", character);

        int capacity = getCapacity(operationHistory);
        int count = getCount(operationHistory);
        int cursor = getCursor(operationHistory);

        if (count == capacity) {
            extendCapacity(operationHistory);
        }

        List<String> array = getArray(operationHistory, "array");

        for (int i = count; i > cursor; i--) {
            array = new ArrayList<>(array);
            array.set(i, array.get(i - 1));
            updateArray(array, operationHistory, "array");
        }
        array = new ArrayList<>(array);
        array.set(cursor, String.valueOf(character));
        updateArray(array, operationHistory, "array");

        count++;
        operationHistory.addInstanceVariable("count", count);
        cursor++;
        operationHistory.addInstanceVariable("cursor", cursor);

        operationHistory.removeLocalVariable("character");

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void deleteCharacter() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("deleteCharacter", memoryHistory);

        int count = getCount(operationHistory);
        int cursor = getCursor(operationHistory);

        List<String> array = getArray(operationHistory, "array");

        if (cursor < count) {
            for (int i = cursor + 1; i < count; i++) {
                array = new ArrayList<>(array);
                array.set(i - 1, array.get(i));
                updateArray(array, operationHistory, "array");
            }
            count--;
            operationHistory.addInstanceVariable("count", count);
        }

        memoryHistory.addOperationHistory(operationHistory);
    }
}
