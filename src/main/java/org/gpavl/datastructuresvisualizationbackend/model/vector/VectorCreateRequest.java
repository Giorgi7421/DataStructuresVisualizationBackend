package org.gpavl.datastructuresvisualizationbackend.model.vector;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.gpavl.datastructuresvisualizationbackend.validation.annotation.ValidVectorCreateRequest;

@Data
@ValidVectorCreateRequest
public class VectorCreateRequest {

    @NotNull
    private String name;
    private Integer amount;
    private String value;

    //TODO add limit to amount

}
