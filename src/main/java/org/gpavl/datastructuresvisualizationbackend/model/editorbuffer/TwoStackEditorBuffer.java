package org.gpavl.datastructuresvisualizationbackend.model.editorbuffer;

import org.gpavl.datastructuresvisualizationbackend.model.MemoryHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.model.OperationHistoryDto;
import org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils;

import java.util.*;

import static org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils.getArray;
import static org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils.updateArray;

public class TwoStackEditorBuffer extends EditorBuffer {

    public TwoStackEditorBuffer() {
        memoryHistory = new MemoryHistoryDto();
        OperationHistoryDto operationHistory = new OperationHistoryDto("TwoStackEditorBuffer", Collections.emptyMap());

        Deque<String> before = new ArrayDeque<>();
        Deque<String> after = new ArrayDeque<>();

        List<String> beforeList = new ArrayList<>(before);
        List<String> afterList = new ArrayList<>(after);

        String beforeAddress = operationHistory.addNewObject(beforeList);
        operationHistory.addInstanceVariable("before", beforeAddress);

        String afterAddress = operationHistory.addNewObject(afterList);
        operationHistory.addInstanceVariable("after", afterAddress);

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void moveCursorForward() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("moveCursorForward", memoryHistory);

        Deque<String> before = getBefore(operationHistory);
        Deque<String> after = getAfter(operationHistory);

        if (!after.isEmpty()) {
            String character = after.pop();
            operationHistory.addLocalVariable("character", character);
            updateArray(new ArrayList<>(after), operationHistory, "after");
            before.push(character);
            updateArray(new ArrayList<>(before), operationHistory, "before");
            operationHistory.removeLocalVariable("character");
        }

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void moveCursorBackward() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("moveCursorBackward", memoryHistory);

        Deque<String> before = getBefore(operationHistory);
        Deque<String> after = getAfter(operationHistory);

        if (!before.isEmpty()) {
            String character = before.pop();
            operationHistory.addLocalVariable("character", character);
            updateArray(new ArrayList<>(before), operationHistory, "before");
            after.push(character);
            updateArray(new ArrayList<>(after), operationHistory, "after");
            operationHistory.removeLocalVariable("character");
        }

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void moveCursorToStart() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("moveCursorToStart", memoryHistory);

        Deque<String> before = getBefore(operationHistory);
        Deque<String> after = getAfter(operationHistory);

        while (!before.isEmpty()) {
            String character = before.pop();
            operationHistory.addLocalVariable("character", character);
            updateArray(new ArrayList<>(before), operationHistory, "before");
            after.push(character);
            updateArray(new ArrayList<>(after), operationHistory, "after");
            operationHistory.removeLocalVariable("character");
        }

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void moveCursorToEnd() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("moveCursorToEnd", memoryHistory);

        Deque<String> before = getBefore(operationHistory);
        Deque<String> after = getAfter(operationHistory);

        while (!after.isEmpty()) {
            String character = after.pop();
            operationHistory.addLocalVariable("character", character);
            updateArray(new ArrayList<>(after), operationHistory, "after");
            before.push(character);
            updateArray(new ArrayList<>(before), operationHistory, "before");
            operationHistory.removeLocalVariable("character");
        }

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void insertCharacter(char character) {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("insertCharacter", memoryHistory);

        operationHistory.addLocalVariable("character", character);

        Deque<String> before = getBefore(operationHistory);
        before.push(String.valueOf(character));
        updateArray(new ArrayList<>(before), operationHistory, "before");

        operationHistory.removeLocalVariable("character");

        memoryHistory.addOperationHistory(operationHistory);
    }

    @Override
    public void deleteCharacter() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("deleteCharacter", memoryHistory);

        Deque<String> after = getAfter(operationHistory);

        if (!after.isEmpty()) {
            after.pop();
            updateArray(new ArrayList<>(after), operationHistory, "after");
        }

        memoryHistory.addOperationHistory(operationHistory);
    }

    private Deque<String> getBefore(OperationHistoryDto operationHistory) {
        List<String> beforeList = getArray(operationHistory, "before");
        return new ArrayDeque<>(beforeList);
    }

    private Deque<String> getAfter(OperationHistoryDto operationHistory) {
        List<String> beforeList = getArray(operationHistory, "after");
        return new ArrayDeque<>(beforeList);
    }
}
