package org.gpavl.datastructuresvisualizationbackend.model.editorbuffer;

import org.gpavl.datastructuresvisualizationbackend.model.DataStructure;
import org.gpavl.datastructuresvisualizationbackend.model.OperationHistoryDto;

public abstract class EditorBuffer extends DataStructure {

    protected int getCursor(OperationHistoryDto operationHistory) {
        return (int) operationHistory.getInstanceVariableValue("cursor");
    }

    public abstract void moveCursorForward();
    public abstract void moveCursorBackward();
    public abstract void moveCursorToStart();
    public abstract void moveCursorToEnd();
    public abstract void insertCharacter(char character);
    public abstract void deleteCharacter();
}
