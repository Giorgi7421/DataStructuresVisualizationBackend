package org.gpavl.datastructuresvisualizationbackend.model.arrayvector;

import lombok.Data;

import java.util.List;

@Data
public class ArrayVectorStateResponse<T> {

    private int capacity;
    private int count;
    private List<T> array;
}
