package org.gpavl.datastructuresvisualizationbackend.model.arrayvector;

import lombok.Data;
import org.gpavl.datastructuresvisualizationbackend.util.DataType;
import org.gpavl.datastructuresvisualizationbackend.validation.annotation.ValidArrayVectorCreateRequest;

@Data
@ValidArrayVectorCreateRequest
public class ArrayVectorCreateRequest<T> {

    private String name;
    private DataType dataType;
    private Integer amount;
    private T value;

}
