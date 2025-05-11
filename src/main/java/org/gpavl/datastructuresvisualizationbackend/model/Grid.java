package org.gpavl.datastructuresvisualizationbackend.model;

import org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils.getArray;
import static org.gpavl.datastructuresvisualizationbackend.util.MemoryUtils.updateArray;

public class Grid extends DataStructure {

    public Grid() {

    }

    public Grid(int numRows, int numColumns) {
        memoryHistory = new MemoryHistoryDto();
        OperationHistoryDto operationHistory = new OperationHistoryDto("Grid", Map.of(
                "numRows", numRows,
                "numColumns", numColumns
        ));

        operationHistory.addLocalVariable("numRows", numRows);
        operationHistory.addLocalVariable("numColumns", numColumns);

        if (numRows < 0 || numColumns < 0) {
            throw new IllegalArgumentException("Don't be so negative!");
        }

        operationHistory.addInstanceVariable("rows", numRows);
        operationHistory.addInstanceVariable("columns", numColumns);

        List<String> elems = Collections.nCopies(numRows, null);
        String newAddress = operationHistory.addNewObject(elems);
        operationHistory.addInstanceVariable("elems", newAddress);

        for (int row = 0; row < numRows; row++) {
            List<String> currentRow = Collections.nCopies(numColumns, null);
            String currentRowAddress = operationHistory.addNewObject(currentRow);

            elems = new ArrayList<>(elems);
            elems.set(row, currentRowAddress);
            updateArray(elems, operationHistory, "elems");
        }

        operationHistory.removeLocalVariable("numColumns");
        operationHistory.removeLocalVariable("numRows");
        memoryHistory.addOperationHistory(operationHistory);
    }

    public void inBounds(int row, int column) {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("inBounds", memoryHistory, "row", row, "column", column);

        operationHistory.addLocalVariable("row", row);
        operationHistory.addLocalVariable("column", column);

        int numRows = (int) operationHistory.getInstanceVariableValue("rows");
        int numColumns = (int) operationHistory.getInstanceVariableValue("columns");

        operationHistory.addResult(row >= 0 && column >= 0 && row < numRows && column < numColumns);

        operationHistory.removeLocalVariable("column");
        operationHistory.removeLocalVariable("row");

        memoryHistory.addOperationHistory(operationHistory);
    }

    public void numRows() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("numRows", memoryHistory);

        int numRows = (int) operationHistory.getInstanceVariableValue("rows");
        operationHistory.addResult(numRows);

        memoryHistory.addOperationHistory(operationHistory);
    }

    public void numColumns() {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("numColumns", memoryHistory);

        int numColumns = (int) operationHistory.getInstanceVariableValue("columns");
        operationHistory.addResult(numColumns);

        memoryHistory.addOperationHistory(operationHistory);
    }

    public void get(int row, int column) {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("get", memoryHistory, "row", row, "column", column);

        operationHistory.addLocalVariable("row", row);
        operationHistory.addLocalVariable("column", column);

        int numRows = (int) operationHistory.getInstanceVariableValue("rows");
        int numColumns = (int) operationHistory.getInstanceVariableValue("columns");

        if (!(row >= 0 && column >= 0 && row < numRows && column < numColumns)) {
            throw new IllegalArgumentException("Not in bounds");
        }

        List<String> elems = getArray(operationHistory, "elems");
        String rowAddress = elems.get(row);
        operationHistory.addLocalVariable("rowAddress", rowAddress);

        List<String> rowList = operationHistory.getArray(rowAddress);
        operationHistory.addResult(rowList.get(column));

        operationHistory.removeLocalVariable("rowAddress");

        operationHistory.removeLocalVariable("column");
        operationHistory.removeLocalVariable("row");
        memoryHistory.addOperationHistory(operationHistory);
    }

    public void set(int row, int column, String element) {
        OperationHistoryDto operationHistory = MemoryUtils.getLastMemorySnapshot("set", memoryHistory, "row", row, "column", column, "element", element);

        operationHistory.addLocalVariable("row", row);
        operationHistory.addLocalVariable("column", column);
        operationHistory.addLocalVariable("element", element);

        int numRows = (int) operationHistory.getInstanceVariableValue("rows");
        int numColumns = (int) operationHistory.getInstanceVariableValue("columns");

        if (!(row >= 0 && column >= 0 && row < numRows && column < numColumns)) {
            throw new IllegalArgumentException("Not in bounds");
        }

        List<String> elems = getArray(operationHistory, "elems");
        String rowAddress = elems.get(row);
        operationHistory.addLocalVariable("rowAddress", rowAddress);

        List<String> rowList = operationHistory.getArray(rowAddress);
        rowList = new ArrayList<>(rowList);
        rowList.set(column, element);
        operationHistory.updateObject(rowAddress, rowList);


        operationHistory.removeLocalVariable("rowAddress");

        operationHistory.removeLocalVariable("element");
        operationHistory.removeLocalVariable("column");
        operationHistory.removeLocalVariable("row");

        memoryHistory.addOperationHistory(operationHistory);
    }
}
