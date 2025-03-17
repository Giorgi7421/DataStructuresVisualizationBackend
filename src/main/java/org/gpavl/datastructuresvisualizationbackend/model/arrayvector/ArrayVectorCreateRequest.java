package org.gpavl.datastructuresvisualizationbackend.model.arrayvector;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.gpavl.datastructuresvisualizationbackend.validation.annotation.ValidArrayVectorCreateRequest;

@Data
@ValidArrayVectorCreateRequest
public class ArrayVectorCreateRequest {

    @NotNull
    private String name;
    private Integer amount;
    private String value;

}
