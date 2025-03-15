package org.gpavl.datastructuresvisualizationbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FloatArrayVectorState extends BaseArrayVectorState<Float> {

    @ElementCollection
    @CollectionTable(name = "float_array_values", joinColumns = @JoinColumn(name = "entity_id"))
    @Column(name = "float_value")
    private List<Float> array;
}
