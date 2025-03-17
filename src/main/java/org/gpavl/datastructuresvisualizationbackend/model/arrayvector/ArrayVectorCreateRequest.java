package org.gpavl.datastructuresvisualizationbackend.model.arrayvector;

import lombok.Data;
import org.gpavl.datastructuresvisualizationbackend.validation.annotation.ValidArrayVectorCreateRequest;

@Data
@ValidArrayVectorCreateRequest
public class ArrayVectorCreateRequest {

    private String name;
    private Integer amount;
    private String value;

}
